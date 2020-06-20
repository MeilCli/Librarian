package net.meilcli.librarian

interface INotice {

    val name: String

    val author: String

    val url: String

    val description: String?

    val resources: List<INoticeResource>
}