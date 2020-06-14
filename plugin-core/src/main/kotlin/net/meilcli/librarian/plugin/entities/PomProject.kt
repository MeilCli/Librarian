package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlChildrenName
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("project", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
data class PomProject(
    private var groupValue: PomGroup?,
    private var artifactValue: PomArtifact?,
    private var versionValue: PomVersion?,
    private var nameValue: PomName?,
    private var descriptionValue: PomDescription?,
    private var urlValue: PomUrl?,
    val parent: PomParentProject?,
    val organization: PomOrganization?,

    @XmlSerialName("licenses", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    @XmlChildrenName("license", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    val licenses: List<PomLicense>?,

    @XmlSerialName("developers", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    @XmlChildrenName("developer", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    val developers: List<PomDeveloper>?
) {

    @Serializable
    @XmlSerialName("groupId", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomGroup(
        @XmlValue(true)
        val value: String
    )

    @Serializable
    @XmlSerialName("artifactId", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomArtifact(
        @XmlValue(true)
        val value: String
    )

    @Serializable
    @XmlSerialName("version", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomVersion(
        @XmlValue(true)
        val value: String
    )

    @Serializable
    @XmlSerialName("name", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomName(
        @XmlValue(true)
        val value: String = ""
    )

    @Serializable
    @XmlSerialName("description", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomDescription(
        @XmlValue(true)
        val value: String = ""
    )

    @Serializable
    @XmlSerialName("url", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
    data class PomUrl(
        @XmlValue(true)
        val value: String = ""
    )

    var group: String?
        get() = groupValue?.value
        set(value) {
            groupValue = value?.let { PomGroup(it) }
        }

    var artifact: String?
        get() = artifactValue?.value
        set(value) {
            artifactValue = value?.let { PomArtifact(it) }
        }

    var version: String?
        get() = versionValue?.value
        set(value) {
            versionValue = value?.let { PomVersion(it) }
        }

    var name: String?
        get() = nameValue?.value
        set(value) {
            nameValue = value?.let { PomName(it) }
        }

    var description: String?
        get() = descriptionValue?.value
        set(value) {
            descriptionValue = value?.let { PomDescription(it) }
        }

    var url: String?
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
        parent: PomParentProject?,
        organization: PomOrganization?,
        licenses: List<PomLicense>?,
        developers: List<PomDeveloper>?
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