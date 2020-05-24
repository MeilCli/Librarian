package net.meilcli.librarian.plugin

import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory

@Suppress("UnstableApiUsage")
class LibrarianPageFactory(
    private val project: Project,
    private val objectFactory: ObjectFactory
) : NamedDomainObjectFactory<LibrarianPageExtension> {

    override fun create(name: String): LibrarianPageExtension {
        return objectFactory.newInstance(LibrarianPageExtension::class.java, name, project, objectFactory)
    }
}