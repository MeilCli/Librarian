package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.Placeholder
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

open class LibrarianNoticeExtension @Inject constructor(
    val name: String,
    @Suppress("UnstableApiUsage") private val objectFactory: ObjectFactory
) {

    var artifacts = mutableListOf<String>()
    var author: String = Placeholder.author
    var url: String = Placeholder.url
    var description: String? = null

    val licenses = objectFactory.domainObjectContainer(LibrarianLicenseExtension::class.java)

    fun licenses(action: Action<in NamedDomainObjectContainer<LibrarianLicenseExtension>>) {
        action.execute(licenses)
    }

    fun toNotice(): Notice {
        return Notice(
            artifacts = artifacts,
            name = name,
            author = author,
            url = url,
            description = description,
            licenses = licenses.map { it.toLicense() }
        )
    }
}