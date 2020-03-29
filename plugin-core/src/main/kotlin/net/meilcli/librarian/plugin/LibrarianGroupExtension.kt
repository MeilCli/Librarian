package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.internal.Placeholder

class LibrarianGroupExtension(val name: String) {

    var artifacts = emptyArray<String>()
    var author: String = Placeholder.author
    var url: String = Placeholder.url
    var description: String? = null
    var licenseName: String = Placeholder.licenseName
    var licenseUrl: String = Placeholder.licenseUrl
}