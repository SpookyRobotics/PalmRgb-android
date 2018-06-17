package nyc.jsjrobotics.palmrgb.fragments.wheeledPlatform

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class WheeledPlatformFragment : Fragment() {
    lateinit var presenter : WheeledPlatformPresenter
    lateinit var view : WheeledPlatformView

    companion object {
        val TAG = "WheeledPlatformFragment"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = WheeledPlatformPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = WheeledPlatformView(container!!)
        return view.rootXml
    }
}