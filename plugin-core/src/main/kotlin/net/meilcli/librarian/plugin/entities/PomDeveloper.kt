package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.Serializable
import net.meilcli.librarian.plugin.extensions.toNullIfEmpty
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("developer", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
data class PomDeveloper(
    private val nameValue: PomName?,
    private val organizationValue: PomOrganization?
) : IPomDeveloper {

    @Serializable
    @XmlSerialName("name", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomName(
        @XmlValue(true)
        val value: String = ""
    )

    @Serializable
    @XmlSerialName("organization", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomOrganization(
        @XmlValue(true)
        val value: String = ""
    )

    override val name: String?
        get() = nameValue?.value?.toNullIfEmpty()

    override val organization: String?
        get() = organizationValue?.value?.toNullIfEmpty()
}