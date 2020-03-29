package net.meilcli.librarian.plugin.entities

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "developer")
data class PomDeveloper(

    @PropertyElement(name = "name")
    val name: String?,

    @PropertyElement(name = "organization")
    val organization: String?
)