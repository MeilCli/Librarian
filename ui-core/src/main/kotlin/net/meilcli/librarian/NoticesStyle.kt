package net.meilcli.librarian

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

class NoticesStyle {

    var noticesShowTitle = true

    @LayoutRes
    var noticesTitleHolderLayout = R.layout.holder_notices_title

    @IdRes
    var noticesTitleHolderTitleId = R.id.notices_title

    @LayoutRes
    var noticesDescriptionHolderLayout = R.layout.holder_notices_description

    @IdRes
    var noticesDescriptionHolderDescriptionId = R.id.notices_description

    @LayoutRes
    var noticesNoticeHolderLayout = R.layout.holder_notices_notice

    @IdRes
    var noticesNoticeHolderNameId = R.id.notices_notice_name

    var onNoticeClicked: (INotice) -> Unit = {}
}