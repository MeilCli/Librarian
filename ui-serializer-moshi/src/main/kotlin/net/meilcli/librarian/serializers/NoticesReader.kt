package net.meilcli.librarian.serializers

import android.content.Context
import com.squareup.moshi.Moshi
import net.meilcli.librarian.INotices
import net.meilcli.librarian.INoticesReader
import java.io.BufferedReader

class NoticesReader : INoticesReader {

    private fun createMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Suppress("ConvertTryFinallyToUseCall")
    override fun read(context: Context, fileName: String): INotices {
        val reader = BufferedReader(context.assets.open(fileName).reader())
        try {
            val text = reader.readText()
            return checkNotNull(createMoshi().adapter(Notices::class.java).fromJson(text))
        } finally {
            reader.close()
        }
    }
}