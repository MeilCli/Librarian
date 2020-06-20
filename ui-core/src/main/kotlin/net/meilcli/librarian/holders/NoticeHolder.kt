package net.meilcli.librarian.holders

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.meilcli.librarian.ILicense
import net.meilcli.librarian.NoticeStyle

sealed class NoticeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class Name(
        parent: ViewGroup,
        style: NoticeStyle
    ) : NoticeHolder(LayoutInflater.from(parent.context).inflate(style.nameHolderLayout, parent, false)) {

        private val name = itemView.findViewById<TextView>(style.nameHolderNameId)

        fun bind(name: String) {
            this.name.text = name
        }
    }

    class Description(
        parent: ViewGroup,
        style: NoticeStyle
    ) : NoticeHolder(LayoutInflater.from(parent.context).inflate(style.descriptionHolderLayout, parent, false)) {

        private val description = itemView.findViewById<TextView>(style.descriptionHolderDescriptionId)

        fun bind(description: String) {
            this.description.text = description
        }
    }

    class Author(
        parent: ViewGroup,
        style: NoticeStyle
    ) : NoticeHolder(LayoutInflater.from(parent.context).inflate(style.authorHolderLayout, parent, false)) {

        private val author = itemView.findViewById<TextView>(style.authorHolderAuthorId)

        @SuppressLint("SetTextI18n")
        fun bind(author: String) {
            this.author.text = "Author: $author"
        }
    }

    class Url(
        parent: ViewGroup,
        private val style: NoticeStyle
    ) : NoticeHolder(LayoutInflater.from(parent.context).inflate(style.urlHolderLayout, parent, false)) {

        private val url = itemView.findViewById<TextView>(style.urlHolderUrlId)

        fun bind(url: String) {
            this.url.text = url
            itemView.setOnClickListener {
                style.onUrlClicked(url)
            }
        }
    }

    class LicenseLabel(
        parent: ViewGroup,
        style: NoticeStyle
    ) : NoticeHolder(LayoutInflater.from(parent.context).inflate(style.licenseLabelHolderLayout, parent, false))

    class License(
        parent: ViewGroup,
        private val style: NoticeStyle
    ) : NoticeHolder(LayoutInflater.from(parent.context).inflate(style.licenseHolderLayout, parent, false)) {

        private val license = itemView.findViewById<TextView>(style.licenseHolderLicenseId)

        fun bind(license: ILicense) {
            this.license.text = license.name
            itemView.setOnClickListener {
                style.onLicenseClicked(license)
            }
        }
    }

    class ArtifactLabel(
        parent: ViewGroup,
        style: NoticeStyle
    ) : NoticeHolder(LayoutInflater.from(parent.context).inflate(style.artifactLabelHolderLayout, parent, false))

    class Artifact(
        parent: ViewGroup,
        style: NoticeStyle
    ) : NoticeHolder(LayoutInflater.from(parent.context).inflate(style.artifactHolderLayout, parent, false)) {

        private val artifact = itemView.findViewById<TextView>(style.artifactHolderArtifactId)

        fun bind(artifact: String) {
            this.artifact.text = artifact
        }
    }

    class ResourceDivider(
        parent: ViewGroup,
        private val style: NoticeStyle
    ) : NoticeHolder(LayoutInflater.from(parent.context).inflate(style.resourceDividerHolderLayout, parent, false)) {

        private val divider = itemView.findViewById<View>(style.resourceDividerHolderDividerId)

        fun bind() {
            this.divider.setBackgroundResource(style.resourceDividerHolderDividerDrawable)
        }
    }
}