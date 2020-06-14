package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("organization", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
data class PomOrganization(
    private val nameValue: PomName?
) {
    @Serializable
    @XmlSerialName("name", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomName(
        @XmlValue(true)
        val value: String = ""
    )

    val name: String?
        get() = nameValue?.value
}