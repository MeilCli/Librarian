package net.meilcli.librarian.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.meilcli.librarian.INotices
import net.meilcli.librarian.INoticesReader
import net.meilcli.librarian.NoticesStyle
import net.meilcli.librarian.ParcelableNotices
import net.meilcli.librarian.views.NoticesView

open class NoticesFragment : Fragment() {

    companion object {

        private const val noticesReaderKey = "noticesReader"
        private const val noticesFileKey = "noticesFile"
        private const val noticesKey = "notices"

        fun createArguments(noticesReader: INoticesReader, noticesFile: String): Bundle {
            return Bundle().apply {
                putSerializable(noticesReaderKey, noticesReader)
                putString(noticesFileKey, noticesFile)
            }
        }

        fun create(noticesReader: INoticesReader, noticesFile: String): NoticesFragment {
            return NoticesFragment().apply {
                arguments = createArguments(noticesReader, noticesFile)
            }
        }
    }

    private var notices: INotices? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notices, container, false)
    }

    open fun createStyle(): NoticesStyle {
        return NoticesStyle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noticesReader = checkNotNull(arguments?.getSerializable(noticesReaderKey) as? INoticesReader) {
            "must set noticesReader to arguments"
        }
        val notices = if (savedInstanceState == null) {
            noticesReader.read(requireContext(), checkNotNull(arguments?.getString(noticesFileKey)))
        } else {
            savedInstanceState.getParcelable<ParcelableNotices>(noticesKey)
                ?: noticesReader.read(requireContext(), checkNotNull(arguments?.getString(noticesFileKey)))
        }
        this.notices = notices

        val noticesView = view.findViewById<NoticesView>(R.id.notices_view)
        noticesView.setStyle(createStyle())
        noticesView.setNotices(notices)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        notices = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val notices = notices ?: return
        outState.putParcelable(noticesKey, ParcelableNotices(notices))
    }
}