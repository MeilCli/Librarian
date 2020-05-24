package net.meilcli.librarian.plugin.internal.bintray

import net.meilcli.librarian.plugin.entities.BintrayLicense
import net.meilcli.librarian.plugin.internal.ILoader

class BintrayLicensesLoader : ILoader<List<BintrayLicense>?> {

    private val api = IBintrayApi.default

    override fun load(): List<BintrayLicense>? {
        return api.getLicenses().execute().body()
    }
}