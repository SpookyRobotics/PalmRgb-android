package nyc.jsjrobotics.palmrgb.fragments.createColor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentWithPresenter
import javax.inject.Inject

class CreateColorFragment : FragmentWithPresenter() {
    override fun tag(): String = TAG

    @Inject
    lateinit var presenter: CreateColorPresenter

    @Inject
    lateinit var view : CreateColorView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view.initView(container!!, savedInstanceState)
        presenter.init(fragmentManager!!, view)
        return view.rootXml
    }

    companion object {
        val TAG = "CreateColorFragment"
    }
}
