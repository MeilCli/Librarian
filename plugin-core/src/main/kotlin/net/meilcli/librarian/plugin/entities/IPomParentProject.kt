package net.meilcli.librarian.plugin.entities

interface IPomParentProject {

    val group: String?
    val artifact: String?
    val version: String?
}