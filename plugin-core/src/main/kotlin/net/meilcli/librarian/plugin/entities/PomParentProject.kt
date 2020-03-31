package net.meilcli.librarian.plugin.entities

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "parent")
data class PomParentProject(

    @PropertyElement(name = "groupId")
    var group: String?,

    @PropertyElement(name = "artifactId")
    var artifact: String?,

    @PropertyElement(name = "version")
    var version: String?
)