package net.meilcli.librarian.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import net.meilcli.librarian.INotice
import net.meilcli.librarian.NoticeStyle
import net.meilcli.librarian.ParcelableNotice
import net.meilcli.librarian.views.NoticeView

class NoticeActivity : AppCompatActivity() {

    companion object {

        private const val noticeKey = "notice"

        fun createIntent(context: Context, notice: INotice): Intent {
            return Intent(context, NoticeActivity::class.java).apply {
                putExtra(noticeKey, ParcelableNotice(notice))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        val notice = intent.getParcelableExtra<ParcelableNotice>(noticeKey)
            ?: throw IllegalStateException("must use createIntent() when launch activity")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = notice.name

        val noticeView = findViewById<NoticeView>(R.id.notice_view)
        noticeView.setNotice(notice)
        noticeView.setStyle(NoticeStyle().apply {
            showName = false
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