package net.meilcli.librarian.serializers

import android.content.Context
import com.google.gson.Gson
import net.meilcli.librarian.INotices
import net.meilcli.librarian.INoticesReader
import java.io.BufferedReader

class NoticesReader : INoticesReader {

    @Suppress("ConvertTryFinallyToUseCall")
    override fun read(context: Context, fileName: String): INotices {
        val reader = BufferedReader(context.assets.open(fileName).reader())
        try {
            val text = reader.readText()
            return checkNotNull(Gson().fromJson(text, GsonNotices::class.java)).toNotices()
        } finally {
            reader.close()
        }
    }
}