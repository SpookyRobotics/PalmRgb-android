package nyc.jsjrobotics.palmrgb.viewFrames.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject


class RgbFrameDialogFragment() : DialogFragmentWithPresenter() {

    @Inject
    lateinit var presenter: RgbFrameDialogPresenter

    @Inject
    lateinit var view: RgbFrameDialogView

    companion object {

        val FRAME_NAME: String = "args.frameName"

        fun buildArguments(frameName: String): Bundle {
            val args = Bundle()
            args.putString(FRAME_NAME, frameName)
            return args
        }

        val TAG: String = "RECIPE_DIALOG_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view.init(inflater, container)
        return view.rootXml
    }

}