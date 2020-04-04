package net.meilcli.librarian.plugin

import java.io.File

open class LibrarianPageExtension(val name: String) {

    var title = name
    var description: String? = null
    var markdown = true
    var markdownFileName = "README.md"
    var json = true
    var jsonFileName = "notices.json"
    var jsonAdditionalOutputPath: File? = null
    var configurations = mutableListOf<String>()
}