package nyc.jsjrobotics.palmrgb.fragments.createMessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentWithPresenter
import javax.inject.Inject

class CreateMessageFragment : FragmentWithPresenter() {
    companion object {
        val TAG = "CreateMessageFragment"
    }

    @Inject
    lateinit var view: CreateMessageView

    @Inject
    lateinit var presenter: CreateMessagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view.initView(container!!, savedInstanceState)
        presenter.init(view)
        lifecycle.addObserver(view)
        return view.rootXml
    }

    override fun tag(): String = TAG
}