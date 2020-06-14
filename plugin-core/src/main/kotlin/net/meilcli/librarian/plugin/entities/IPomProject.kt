package net.meilcli.librarian.plugin.entities

interface IPomProject {

    val group: String?
    val artifact: String?
    val version: String?
    val name: String?
    val description: String?
    val url: String?
    val parent: IPomParentProject?
    val organization: IPomOrganization?
    val licenses: List<IPomLicense>?
    val developers: List<IPomDeveloper>?
}