package net.meilcli.librarian.plugin

class LibrarianGroupExtension(val name: String) {

    var artifacts = emptyArray<String>()
    var author: String? = null
    var url: String? = null
    var description: String? = null
    var licenseName: String? = null
    var licenseUrl: String? = null
}