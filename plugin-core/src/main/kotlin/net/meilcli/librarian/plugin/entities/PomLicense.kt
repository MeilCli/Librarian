package net.meilcli.librarian.plugin.entities

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "license")
data class PomLicense(

    @PropertyElement(name = "name")
    val name: String?,

    @PropertyElement(name = "url")
    val url: String?
)