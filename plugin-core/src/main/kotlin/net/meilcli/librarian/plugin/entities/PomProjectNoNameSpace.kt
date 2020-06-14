package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlChildrenName
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("project", namespace = "", prefix = "")
data class PomProjectNoNameSpace(
    private var groupValue: PomGroup?,
    private var artifactValue: PomArtifact?,
    private var versionValue: PomVersion?,
    private var nameValue: PomName?,
    private var descriptionValue: PomDescription?,
    private var urlValue: PomUrl?,
    override val parent: PomParentProjectNoNameSpace?,
    override val organization: PomOrganizationNoNameSpace?,

    @XmlSerialName("licenses", namespace = "", prefix = "")
    @XmlChildrenName("license", namespace = "", prefix = "")
    override val licenses: List<PomLicenseNoNameSpace>?,

    @XmlSerialName("developers", namespace = "", prefix = "")
    @XmlChildrenName("developer", namespace = "", prefix = "")
    override val developers: List<PomDeveloperNoNameSpace>?
) : IPomProject {

    @Serializable
    @XmlSerialName("groupId", namespace = "", prefix = "")
    data class PomGroup(
        @XmlValue(true)
        val value: String
    )

    @Serializable
    @XmlSerialName("artifactId", namespace = "", prefix = "")
    data class PomArtifact(
        @XmlValue(true)
        val value: String
    )

    @Serializable
    @XmlSerialName("version", namespace = "", prefix = "")
    data class PomVersion(
        @XmlValue(true)
        val value: String
    )

    @Serializable
    @XmlSerialName("name", namespace = "", prefix = "")
    data class PomName(
        @XmlValue(true)
        val value: String = ""
    )

    @Serializable
    @XmlSerialName("description", namespace = "", prefix = "")
    data class PomDescription(
        @XmlValue(true)
        val value: String = ""
    )

    @Serializable
    @XmlSerialName("url", namespace = "", prefix = "")
    data class PomUrl(
        @XmlValue(true)
        val value: String = ""
    )

    override var group: String?
        get() = groupValue?.value
        set(value) {
            groupValue = value?.let { PomGroup(it) }
        }

    override var artifact: String?
        get() = artifactValue?.value
        set(value) {
            artifactValue = value?.let { PomArtifact(it) }
        }

    override var version: String?
        get() = versionValue?.value
        set(value) {
            versionValue = value?.let { PomVersion(it) }
        }

    override var name: String?
        get() = nameValue?.value
        set(value) {
            nameValue = value?.let { PomName(it) }
        }

    override var description: String?
        get() = descriptionValue?.value
        set(value) {
            descriptionValue = value?.let { PomDescription(it) }
        }

    override var url: String?
        get() = urlValue?.value
        set(value) {
            urlValue = value?.let { PomUrl(it) }
        }

    constructor(
        group: String?,
        artifact: String?,
        version: String?,
        name: String?,
        description: String?,
        url: String?,
        parent: PomParentProjectNoNameSpace?,
        organization: PomOrganizationNoNameSpace?,
        licenses: List<PomLicenseNoNameSpace>?,
        developers: List<PomDeveloperNoNameSpace>?
    ) : this(
        group?.let { PomGroup(it) },
        artifact?.let { PomArtifact(it) },
        version?.let { PomVersion(it) },
        name?.let { PomName(it) },
        description?.let { PomDescription(it) },
        url?.let { PomUrl(it) },
        parent,
        organization,
        licenses,
        developers
    )
}