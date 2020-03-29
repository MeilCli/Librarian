package net.meilcli.librarian.plugin

class LibrarianGroupExtension(val name: String) {

    var artifacts = emptyArray<String>()
    var author: String = "PUT AUTHOR"
    var url: String = "PUT URL"
    var description: String? = null
    var licenseName: String = "PUT LICENSE NAME"
    var licenseUrl: String = "PUT LICENSE URL"
}