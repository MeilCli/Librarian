package net.meilcli.librarian.serializers

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test

class ParseTest {

    companion object {

        private val json = """
            {
                "title": "test title",
                "description": "test description",
                "notices": [
                    {
                        "name": "Librarian",
                        "author": "MeilCli",
                        "url": "https://github.com/MeilCli/Librarian",
                        "description": null,
                        "resources": [
                            {
                                "artifacts": [
                                    "net.meilcli.librarian:ui-core"
                                ],
                                "licenses": [
                                    {
                                        "name": "MIT License",
                                        "url": "https://github.com/MeilCli/Librarian/blob/master/LICENSE"
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        """.trimIndent()
    }

    @Test
    fun test() {
        val notices = Gson().fromJson(json, GsonNotices::class.java).toNotices()

        Assert.assertEquals("test title", notices.title)
        Assert.assertEquals("test description", notices.description)
        Assert.assertEquals(1, notices.notices.size)

        val notice = notices.notices[0]

        Assert.assertEquals("Librarian", notice.name)
        Assert.assertEquals("MeilCli", notice.author)
        Assert.assertEquals("https://github.com/MeilCli/Librarian", notice.url)
        Assert.assertEquals(null, notice.description)
        Assert.assertEquals(1, notice.resources.size)

        val resource = notice.resources.first()

        Assert.assertEquals(1, resource.artifacts.size)
        Assert.assertEquals("net.meilcli.librarian:ui-core", resource.artifacts.first())
        Assert.assertEquals(1, resource.licenses.size)

        val license = resource.licenses.first()

        Assert.assertEquals("MIT License", license.name)
        Assert.assertEquals("https://github.com/MeilCli/Librarian/blob/master/LICENSE", license.url)
    }
}