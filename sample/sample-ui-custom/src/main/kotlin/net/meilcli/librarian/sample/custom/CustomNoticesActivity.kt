package net.meilcli.librarian.sample.custom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_custom_notices.*
import net.meilcli.librarian.INotices
import net.meilcli.librarian.INoticesReader
import net.meilcli.librarian.NoticesStyle
import net.meilcli.librarian.ParcelableNotices

class CustomNoticesActivity : AppCompatActivity() {

    companion object {

        private const val noticesKey = "notices"
        private const val noticesFileKey = "noticesFile"
        private const val noticesReaderKey = "noticesReader"

        fun createIntent(context: Context, noticesReader: INoticesReader, noticesFile: String): Intent {
            return Intent(context, CustomNoticesActivity::class.java).apply {
                putExtra(noticesReaderKey, noticesReader)
                putExtra(noticesFileKey, noticesFile)
            }
        }
    }

    private lateinit var notices: INotices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_notices)

        val noticesReader = checkNotNull(intent.getSerializableExtra(noticesReaderKey) as? INoticesReader) { "must set noticesReader" }

        notices = if (savedInstanceState == null) {
            noticesReader.read(this, checkNotNull(intent.getStringExtra(noticesFileKey)) { "must set noticesFile" })
        } else {
            savedInstanceState.getParcelable<ParcelableNotices>(noticesKey)
                ?: noticesReader.read(this, checkNotNull(intent.getStringExtra(noticesFileKey)) { "must set noticesFile" })
        }

        noticesView.setNotices(notices)
        noticesView.setStyle(NoticesStyle().apply {
            titleHolderLayout = R.layout.holder_custom_notices_title
            titleHolderTitleId = R.id.custom_notices_title
            onNoticeClicked = { startActivity(CustomNoticeActivity.createIntent(this@CustomNoticesActivity, it)) }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(noticesKey, ParcelableNotices(notices))
    }
}