package net.meilcli.librarian.plugin.tasks

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.internal.artifacts.ArtifactsByLibraryGroupsFilter
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsLoader
import net.meilcli.librarian.plugin.internal.artifacts.ConfigurationArtifactsToArtifactsTranslator
import net.meilcli.librarian.plugin.internal.bintray.*
import net.meilcli.librarian.plugin.internal.libraries.LocalLibrariesLoader
import net.meilcli.librarian.plugin.internal.librarygroups.LocalLibraryGroupsLoader
import net.meilcli.librarian.plugin.internal.librarygroups.LocalLibraryGroupsWriter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GenerateBintrayGroupsTask : DefaultTask() {

    @Input
    var extension: LibrarianExtension? = null

    @TaskAction
    fun action() {
        val extension = extension ?: return
        if (extension.useBintray.not()) {
            return
        }

        val libraryGroupsLoader = LocalLibraryGroupsLoader(project)
        val configurationArtifactsLoader = ConfigurationArtifactsLoader(project, extension)
        val artifactsTranslator = ConfigurationArtifactsToArtifactsTranslator()
        val libraryGroupsFilter = ArtifactsByLibraryGroupsFilter(libraryGroupsLoader.load())
        val artifacts = configurationArtifactsLoader.load()
            .let { artifactsTranslator.translate(it) }
            .let { libraryGroupsFilter.filter(it) }

        val librariesLoader = LocalLibrariesLoader(project)
        val bintrayRepositoryLoader = ArtifactToBintrayRepositoryLoader(BintrayRepositoriesLoader(project))
        val bintrayPackageLoader = ArtifactAndBintrayRepositoryToBintrayPackageLoader(project)
        val bintrayLicensesLoader = BintrayLicensesLoader()
        val bintrayAggregator = BintrayAggregator(librariesLoader, bintrayRepositoryLoader, bintrayPackageLoader, bintrayLicensesLoader)

        val libraryGroups = bintrayAggregator.aggregate(artifacts)

        val libraryGroupsWriter = LocalLibraryGroupsWriter(project)
        libraryGroupsWriter.write(libraryGroups)
    }
}