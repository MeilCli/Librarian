package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.Serializable
import net.meilcli.librarian.plugin.extensions.toNullIfEmpty
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("license", namespace = "", prefix = "")
data class PomLicenseNoNameSpace(
    private val nameValue: PomName?,
    private val urlValue: PomUrl?
) : IPomLicense {

    @Serializable
    @XmlSerialName("name", namespace = "", prefix = "")
    data class PomName(
        @XmlValue(true)
        val value: String = ""
    )

    @Serializable
    @XmlSerialName("url", namespace = "", prefix = "")
    data class PomUrl(
        @XmlValue(true)
        val value: String = ""
    )

    override val name: String?
        get() = nameValue?.value?.toNullIfEmpty()

    override val url: String?
        get() = urlValue?.value?.toNullIfEmpty()
}