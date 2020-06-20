package net.meilcli.librarian

interface INoticeResource {

    val artifacts: List<String>

    val licenses: List<ILicense>
}