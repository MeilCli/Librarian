package net.meilcli.librarian.serializers

import net.meilcli.librarian.INotice
import net.meilcli.librarian.INotices

data class Notices(
    override val title: String,
    override val description: String?,
    override val notices: List<INotice>
) : INotices