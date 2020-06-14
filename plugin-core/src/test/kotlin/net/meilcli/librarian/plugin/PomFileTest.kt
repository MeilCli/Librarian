package net.meilcli.librarian.plugin

import kotlinx.serialization.modules.EmptyModule
import net.meilcli.librarian.plugin.entities.PomProject
import nl.adaptivity.xmlutil.serialization.XML
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PomFileTest {

    private fun readFile(fileName: String): String {
        return this.javaClass
            .classLoader
            .getResourceAsStream(fileName)
            ?.readBytes()
            ?.toString(Charsets.UTF_8)
            ?: throw IllegalStateException("not found test file")
    }

    @Test
    fun testFull() {
        val text = readFile("test-pom-full.xml")
        val xml = XML(EmptyModule) {
            this.unknownChildHandler = { _, _, _, _ -> }
        }
        val project = xml.parse(PomProject.serializer(), text)

        assertEquals("test-group", project.group)
        assertEquals("test-artifact", project.artifact)
        assertEquals("1.0.0", project.version)
        assertEquals("test-name", project.name)
        assertEquals("test-description", project.description)
        assertEquals("https://meilcli.net", project.url)
        assertEquals(1, project.licenses?.size)
        assertEquals("The Apache License, Version 2.0", project.licenses?.first()?.name)
        assertEquals("http://www.apache.org/licenses/LICENSE-2.0.txt", project.licenses?.first()?.url)
        assertEquals(1, project.developers?.size)
        assertEquals("test-name", project.developers?.first()?.name)
        assertEquals("test-organization", project.developers?.first()?.organization)
        assertNotNull(project.parent)
        assertEquals("test-group", project.parent?.group)
        assertEquals("test-artifact", project.parent?.artifact)
        assertEquals("1.0.0", project.parent?.version)
        assertNotNull(project.organization)
        assertEquals("test-organization", project.organization?.name)
    }

    @Test
    fun testWithEmptyUrl() {
        val text = readFile("test-pom-with-empty-url.xml")
        val xml = XML(EmptyModule) {
            this.unknownChildHandler = { _, _, _, _ -> }
        }
        val project = xml.parse(PomProject.serializer(), text)

        assertEquals("test-group", project.group)
        assertEquals("test-artifact", project.artifact)
        assertEquals("1.0.0", project.version)
        assertEquals("test-name", project.name)
        assertEquals("test-description", project.description)
        assertEquals("", project.url)
        assertEquals(1, project.licenses?.size)
        assertEquals("The Apache License, Version 2.0", project.licenses?.first()?.name)
        assertEquals("http://www.apache.org/licenses/LICENSE-2.0.txt", project.licenses?.first()?.url)
        assertEquals(1, project.developers?.size)
        assertEquals("test-name", project.developers?.first()?.name)
        assertEquals("test-organization", project.developers?.first()?.organization)
        assertNotNull(project.parent)
        assertEquals("test-group", project.parent?.group)
        assertEquals("test-artifact", project.parent?.artifact)
        assertEquals("1.0.0", project.parent?.version)
        assertNotNull(project.organization)
        assertEquals("test-organization", project.organization?.name)
    }

    @Test
    fun testWithNullUrl() {
        val text = readFile("test-pom-with-null-url.xml")
        val xml = XML(EmptyModule) {
            this.unknownChildHandler = { _, _, _, _ -> }
        }
        val project = xml.parse(PomProject.serializer(), text)

        assertEquals("test-group", project.group)
        assertEquals("test-artifact", project.artifact)
        assertEquals("1.0.0", project.version)
        assertEquals("test-name", project.name)
        assertEquals("test-description", project.description)
        assertEquals("", project.url)
        assertEquals(1, project.licenses?.size)
        assertEquals("The Apache License, Version 2.0", project.licenses?.first()?.name)
        assertEquals("http://www.apache.org/licenses/LICENSE-2.0.txt", project.licenses?.first()?.url)
        assertEquals(1, project.developers?.size)
        assertEquals("test-name", project.developers?.first()?.name)
        assertEquals("test-organization", project.developers?.first()?.organization)
        assertNotNull(project.parent)
        assertEquals("test-group", project.parent?.group)
        assertEquals("test-artifact", project.parent?.artifact)
        assertEquals("1.0.0", project.parent?.version)
        assertNotNull(project.organization)
        assertEquals("test-organization", project.organization?.name)
    }

    @Test
    fun testWithoutLicenses() {
        val text = readFile("test-pom-without-licenses.xml")
        val xml = XML(EmptyModule) {
            this.unknownChildHandler = { _, _, _, _ -> }
        }
        val project = xml.parse(PomProject.serializer(), text)

        assertEquals("test-group", project.group)
        assertEquals("test-artifact", project.artifact)
        assertEquals("1.0.0", project.version)
        assertEquals("test-name", project.name)
        assertEquals("test-description", project.description)
        assertEquals("https://meilcli.net", project.url)
        assertNull(project.licenses)
        assertEquals(1, project.developers?.size)
        assertEquals("test-name", project.developers?.first()?.name)
        assertEquals("test-organization", project.developers?.first()?.organization)
        assertNotNull(project.parent)
        assertEquals("test-group", project.parent?.group)
        assertEquals("test-artifact", project.parent?.artifact)
        assertEquals("1.0.0", project.parent?.version)
    }

    @Test
    fun testWithoutDevelopers() {
        val text = readFile("test-pom-without-developers.xml")
        val xml = XML(EmptyModule) {
            this.unknownChildHandler = { _, _, _, _ -> }
        }
        val project = xml.parse(PomProject.serializer(), text)

        assertEquals("test-group", project.group)
        assertEquals("test-artifact", project.artifact)
        assertEquals("1.0.0", project.version)
        assertEquals("test-name", project.name)
        assertEquals("test-description", project.description)
        assertEquals("https://meilcli.net", project.url)
        assertEquals(1, project.licenses?.size)
        assertEquals("The Apache License, Version 2.0", project.licenses?.first()?.name)
        assertEquals("http://www.apache.org/licenses/LICENSE-2.0.txt", project.licenses?.first()?.url)
        assertNull(project.developers)
        assertNotNull(project.parent)
        assertEquals("test-group", project.parent?.group)
        assertEquals("test-artifact", project.parent?.artifact)
        assertEquals("1.0.0", project.parent?.version)
    }

    @Test
    fun testWithoutParent() {
        val text = readFile("test-pom-without-parent.xml")
        val xml = XML(EmptyModule) {
            this.unknownChildHandler = { _, _, _, _ -> }
        }
        val project = xml.parse(PomProject.serializer(), text)

        assertEquals("test-group", project.group)
        assertEquals("test-artifact", project.artifact)
        assertEquals("1.0.0", project.version)
        assertEquals("test-name", project.name)
        assertEquals("test-description", project.description)
        assertEquals("https://meilcli.net", project.url)
        assertEquals(1, project.licenses?.size)
        assertEquals("The Apache License, Version 2.0", project.licenses?.first()?.name)
        assertEquals("http://www.apache.org/licenses/LICENSE-2.0.txt", project.licenses?.first()?.url)
        assertEquals(1, project.developers?.size)
        assertEquals("test-name", project.developers?.first()?.name)
        assertEquals("test-organization", project.developers?.first()?.organization)
        assertNull(project.parent)
    }

    @Test
    fun testWithoutOrganization() {
        val text = readFile("test-pom-without-organization.xml")
        val xml = XML(EmptyModule) {
            this.unknownChildHandler = { _, _, _, _ -> }
        }
        val project = xml.parse(PomProject.serializer(), text)

        assertEquals("test-group", project.group)
        assertEquals("test-artifact", project.artifact)
        assertEquals("1.0.0", project.version)
        assertEquals("test-name", project.name)
        assertEquals("test-description", project.description)
        assertEquals("https://meilcli.net", project.url)
        assertEquals(1, project.licenses?.size)
        assertEquals("The Apache License, Version 2.0", project.licenses?.first()?.name)
        assertEquals("http://www.apache.org/licenses/LICENSE-2.0.txt", project.licenses?.first()?.url)
        assertEquals(1, project.developers?.size)
        assertEquals("test-name", project.developers?.first()?.name)
        assertEquals("test-organization", project.developers?.first()?.organization)
        assertNotNull(project.parent)
        assertEquals("test-group", project.parent?.group)
        assertEquals("test-artifact", project.parent?.artifact)
        assertEquals("1.0.0", project.parent?.version)
        assertNull(project.organization)
    }
}