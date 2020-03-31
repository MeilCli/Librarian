package net.meilcli.librarian.plugin.entities

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "project")
data class PomProject(

    @PropertyElement(name = "groupId")
    var group: String?,

    @PropertyElement(name = "artifactId")
    var artifact: String?,

    @PropertyElement(name = "version")
    var version: String?,

    @PropertyElement(name = "name")
    val name: String?,

    @PropertyElement(name = "description")
    val description: String?,

    @PropertyElement(name = "url")
    val url: String?,

    @Element
    val parent: PomParentProject?,

    @Path("licenses")
    @Element
    val licenses: List<PomLicense>?,

    @Path("developers")
    @Element
    val developers: List<PomDeveloper>?
)