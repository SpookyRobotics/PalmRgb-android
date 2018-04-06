package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentWithPresenter
import nyc.jsjrobotics.palmrgb.fragments.createFrame.CreateFrameFragment
import javax.inject.Inject


class ConnectionStatusFragment : FragmentWithPresenter() {

    companion object {
        val TAG = "ConnectionStatusFragment"
    }

    @Inject
    lateinit var view : ConnectionStatusView

    @Inject
    lateinit var presenter : ConnectionStatusPresenter

    override fun tag(): String = CreateFrameFragment.TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view.initView(container!!, savedInstanceState)
        presenter.init(view, onHiddenChanged)
        return view.rootXml
    }
}