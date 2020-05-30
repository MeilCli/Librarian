package net.meilcli.librarian.plugin

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

open class LibrarianExtension @Inject constructor(
    project: Project,
    @Suppress("UnstableApiUsage") private val objectFactory: ObjectFactory
) {

    companion object {

        const val defaultDataFolder = "Library"
        const val buildFolder = "librarian"
        const val artifactsFolder = "artifacts"
        const val groupsFolder = "groups"
    }

    var dataFolderName = defaultDataFolder

    var depth = "firstLevel"

    val depthType: LibrarianDepth
        get() = when (depth) {
            "allLevel" -> LibrarianDepth.AllLevel
            else -> LibrarianDepth.FirstLevel
        }

    var failOnGeneratePageWhenFoundPlaceholder = true

    var failOnOverrideUnMatchedLicense = true

    var useBintray = false

    var ignoreArtifacts = mutableListOf<String>()

    val pages = project.container(LibrarianPageExtension::class.java, LibrarianPageFactory(project, objectFactory))

    val groups = objectFactory.domainObjectContainer(LibrarianGroupExtension::class.java)

    fun pages(action: Action<in NamedDomainObjectContainer<LibrarianPageExtension>>) {
        action.execute(pages)
    }

    fun groups(action: Action<in NamedDomainObjectContainer<LibrarianGroupExtension>>) {
        action.execute(groups)
    }
}