package net.meilcli.librarian.plugin

import net.meilcli.librarian.plugin.entities.License
import net.meilcli.librarian.plugin.internal.Placeholder

open class LibrarianLicenseExtension(val name: String) {

    var url: String = Placeholder.url

    fun toLicense(): License {
        return License(name, url)
    }
}