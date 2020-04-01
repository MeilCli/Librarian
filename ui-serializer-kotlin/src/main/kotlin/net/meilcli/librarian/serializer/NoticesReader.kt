package net.meilcli.librarian.serializer

import android.content.Context
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import net.meilcli.librarian.INotices
import net.meilcli.librarian.INoticesReader
import java.io.BufferedReader

class NoticesReader : INoticesReader {

    @Suppress("ConvertTryFinallyToUseCall")
    @UnstableDefault
    override fun read(context: Context, fileName: String): INotices {
        val reader = BufferedReader(context.assets.open(fileName).reader())
        try {
            val text = reader.readText()
            val json = Json {}
            return json.parse(Notices.serializer(), text)
        } finally {
            reader.close()
        }
    }
}