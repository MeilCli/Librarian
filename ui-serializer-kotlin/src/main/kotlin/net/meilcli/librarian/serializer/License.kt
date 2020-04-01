package net.meilcli.librarian.serializer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.meilcli.librarian.ILicense

@Serializable
data class License(

    @SerialName("name")
    override val name: String,

    @SerialName("url")
    override val url: String
) : ILicense