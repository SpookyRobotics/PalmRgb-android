package nyc.jsjrobotics.palmrgb

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject


class CreateFrameFragment : DefaultFragment() {

    companion object {
        val TAG = "CreateFrameFragment"
    }

    @Inject
    lateinit var view: CreateFrameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view.initView(container!!)
        return view.rootXml
    }
}
