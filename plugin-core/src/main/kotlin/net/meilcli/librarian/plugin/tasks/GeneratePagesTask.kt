package net.meilcli.librarian.plugin.tasks

import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.LibrarianPageExtension
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.ArtifactLoader
import net.meilcli.librarian.plugin.internal.LibrarianException
import net.meilcli.librarian.plugin.internal.PlaceHolder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GeneratePagesTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @UnstableDefault
    @TaskAction
    fun action() {
        val extension = extension ?: return
        if (extension.pages.isEmpty()) {
            return
        }

        val artifactLoaderResult = ArtifactLoader().load(project, extension.depthType)
        val artifacts = loadArtifacts()
        val groups = loadGroups()

        for (page in extension.pages) {
            try {
                writePage(page, artifactLoaderResult, artifacts ?: emptyList(), groups ?: emptyList())
            } catch (exception: Exception) {
                if (exception is LibrarianException) {
                    throw exception
                }
                project.logger.error("Failed Librarian, page: ${page.name}", exception)
            }
        }
    }

    @UnstableDefault
    private fun writePage(
        page: LibrarianPageExtension,
        artifactLoaderResult: ArtifactLoader.Result,
        artifacts: List<Library>,
        groups: List<LibraryGroup>
    ) {
        val notices = mutableListOf<Notice>()
        val noticeArtifacts = mutableSetOf<Artifact>()

        for (configuration in page.configurations) {
            val artifactResult = artifactLoaderResult.entries.firstOrNull { it.configurationName == configuration }
            if (artifactResult == null) {
                project.logger.warn("Librarian cannot resolve unknown configuration: $configuration")
                continue
            }
            noticeArtifacts.addAll(artifactResult.artifacts)
        }

        for (noticeArtifact in noticeArtifacts) {
            val foundArtifact = artifacts.find { it.artifact == noticeArtifact.artifact }
            val foundGroup = groups.find { it.artifacts.contains(noticeArtifact.artifact) }
            val notice = when {
                foundGroup != null && foundArtifact != null -> {
                    if (foundArtifact.licenses.isNotEmpty()) {
                        // check same licenses
                        for (checkLicense in foundArtifact.licenses) {
                            if (checkLicense.name == PlaceHolder.licenseName || checkLicense.url == PlaceHolder.licenseUrl) {
                                // will override by group
                                continue
                            }
                            if (foundGroup.licenses.contains(checkLicense).not()) {
                                project.logger.warn("Librarian warning: group has not artifact license, ${foundGroup.name}, ${foundArtifact.artifact}")
                            }
                        }
                    }
                    Notice(
                        artifacts = foundGroup.artifacts,
                        name = foundGroup.name,
                        author = foundGroup.author,
                        url = foundGroup.url,
                        description = foundGroup.description,
                        licenses = foundGroup.licenses
                    )
                }
                foundArtifact != null -> {
                    Notice(
                        artifacts = listOf(foundArtifact.artifact),
                        name = foundArtifact.name,
                        author = foundArtifact.author,
                        url = foundArtifact.url,
                        description = foundArtifact.description,
                        licenses = foundArtifact.licenses
                    )
                }
                else -> {
                    throw LibrarianException("Librarian not found library data: ${noticeArtifact.group}:${noticeArtifact.name}")
                }
            }

            if (notices.contains(notice).not()) {
                notices.add(notice)
            }
        }

        checkNotice(notices)

        notices.sortBy { it.name }

        if (page.markdown) {
            writeMarkdownPage(page, notices)
        }
        if (page.json) {
            writeJsonPage(page, notices)
        }
    }

    private fun checkNotice(notices: List<Notice>) {
        val extension = extension ?: return

        fun warnOrThrow(notice: Notice) {
            if (extension.failOnGeneratePageWhenFoundPlaceholder) {
                throw LibrarianException("Librarian error: notice has placeholder, ${notice.artifacts.joinToString()}")
            } else {
                project.logger.warn("Librarian warning: notice has placeholder, ${notice.artifacts.joinToString()}")
            }
        }

        for (notice in notices) {
            if (notice.author == PlaceHolder.author || notice.name == PlaceHolder.name || notice.url == PlaceHolder.url) {
                warnOrThrow(notice)
            }
            for (license in notice.licenses) {
                if (license.name == PlaceHolder.name || license.url == PlaceHolder.url) {
                    warnOrThrow(notice)
                }
            }
        }
    }

    private fun writeMarkdownPage(page: LibrarianPageExtension, notices: List<Notice>) {
        val extension = extension ?: return
        val sb = StringBuilder()
        sb.appendln("# ${page.title}")
        sb.appendln("*This markdown is auto generated by [Librarian](https://github.com/MeilCli/Librarian)*")
        sb.appendln()
        page.description?.also {
            sb.appendln(it)
            sb.appendln()
        }
        sb.appendln("## Using")
        for (notice in notices) {
            sb.appendln("- [${notice.name}](${notice.url}), licensed on ${notice.licenses.joinToString { "[${it.name}](${it.url})" }}, made by ${notice.author}")
        }

        val outputDirectory = File(project.rootProject.rootDir, "${extension.dataFolderName}/${page.name}")
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val outputFile = File(outputDirectory, page.markdownFileName)
        outputFile.writeText(sb.toString(), Charsets.UTF_8)
    }

    @UnstableDefault
    private fun writeJsonPage(page: LibrarianPageExtension, notices: List<Notice>) {
        val extension = extension ?: return
        val outputDirectory = File(project.rootProject.rootDir, "${extension.dataFolderName}/${page.name}")
        if (outputDirectory.exists().not()) {
            outputDirectory.mkdirs()
        }

        val json = Json {
            this.prettyPrint = true
        }

        val outputFile = File(outputDirectory, page.jsonFileName)
        val text = json.stringify(ListSerializer(Notice.serializer()), notices)
        outputFile.writeText(text, Charsets.UTF_8)
    }

    @UnstableDefault
    private fun loadArtifacts(): List<Library>? {
        val extension = extension ?: return null
        val artifactsFolder = File(project.rootProject.rootDir, "${extension.dataFolderName}/${extension.artifactsFolderName}")
        if (artifactsFolder.exists().not()) {
            return null
        }

        val result = mutableListOf<Library>()
        val json = Json(JsonConfiguration.Default)
        for (artifactFile in artifactsFolder.listFiles() ?: emptyArray()) {
            try {
                result += json.parse(Library.serializer(), artifactFile.readText())
            } catch (exception: Exception) {
                project.logger.warn("Librarian cannot read artifact file: ${artifactFile.absolutePath}", exception)
            }
        }

        return result
    }

    @UnstableDefault
    private fun loadGroups(): List<LibraryGroup>? {
        val extension = extension ?: return null
        val groupsFolder = File(project.rootProject.rootDir, "${extension.dataFolderName}/${extension.groupsFolderName}")
        if (groupsFolder.exists().not()) {
            return null
        }

        val result = mutableListOf<LibraryGroup>()
        val json = Json(JsonConfiguration.Default)
        for (grouptFile in groupsFolder.listFiles() ?: emptyArray()) {
            try {
                result += json.parse(LibraryGroup.serializer(), grouptFile.readText())
            } catch (exception: Exception) {
                project.logger.warn("Librarian cannot read group file: ${grouptFile.absolutePath}", exception)
            }
        }

        return result
    }
}