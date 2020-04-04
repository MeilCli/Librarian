package net.meilcli.librarian

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

class NoticeStyle {

    var showName = true

    @LayoutRes
    var nameHolderLayout = R.layout.holder_notice_name

    @IdRes
    var nameHolderNameId = R.id.notice_name

    @LayoutRes
    var descriptionHolderLayout = R.layout.holder_notice_description

    @IdRes
    var descriptionHolderDescriptionId = R.id.notice_description

    @LayoutRes
    var authorHolderLayout = R.layout.holder_notice_author

    @IdRes
    var authorHolderAuthorId = R.id.notice_author

    @LayoutRes
    var urlHolderLayout = R.layout.holder_notice_url

    @IdRes
    var urlHolderUrlId = R.id.notice_url

    @LayoutRes
    var licenseLabelHolderLayout = R.layout.holder_notice_license_label

    @LayoutRes
    var licenseHolderLayout = R.layout.holder_notice_license

    @IdRes
    var licenseHolderLicenseId = R.id.notice_license

    var onUrlClicked: (String) -> Unit = {}

    var onLicenseClicked: (ILicense) -> Unit = {}
}