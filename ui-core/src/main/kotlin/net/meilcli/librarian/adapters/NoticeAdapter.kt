package net.meilcli.librarian.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.meilcli.librarian.ILicense
import net.meilcli.librarian.INotice
import net.meilcli.librarian.NoticeStyle
import net.meilcli.librarian.holders.NoticeHolder

class NoticeAdapter : RecyclerView.Adapter<NoticeHolder>() {

    companion object {

        private const val nameViewType = 1
        private const val descriptionViewType = 2
        private const val authorViewType = 3
        private const val urlViewType = 4
        private const val licenseLabelViewType = 5
        private const val licenseViewType = 6
    }

    private sealed class Entity {

        class Name(val name: String) : Entity()
        class Description(val description: String) : Entity()
        class Author(val author: String) : Entity()
        class Url(val url: String) : Entity()
        object LicenseLabel : Entity()
        class License(val license: ILicense) : Entity()
    }

    private val entities = mutableListOf<Entity>()

    var style: NoticeStyle = NoticeStyle()
        set(value) {
            field = value
            initEntities()
            notifyDataSetChanged()
        }

    var notice: INotice? = null
        set(value) {
            field = value
            initEntities()
            notifyDataSetChanged()
        }

    private fun initEntities() {
        val notice = notice ?: return

        entities.clear()

        if (style.showName) {
            entities += Entity.Name(notice.name)
        }
        val description = notice.description
        if (description != null) {
            entities += Entity.Description(description)
        }
        entities += Entity.Author(notice.author)
        entities += Entity.Url(notice.url)
        entities += Entity.LicenseLabel
        for (license in notice.licenses) {
            entities += Entity.License(license)
        }
    }

    override fun getItemCount(): Int {
        return entities.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (entities[position]) {
            is Entity.Name -> nameViewType
            is Entity.Description -> descriptionViewType
            is Entity.Author -> authorViewType
            is Entity.Url -> urlViewType
            is Entity.LicenseLabel -> licenseLabelViewType
            is Entity.License -> licenseViewType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeHolder {
        return when (viewType) {
            nameViewType -> NoticeHolder.Name(parent, style)
            descriptionViewType -> NoticeHolder.Description(parent, style)
            authorViewType -> NoticeHolder.Author(parent, style)
            urlViewType -> NoticeHolder.Url(parent, style)
            licenseLabelViewType -> NoticeHolder.LicenseLabel(parent, style)
            licenseViewType -> NoticeHolder.License(parent, style)
            else -> throw IllegalStateException("unknown view holder type")
        }
    }

    override fun onBindViewHolder(holder: NoticeHolder, position: Int) {
        val entity = entities[position]
        when (holder) {
            is NoticeHolder.Name -> holder.bind((entity as Entity.Name).name)
            is NoticeHolder.Description -> holder.bind((entity as Entity.Description).description)
            is NoticeHolder.Author -> holder.bind((entity as Entity.Author).author)
            is NoticeHolder.Url -> holder.bind((entity as Entity.Url).url)
            is NoticeHolder.LicenseLabel -> {
            }
            is NoticeHolder.License -> holder.bind((entity as Entity.License).license)
        }
    }
}