package net.meilcli.librarian.plugin.entities

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("parent", namespace = "http://maven.apache.org/POM/4.0.0", prefix = "")
data class PomParentProject(
    private val groupValue: PomGroup?,
    private val artifactValue: PomArtifact?,
    private val versionValue: PomVersion?
) : IPomParentProject {

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

    override val group: String?
        get() = groupValue?.value

    override val artifact: String?
        get() = artifactValue?.value

    override val version: String?
        get() = versionValue?.value
}