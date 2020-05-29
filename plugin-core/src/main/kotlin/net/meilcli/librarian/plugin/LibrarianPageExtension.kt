package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.configurations.Configurations
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import java.io.File
import javax.inject.Inject

open class LibrarianPageExtension @Inject constructor(
    val name: String,
    project: Project,
    @Suppress("UnstableApiUsage") private val objectFactory: ObjectFactory
) {

    var title = name
    var description: String? = null
    var markdown = true
    var markdownFileName = "README.md"
    var json = true
    var jsonFileName = "notices.json"
    var jsonAdditionalOutputPath: File? = null

    val configurations = objectFactory.newInstance(Configurations::class.java, objectFactory)

    fun configurations(action: Action<in Configurations>) {
        action.execute(configurations)
    }

    val additionalNotices = project.container(LibrarianNoticeExtension::class.java, LibrarianNoticeFactory(objectFactory))

    fun additionalNotices(action: Action<in NamedDomainObjectContainer<LibrarianNoticeExtension>>) {
        action.execute(additionalNotices)
    }
}