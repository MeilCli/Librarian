package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("license", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
data class PomLicense(
    private val nameValue: PomName?,
    private val urlValue: PomUrl?
) {
    @Serializable
    @XmlSerialName("name", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomName(
        @XmlValue(true)
        val value: String = ""
    )

    @Serializable
    @XmlSerialName("url", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomUrl(
        @XmlValue(true)
        val value: String = ""
    )

    val name: String?
        get() = nameValue?.value

    val url: String?
        get() = urlValue?.value
}