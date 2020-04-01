package net.meilcli.librarian

interface INotices {

    val title: String

    val description: String?

    val notices: List<INotice>
}