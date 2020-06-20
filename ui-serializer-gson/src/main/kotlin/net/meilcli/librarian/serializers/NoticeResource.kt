package net.meilcli.librarian.serializers

import net.meilcli.librarian.ILicense
import net.meilcli.librarian.INoticeResource

data class NoticeResource(
    override val artifacts: List<String>,
    override val licenses: List<ILicense>
) : INoticeResource