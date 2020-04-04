package net.meilcli.librarian.sample.core

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.meilcli.librarian.INotices
import net.meilcli.librarian.NoticesStyle
import net.meilcli.librarian.ParcelableNotices
import net.meilcli.librarian.serializers.NoticesReader

class MainActivity : AppCompatActivity() {

    companion object {

        private const val noticesKey = "notices"
    }

    private lateinit var notices: INotices

    @Suppress("EXPERIMENTAL_API_USAGE")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notices = if (savedInstanceState == null) {
            NoticesReader().read(this, "notices.json")
        } else {
            savedInstanceState.getParcelable<ParcelableNotices>(noticesKey)
                ?: NoticesReader().read(this, "notices.json")
        }
        noticesView.setNotices(notices)
        noticesView.setStyle(NoticesStyle().apply {
            onNoticeClicked = { Toast.makeText(this@MainActivity, "onClicked: ${it.name}", Toast.LENGTH_SHORT).show() }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(noticesKey, ParcelableNotices(notices))
    }
}
