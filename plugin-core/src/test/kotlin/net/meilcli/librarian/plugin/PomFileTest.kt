package net.meilcli.librarian.plugin

import kotlinx.serialization.modules.EmptyModule
import net.meilcli.librarian.plugin.entities.IPomProject
import net.meilcli.librarian.plugin.entities.PomProject
import net.meilcli.librarian.plugin.entities.PomProjectNoNameSpace
import nl.adaptivity.xmlutil.XmlException
import nl.adaptivity.xmlutil.serialization.XML
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PomFileTest {

    private fun readFile(fileName: String): IPomProject {
        val text = this.javaClass
            .classLoader
            .getResourceAsStream(fileName)
            ?.readBytes()
            ?.toString(Charsets.UTF_8)
            ?: throw IllegalStateException("not found test file")
        val xml = XML(EmptyModule) {
            this.unknownChildHandler = { _, _, _, _ -> }
        }
        return try {
            xml.parse(PomProject.serializer(), text)
        } catch (exception: XmlException) {
            xml.parse(PomProjectNoNameSpace.serializer(), text)
        }
    }

    @Test
    fun testFull() {
        val project = readFile("test-pom-full.xml")

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
        val project = readFile("test-pom-with-empty-url.xml")

        assertEquals("test-group", project.group)
        assertEquals("test-artifact", project.artifact)
        assertEquals("1.0.0", project.version)
        assertEquals("test-name", project.name)
        assertEquals("test-description", project.description)
        assertNull(project.url)
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
        val project = readFile("test-pom-with-null-url.xml")

        assertEquals("test-group", project.group)
        assertEquals("test-artifact", project.artifact)
        assertEquals("1.0.0", project.version)
        assertEquals("test-name", project.name)
        assertEquals("test-description", project.description)
        assertNull(project.url)
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
        val project = readFile("test-pom-without-licenses.xml")

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
        val project = readFile("test-pom-without-developers.xml")

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
        val project = readFile("test-pom-without-parent.xml")

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
        val project = readFile("test-pom-without-organization.xml")

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

    @Test
    fun testWithoutNameSpace() {
        val project = readFile("test-pom-without-namespace.xml")

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
}