package net.meilcli.librarian.serializers

import net.meilcli.librarian.ILicense

data class License(
    override val name: String,
    override val url: String
) : ILicense