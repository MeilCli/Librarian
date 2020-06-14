package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("developer", namespace = "", prefix = "")
data class PomDeveloperNoNameSpace(
    private val nameValue: PomName?,
    private val organizationValue: PomOrganization?
) : IPomDeveloper {

    @Serializable
    @XmlSerialName("name", namespace = "", prefix = "")
    data class PomName(
        @XmlValue(true)
        val value: String = ""
    )

    @Serializable
    @XmlSerialName("organization", namespace = "", prefix = "")
    data class PomOrganization(
        @XmlValue(true)
        val value: String = ""
    )

    override val name: String?
        get() = nameValue?.value

    override val organization: String?
        get() = organizationValue?.value
}