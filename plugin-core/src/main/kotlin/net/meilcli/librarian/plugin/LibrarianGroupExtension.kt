package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.internal.PlaceHolder

class LibrarianGroupExtension(val name: String) {

    var artifacts = emptyArray<String>()
    var author: String = PlaceHolder.author
    var url: String = PlaceHolder.url
    var description: String? = null
    var licenseName: String = PlaceHolder.licenseName
    var licenseUrl: String = PlaceHolder.licenseUrl
}