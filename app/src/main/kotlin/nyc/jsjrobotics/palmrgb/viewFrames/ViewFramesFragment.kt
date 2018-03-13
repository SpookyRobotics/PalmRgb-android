package nyc.jsjrobotics.palmrgb.viewFrames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultFragment
import javax.inject.Inject


class ViewFramesFragment : DefaultFragment() {
    companion object {
        val TAG = "ViewFramesFragment"
    }

    @Inject
    lateinit var view: ViewFramesView

    @Inject
    lateinit var presenter: ViewFramesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view.initView(container!!, savedInstanceState)
        presenter.init(view, onHiddenChanged)
        return view.rootXml
    }
}