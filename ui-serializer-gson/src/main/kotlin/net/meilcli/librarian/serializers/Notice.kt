package net.meilcli.librarian.serializers

import net.meilcli.librarian.INotice
import net.meilcli.librarian.INoticeResource

data class Notice(
    override val name: String,
    override val author: String,
    override val url: String,
    override val description: String?,
    override val resources: List<INoticeResource>
) : INotice