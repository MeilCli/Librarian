package net.meilcli.librarian

import android.content.Context
import java.io.Serializable

interface INoticesReader : Serializable {

    fun read(context: Context, fileName: String): INotices
}