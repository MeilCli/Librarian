package net.meilcli.librarian.serializers

import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
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
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Notices::class.java)
        val notices = checkNotNull(adapter.fromJson(json))

        assertEquals("test title", notices.title)
        assertEquals("test description", notices.description)
        assertEquals(1, notices.notices.size)

        val notice = notices.notices[0]

        assertEquals(1, notice.artifacts.size)
        assertEquals("net.meilcli.librarian:ui-core", notice.artifacts.first())
        assertEquals("Librarian", notice.name)
        assertEquals("MeilCli", notice.author)
        assertEquals("https://github.com/MeilCli/Librarian", notice.url)
        assertEquals(null, notice.description)
        assertEquals(1, notice.licenses.size)

        val license = notice.licenses.first()

        assertEquals("MIT License", license.name)
        assertEquals("https://github.com/MeilCli/Librarian/blob/master/LICENSE", license.url)
    }
}