package net.meilcli.librarian.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.meilcli.librarian.INotices
import net.meilcli.librarian.NoticesStyle
import net.meilcli.librarian.adapters.NoticesAdapter

class NoticesView : RecyclerView {

    private val adapter = NoticesAdapter()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        super.setAdapter(adapter)
        layoutManager = LinearLayoutManager(context)
    }

    fun setNotices(notices: INotices) {
        adapter.notices = notices
    }

    fun setStyle(style: NoticesStyle) {
        adapter.style = style
    }
}