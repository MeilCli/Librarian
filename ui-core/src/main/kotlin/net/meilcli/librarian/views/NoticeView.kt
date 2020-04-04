package net.meilcli.librarian.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.meilcli.librarian.INotice
import net.meilcli.librarian.NoticeStyle
import net.meilcli.librarian.adapters.NoticeAdapter

class NoticeView : RecyclerView {

    private val adapter = NoticeAdapter()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        super.setAdapter(adapter)
        layoutManager = LinearLayoutManager(context)
    }

    fun setNotice(notice: INotice) {
        adapter.notice = notice
    }

    fun setStyle(style: NoticeStyle) {
        adapter.style = style
    }
}