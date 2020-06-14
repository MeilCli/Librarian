package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("organization", namespace = "", prefix = "")
data class PomOrganizationNoNameSpace(
    private val nameValue: PomName?
) : IPomOrganization {

    @Serializable
    @XmlSerialName("name", namespace = "", prefix = "")
    data class PomName(
        @XmlValue(true)
        val value: String = ""
    )

    override val name: String?
        get() = nameValue?.value
}