package net.meilcli.librarian.serializers

import net.meilcli.librarian.ILicense
import net.meilcli.librarian.INotice

data class Notice(
    override val artifacts: List<String>,
    override val name: String,
    override val author: String,
    override val url: String,
    override val description: String?,
    override val licenses: List<ILicense>
) : INotice