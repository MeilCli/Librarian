package net.meilcli.librarian.plugin.internal.notices

import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.IValidator2
import net.meilcli.librarian.plugin.internal.Placeholder

class LicenseOverrideNoticeValidator : IValidator2<Notice, Notice> {

    override fun valid(element1: Notice, element2: Notice): Boolean {
        if (element2.resources.isEmpty()) {
            // no override
            return true
        }
        if (element2.resources.all { it.licenses.isEmpty() }) {
            // no override
            return true
        }
        if (element1.resources.size != element2.resources.size) {
            return false
        }
        for ((licenses1, licenses2) in element1.resources.map { it.licenses }.zip(element2.resources.map { it.licenses })) {
            if (licenses1.isNotEmpty()) {
                // check same licenses
                for (checkLicense in licenses1) {
                    if (checkLicense.name == Placeholder.licenseName || checkLicense.url == Placeholder.licenseUrl) {
                        continue
                    }
                    if (licenses2.contains(checkLicense).not()) {
                        // not found license in override licenses
                        return false
                    }
                }
            }
        }
        val element1Artifacts = element1.resources.flatMap { it.artifacts }
        val element2Artifacts = element2.resources.flatMap { it.artifacts }
        if (element1Artifacts.all { element2Artifacts.contains(it) }.not()) {
            return false
        }

        return true
    }
}