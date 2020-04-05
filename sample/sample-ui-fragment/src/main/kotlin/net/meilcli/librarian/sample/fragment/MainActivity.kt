package net.meilcli.librarian.sample.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.meilcli.librarian.INotice
import net.meilcli.librarian.INoticesReader
import net.meilcli.librarian.NoticeStyle
import net.meilcli.librarian.NoticesStyle
import net.meilcli.librarian.fragments.NoticeFragment
import net.meilcli.librarian.fragments.NoticesFragment
import net.meilcli.librarian.serializers.NoticesReader

class MainActivity : AppCompatActivity() {

    class MyNoticesFragment : NoticesFragment() {

        companion object {

            fun create(noticesReader: INoticesReader, noticesFile: String): MyNoticesFragment {
                return MyNoticesFragment().apply {
                    arguments = createArguments(noticesReader, noticesFile)
                }
            }
        }

        override fun createStyle(): NoticesStyle {
            return super.createStyle().apply {
                onNoticeClicked = {
                    parentFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, MyNoticeFragment.create(it))
                        .commit()
                }
            }
        }
    }

    class MyNoticeFragment : NoticeFragment() {

        companion object {

            fun create(notice: INotice): MyNoticeFragment {
                return MyNoticeFragment().apply {
                    arguments = createArguments(notice)
                }
            }
        }

        override fun createStyle(): NoticeStyle {
            return super.createStyle().apply {
                onUrlClicked = { launchUrl(it) }
                onLicenseClicked = { launchUrl(it.url) }
            }
        }

        private fun launchUrl(url: String) {
            requireContext().applicationContext
                .startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, MyNoticesFragment.create(NoticesReader(), "notices.json"))
            .commitNow()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}
