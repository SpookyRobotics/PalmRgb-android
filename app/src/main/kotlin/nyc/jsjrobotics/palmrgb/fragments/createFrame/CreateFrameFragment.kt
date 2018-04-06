package nyc.jsjrobotics.palmrgb.fragments.createFrame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentWithPresenter
import javax.inject.Inject


class CreateFrameFragment : FragmentWithPresenter() {

    companion object {
        val TAG = "CreateFrameFragment"
    }

    @Inject
    lateinit var view: CreateFrameView

    @Inject
    lateinit var presenter: CreateFramePresenter

    override fun tag(): String = TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view.initView(container!!, savedInstanceState)
        presenter.init(fragmentManager!!, view)
        return view.rootXml
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        view.writeStateToModel()
    }
}