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
    private val libraryGroupToNoticeTranslator: ITranslator<LibraryGroup, Notice>,
    private val noticeOverride: IOverride<Notice>,
    private val overrideNoticeValidator: IValidator2<Notice, Notice>
) : IAggregator3<Collection<Artifact>, List<Library>, List<LibraryGroup>, List<Notice>> {

    private val logger = LoggerFactory.getLogger(ArtifactsToNoticesAggregator::class.java)

    override fun aggregate(source1: Collection<Artifact>, source2: List<Library>, source3: List<LibraryGroup>): List<Notice> {
        return source1
            .map { noticeArtifact ->
                val foundLibrary = source2.find { it.artifact == noticeArtifact.artifact }
                    ?: throw LibrarianException("Librarian not found library data: ${noticeArtifact.group}:${noticeArtifact.name}")
                val foundLibraryGroup = source3.find { it.artifacts.contains(noticeArtifact.artifact) }

                var notice = libraryToNoticeTranslator.translate(foundLibrary)
                if (foundLibraryGroup != null) {
                    val libraryGroupNotice = libraryGroupToNoticeTranslator.translate(foundLibraryGroup)
                    if (overrideNoticeValidator.valid(notice, libraryGroupNotice).not()) {
                        if (extension.failOnOverrideUnMatchedLicense) {
                            throw LibrarianException("group has not artifact license, ${foundLibraryGroup.name}, ${foundLibrary.artifact}")
                        } else {
                            logger.warn("Librarian warning: group has not artifact license, ${foundLibraryGroup.name}, ${foundLibrary.artifact}")
                        }
                    }
                    notice = noticeOverride.override(notice, libraryGroupNotice)
                }
                notice
            }
            .distinct()
    }
}