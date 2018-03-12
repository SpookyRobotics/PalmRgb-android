package nyc.jsjrobotics.palmrgb.createFrame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultFragment
import javax.inject.Inject


class CreateFrameFragment : DefaultFragment() {

    companion object {
        val TAG = "CreateFrameFragment"
    }

    @Inject
    lateinit var view: CreateFrameView

    @Inject
    lateinit var presenter: CreateFramePresenter

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