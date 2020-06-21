package net.meilcli.librarian.sample.custom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_custom_notice.*
import net.meilcli.librarian.INotice
import net.meilcli.librarian.NoticeStyle
import net.meilcli.librarian.ParcelableNotice

class CustomNoticeActivity : AppCompatActivity() {

    companion object {

        private const val noticeKey = "notice"

        fun createIntent(context: Context, notice: INotice): Intent {
            return Intent(context, CustomNoticeActivity::class.java).apply {
                putExtra(noticeKey, ParcelableNotice(notice))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_notice)

        val notice = intent.getParcelableExtra<ParcelableNotice>(noticeKey)
            ?: throw IllegalStateException("must use createIntent() when launch activity")

        noticeView.setNotice(notice)
        noticeView.setStyle(NoticeStyle().apply {
            nameHolderLayout = R.layout.holder_custom_notice_name
            nameHolderNameId = R.id.custom_notice_name
            licenseLabelHolderLayout = R.layout.holder_custom_notice_license_label
            artifactLabelHolderLayout = R.layout.holder_custom_notice_artifact_label
            onLicenseClicked = { launchUrl(it.url) }
            onUrlClicked = { launchUrl(it) }
        })
    }

    private fun launchUrl(url: String) {
        applicationContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}