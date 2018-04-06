package nyc.jsjrobotics.palmrgb.fragments.createColor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentWithPresenter
import javax.inject.Inject

class CreateColorFragment : FragmentWithPresenter() {
    companion object {
        val TAG : String = "CreateColorFragment"
    }

    override fun tag(): String = TAG

    @Inject
    lateinit var view : CreateColorView

    @Inject
    lateinit var presenter : CreateColorPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view.initView(container!!, savedInstanceState)
        presenter.init(view)
        return view.rootXml
    }
}