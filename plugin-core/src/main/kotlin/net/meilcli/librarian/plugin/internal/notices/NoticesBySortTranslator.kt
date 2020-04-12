package net.meilcli.librarian.plugin.internal.notices

import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.ITranslator

class NoticesBySortTranslator : ITranslator<List<Notice>, List<Notice>> {

    override fun translate(source: List<Notice>): List<Notice> {
        return source.sortedBy { it.name }
    }
}