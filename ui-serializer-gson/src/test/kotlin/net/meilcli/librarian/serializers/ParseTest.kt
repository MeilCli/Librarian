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
                        "artifacts": [
                            "net.meilcli.librarian:ui-core"
                        ],
                        "name": "Librarian",
                        "author": "MeilCli",
                        "url": "https://github.com/MeilCli/Librarian",
                        "description": null,
                        "licenses": [
                            {
                                "name": "MIT License",
                                "url": "https://github.com/MeilCli/Librarian/blob/master/LICENSE"
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

        Assert.assertEquals(1, notice.artifacts.size)
        Assert.assertEquals("net.meilcli.librarian:ui-core", notice.artifacts.first())
        Assert.assertEquals("Librarian", notice.name)
        Assert.assertEquals("MeilCli", notice.author)
        Assert.assertEquals("https://github.com/MeilCli/Librarian", notice.url)
        Assert.assertEquals(null, notice.description)
        Assert.assertEquals(1, notice.licenses.size)

        val license = notice.licenses.first()

        Assert.assertEquals("MIT License", license.name)
        Assert.assertEquals("https://github.com/MeilCli/Librarian/blob/master/LICENSE", license.url)
    }
}