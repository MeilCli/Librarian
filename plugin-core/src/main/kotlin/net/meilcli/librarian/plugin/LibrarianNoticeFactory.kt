package net.meilcli.librarian.plugin

import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.model.ObjectFactory

@Suppress("UnstableApiUsage")
class LibrarianNoticeFactory(
    private val objectFactory: ObjectFactory
) : NamedDomainObjectFactory<LibrarianNoticeExtension> {

    override fun create(name: String): LibrarianNoticeExtension {
        return objectFactory.newInstance(LibrarianNoticeExtension::class.java, name, objectFactory)
    }
}