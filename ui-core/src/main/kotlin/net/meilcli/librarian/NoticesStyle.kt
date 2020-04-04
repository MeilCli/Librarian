package net.meilcli.librarian

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

class NoticesStyle {

    var noticesShowTitle = true

    @LayoutRes
    var titleHolderLayout = R.layout.holder_notices_title

    @IdRes
    var titleHolderTitleId = R.id.notices_title

    @LayoutRes
    var descriptionHolderLayout = R.layout.holder_notices_description

    @IdRes
    var descriptionHolderDescriptionId = R.id.notices_description

    @LayoutRes
    var noticeHolderLayout = R.layout.holder_notices_notice

    @IdRes
    var noticeHolderNameId = R.id.notices_notice_name

    var onNoticeClicked: (INotice) -> Unit = {}
}