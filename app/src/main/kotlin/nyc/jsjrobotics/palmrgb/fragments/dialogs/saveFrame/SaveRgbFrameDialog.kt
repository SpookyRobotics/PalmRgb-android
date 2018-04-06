package nyc.jsjrobotics.palmrgb.fragments.dialogs.saveFrame

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import javax.inject.Inject

class SaveRgbFrameDialog : DialogFragmentWithPresenter() {
    companion object {
        val TAG = "SaveRgbFrameDialog"
    }

    @Inject
    lateinit var model: SaveRgbFrameDialogModel
    private lateinit var displayedDialog : AlertDialog

    override fun tag(): String = TAG

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val builder = AlertDialog.Builder(activity)
        val view = inflater.inflate(R.layout.dialog_save_rgb_frame, null)
        builder.setView(view)
                .setMessage(R.string.select_a_frame_name)
                .setPositiveButton(R.string.create_frame, buildPositiveButtonHandler())
                .setNegativeButton(R.string.cancel, buildNegativeButtonHandler())
        displayedDialog = builder.create()
        return displayedDialog
    }

    private fun buildNegativeButtonHandler(): DialogInterface.OnClickListener? {
        return DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
        }
    }

    private fun buildPositiveButtonHandler(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, id ->
            model.requestSaveCurrentFrame(getTitle())
            dialog.dismiss()
        }
    }

    private fun getTitle(): String {
        return displayedDialog.findViewById<EditText>(R.id.new_frame_name).text.toString()
    }
}

