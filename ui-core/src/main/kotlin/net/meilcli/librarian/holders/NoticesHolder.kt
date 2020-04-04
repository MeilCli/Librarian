package net.meilcli.librarian.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.meilcli.librarian.INotice
import net.meilcli.librarian.NoticesStyle

sealed class NoticesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class Title(
        parent: ViewGroup,
        style: NoticesStyle
    ) : NoticesHolder(LayoutInflater.from(parent.context).inflate(style.noticesTitleHolderLayout, parent, false)) {

        private val title = itemView.findViewById<TextView>(style.noticesTitleHolderTitleId)

        fun bind(title: String) {
            this.title.text = title
        }
    }

    class Description(
        parent: ViewGroup,
        style: NoticesStyle
    ) : NoticesHolder(LayoutInflater.from(parent.context).inflate(style.noticesDescriptionHolderLayout, parent, false)) {

        private val description = itemView.findViewById<TextView>(style.noticesDescriptionHolderDescriptionId)

        fun bind(description: String) {
            this.description.text = description
        }
    }

    class Notice(
        parent: ViewGroup,
        private val style: NoticesStyle
    ) : NoticesHolder(LayoutInflater.from(parent.context).inflate(style.noticesNoticeHolderLayout, parent, false)) {

        private val name = itemView.findViewById<TextView>(style.noticesNoticeHolderNameId)

        fun bind(notice: INotice) {
            this.name.text = notice.name
            itemView.setOnClickListener {
                style.onNoticeClicked(notice)
            }
        }
    }
}