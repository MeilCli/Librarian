package net.meilcli.librarian.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.meilcli.librarian.INotice
import net.meilcli.librarian.INotices
import net.meilcli.librarian.NoticesStyle
import net.meilcli.librarian.holders.NoticesHolder

class NoticesAdapter : RecyclerView.Adapter<NoticesHolder>() {

    companion object {

        private const val titleViewType = 1
        private const val descriptionViewType = 2
        private const val noticeViewType = 3
    }

    private sealed class Entity {

        class Title(val title: String) : Entity()
        class Description(val description: String) : Entity()
        class Notice(val notice: INotice) : Entity()
    }

    private val entities = mutableListOf<Entity>()

    var style: NoticesStyle = NoticesStyle()
        set(value) {
            field = value
            initEntities()
            notifyDataSetChanged()
        }

    var notices: INotices? = null
        set(value) {
            field = value
            initEntities()
            notifyDataSetChanged()
        }

    private fun initEntities() {
        val notices = notices ?: return

        entities.clear()

        if (style.noticesShowTitle) {
            entities += Entity.Title(notices.title)
        }
        val description = notices.description
        if (description != null) {
            entities += Entity.Description(description)
        }
        for (notice in notices.notices) {
            entities += Entity.Notice(notice)
        }
    }

    override fun getItemCount(): Int {
        return entities.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (entities[position]) {
            is Entity.Title -> titleViewType
            is Entity.Description -> descriptionViewType
            is Entity.Notice -> noticeViewType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticesHolder {
        return when (viewType) {
            titleViewType -> NoticesHolder.Title(parent, style)
            descriptionViewType -> NoticesHolder.Description(parent, style)
            noticeViewType -> NoticesHolder.Notice(parent, style)
            else -> throw IllegalStateException("unknown view holder type")
        }
    }

    override fun onBindViewHolder(holder: NoticesHolder, position: Int) {
        val entity = entities[position]
        when (holder) {
            is NoticesHolder.Title -> holder.bind((entity as Entity.Title).title)
            is NoticesHolder.Description -> holder.bind((entity as Entity.Description).description)
            is NoticesHolder.Notice -> holder.bind((entity as Entity.Notice).notice)
        }
    }
}