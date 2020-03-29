package net.meilcli.librarian.plugin

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

open class LibrarianExtension @Inject constructor(
    @Suppress("UnstableApiUsage") val objectFactory: ObjectFactory
) {

    companion object {

        const val defaultDataFolder = "Library"
        const val defaultArtifactsFolder = "Artifacts"
        const val defaultGroupsFolder = "Groups"
    }

    var dataFolderName = defaultDataFolder

    var artifactsFolderName = defaultArtifactsFolder

    var groupsFolderName = defaultGroupsFolder

    var depth = "firstLevel"

    val depthType: LibrarianDepth
        get() = when (depth) {
            "allLevel" -> LibrarianDepth.AllLevel
            else -> LibrarianDepth.FirstLevel
        }

    var failOnGeneratePageWhenFoundPlaceholder = true

    val pages = objectFactory.domainObjectContainer(LibrarianPageExtension::class.java)

    val groups = objectFactory.domainObjectContainer(LibrarianGroupExtension::class.java)

    fun pages(action: Action<in NamedDomainObjectContainer<LibrarianPageExtension>>) {
        action.execute(pages)
    }

    fun groups(action: Action<in NamedDomainObjectContainer<LibrarianGroupExtension>>) {
        action.execute(groups)
    }
}