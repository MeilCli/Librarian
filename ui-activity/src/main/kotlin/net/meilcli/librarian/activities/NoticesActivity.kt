package net.meilcli.librarian.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.meilcli.librarian.INotices
import net.meilcli.librarian.INoticesReader
import net.meilcli.librarian.NoticesStyle
import net.meilcli.librarian.ParcelableNotices
import net.meilcli.librarian.views.NoticesView

class NoticesActivity : AppCompatActivity() {

    companion object {

        private const val noticesKey = "notices"
        private const val noticesFileKey = "noticesFile"
        private const val noticesReaderKey = "noticesReader"

        fun createIntent(context: Context, noticesReader: INoticesReader, noticesFile: String): Intent {
            return Intent(context, NoticesActivity::class.java).apply {
                putExtra(noticesReaderKey, noticesReader)
                putExtra(noticesFileKey, noticesFile)
            }
        }
    }

    private lateinit var notices: INotices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notices)

        val noticesReader = checkNotNull(intent.getSerializableExtra(noticesReaderKey) as? INoticesReader) { "must set noticesReader" }

        notices = if (savedInstanceState == null) {
            noticesReader.read(this, checkNotNull(intent.getStringExtra(noticesFileKey)) { "must set noticesFile" })
        } else {
            savedInstanceState.getParcelable<ParcelableNotices>(noticesKey)
                ?: noticesReader.read(this, checkNotNull(intent.getStringExtra(noticesFileKey)) { "must set noticesFile" })
        }

        val noticesView = findViewById<NoticesView>(R.id.notices_view)
        noticesView.setNotices(notices)
        noticesView.setStyle(NoticesStyle().apply {
            onNoticeClicked = { startActivity(NoticeActivity.createIntent(this@NoticesActivity, it)) }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(noticesKey, ParcelableNotices(notices))
    }
}