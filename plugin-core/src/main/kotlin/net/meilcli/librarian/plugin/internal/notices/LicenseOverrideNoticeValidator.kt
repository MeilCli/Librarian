package net.meilcli.librarian.plugin.internal.notices

import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.IValidator2
import net.meilcli.librarian.plugin.internal.Placeholder

class LicenseOverrideNoticeValidator : IValidator2<Notice, Notice> {

    override fun valid(element1: Notice, element2: Notice): Boolean {
        if (element2.licenses.isEmpty()) {
            // no override
            return true
        }
        if (element1.licenses.isNotEmpty()) {
            // check same licenses
            for (checkLicense in element1.licenses) {
                if (checkLicense.name == Placeholder.licenseName || checkLicense.url == Placeholder.licenseUrl) {
                    continue
                }
                if (element2.licenses.contains(checkLicense).not()) {
                    // not found license in override licenses
                    return false
                }
            }
        }

        return true
    }
}