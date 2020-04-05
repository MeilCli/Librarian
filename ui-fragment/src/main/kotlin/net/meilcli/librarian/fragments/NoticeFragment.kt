package net.meilcli.librarian.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.meilcli.librarian.INotice
import net.meilcli.librarian.NoticeStyle
import net.meilcli.librarian.ParcelableNotice
import net.meilcli.librarian.views.NoticeView

open class NoticeFragment : Fragment() {

    companion object {

        private const val noticeKey = "notice"

        fun createArguments(notice: INotice): Bundle {
            return Bundle().apply {
                putParcelable(noticeKey, ParcelableNotice(notice))
            }
        }

        fun create(notice: INotice): NoticeFragment {
            return NoticeFragment().apply {
                arguments = createArguments(notice)
            }
        }
    }

    private var notice: INotice? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notice, container, false)
    }

    open fun createStyle(): NoticeStyle {
        return NoticeStyle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notice = if (savedInstanceState == null) {
            checkNotNull(arguments?.getParcelable<ParcelableNotice>(noticeKey)) {
                "must set notice to arguments"
            }
        } else {
            savedInstanceState.getParcelable<ParcelableNotice>(noticeKey)
                ?: checkNotNull(arguments?.getParcelable<ParcelableNotice>(noticeKey))
        }
        this.notice = notice

        val noticeView = view.findViewById<NoticeView>(R.id.notice_view)
        noticeView.setStyle(createStyle())
        noticeView.setNotice(notice)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        notice = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val notice = notice ?: return
        outState.putParcelable(noticeKey, ParcelableNotice(notice))
    }
}