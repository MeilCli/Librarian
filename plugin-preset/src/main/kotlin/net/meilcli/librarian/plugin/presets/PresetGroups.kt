package net.meilcli.librarian.plugin.presets

import net.meilcli.librarian.plugin.entities.LibraryGroup

object PresetGroups {

    private val mutableGroups = mutableListOf<LibraryGroup>()
    val groups: List<LibraryGroup>
        get() = mutableGroups

    operator fun plusAssign(group: LibraryGroup) {
        mutableGroups += group
    }

    init {
        kotlin()
        android()
        librarian()
        junit()
        square()
        bintray()
        xmlUtil()
        immutables()
        google()
        java()
        bumptech()
        coil()
        koin()
        ktor()
        fuel()
        facebook()
        amazon()
        gmazzo()
        realm()
    }
}