package nyc.jsjrobotics.palmrgb.viewFrames.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.dialogs.DialogFragmentWithPresenter
import javax.inject.Inject


class RgbFrameDialogFragment() : DialogFragmentWithPresenter() {
    @Inject
    lateinit var presenter: RgbFrameDialogPresenter

    @Inject
    lateinit var view: RgbFrameDialogView

    companion object {

        val FRAME_NAME: String = "args.frameName"

        fun buildArguments(frameName: Long): Bundle {
            val args = Bundle()
            args.putLong(FRAME_NAME, frameName)
            return args
        }

        val TAG: String = "RECIPE_DIALOG_FRAGMENT"
    }

    override fun tag(): String = TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view.init(inflater, container)
        presenter.init(view, getFrameName())
        return view.rootXml
    }

    private fun getFrameName(): Long? {
        arguments?.let {
            return it.getLong(FRAME_NAME)
        }
        return null
    }

}