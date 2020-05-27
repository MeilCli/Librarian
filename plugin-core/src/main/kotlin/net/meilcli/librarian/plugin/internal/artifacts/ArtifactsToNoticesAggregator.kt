package net.meilcli.librarian.plugin.internal.artifacts

import net.meilcli.librarian.plugin.LibrarianExtension
import net.meilcli.librarian.plugin.entities.Artifact
import net.meilcli.librarian.plugin.entities.Library
import net.meilcli.librarian.plugin.entities.LibraryGroup
import net.meilcli.librarian.plugin.entities.Notice
import net.meilcli.librarian.plugin.internal.*
import org.slf4j.LoggerFactory

class ArtifactsToNoticesAggregator(
    private val extension: LibrarianExtension,
    private val libraryToNoticeTranslator: ITranslator<Library, Notice>,
    private val librariesToNoticeTranslator: ITranslator<List<Library>, Notice>,
    private val libraryGroupToNoticeTranslator: ITranslator<LibraryGroup, Notice>,
    private val noticeOverride: IOverride<Notice>,
    private val overrideNoticeValidator: IValidator2<Notice, Notice>
) : IAggregator3<Collection<Artifact>, List<Library>, List<LibraryGroup>, List<Notice>> {

    private class AggregateGroupResult {

        private val mutableGroups = mutableMapOf<LibraryGroup, MutableList<Artifact>>()
        val groups: Map<LibraryGroup, List<Artifact>>
            get() = mutableGroups

        private val mutableNonGroups = mutableListOf<Artifact>()
        val nonGroups: List<Artifact>
            get() = mutableNonGroups

        fun add(group: LibraryGroup, artifact: Artifact) {
            mutableGroups.getOrPut(group) { mutableListOf() }
                .add(artifact)
        }

        fun add(artifact: Artifact) {
            mutableNonGroups += artifact
        }
    }

    private val logger = LoggerFactory.getLogger(ArtifactsToNoticesAggregator::class.java)

    override fun aggregate(source1: Collection<Artifact>, source2: List<Library>, source3: List<LibraryGroup>): List<Notice> {
        val aggregateGroupResult = aggregateGroup(source1, source3)
        val result = mutableListOf<Notice>()

        result += aggregateGroupResult.groupToNotices(source2)
        result += aggregateGroupResult.nonGroups.map { libraryToNoticeTranslator.translate(it.findLibrary(source2)) }

        return result.distinct()
    }

    private fun Artifact.findLibrary(source: List<Library>): Library {
        return source.find { it.artifact == this.artifact }
            ?: throw LibrarianException("Librarian not found library data: ${this.group}:${this.name}")
    }

    private fun Artifact.findLibraryGroup(source: List<LibraryGroup>): LibraryGroup? {
        return source.find { it.artifacts.contains(this.artifact) }
    }

    private fun aggregateGroup(source1: Collection<Artifact>, source2: List<LibraryGroup>): AggregateGroupResult {
        val result = AggregateGroupResult()

        for (element in source1) {
            val libraryGroup = element.findLibraryGroup(source2)
            if (libraryGroup != null) {
                result.add(libraryGroup, element)
            } else {
                result.add(element)
            }
        }

        return result
    }

    private fun AggregateGroupResult.groupToNotices(source: List<Library>): List<Notice> {
        fun Map.Entry<LibraryGroup, List<Artifact>>.overrideLicenseTranslate(): Notice {
            var notice = librariesToNoticeTranslator.translate(value.map { it.findLibrary(source) })
            val libraryGroupNotice = libraryGroupToNoticeTranslator.translate(key)
            if (overrideNoticeValidator.valid(notice, libraryGroupNotice).not()) {
                if (extension.failOnOverrideUnMatchedLicense) {
                    throw LibrarianException("group has not artifact license, ${key.name}, ${notice.artifacts.joinToString()}, source: ${notice.licenses.joinToString()}, group: ${libraryGroupNotice.licenses.joinToString()}")
                } else {
                    logger.warn("Librarian warning: group has not artifact license, ${key.name}, ${notice.artifacts.joinToString()}, source: ${notice.licenses.joinToString()}, group: ${libraryGroupNotice.licenses.joinToString()}")
                }
                notice = noticeOverride.override(notice, libraryGroupNotice)
            }
            return notice
        }

        fun Map.Entry<LibraryGroup, List<Artifact>>.nonOverrideLicenseTranslate(): List<Notice> {
            val licenseGroups = value.map { it.findLibrary(source) }
                .groupBy { it.licenses }
            return licenseGroups.map {
                val notice = librariesToNoticeTranslator.translate(it.value)
                val libraryGroupNotice = libraryGroupToNoticeTranslator.translate(key)
                noticeOverride.override(notice, libraryGroupNotice)
            }
        }

        return this.groups.flatMap {
            if (it.key.licenses?.isEmpty() != false) {
                it.nonOverrideLicenseTranslate()
            } else {
                listOf(it.overrideLicenseTranslate())
            }
        }
    }
}