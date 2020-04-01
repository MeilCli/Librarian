package net.meilcli.librarian

interface INotice {

    val artifacts: List<String>

    val name: String

    val author: String

    val url: String

    val description: String?

    val licenses: List<ILicense>
}