package nyc.jsjrobotics.palmrgb.createFrame

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.EditText
import nyc.jsjrobotics.palmrgb.R

class CreateFrameDialog : DialogFragment() {

    val model : CreateFrameDialogModel = CreateFrameDialogModel.instance()
    private lateinit var displayedDialog : AlertDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater

        val builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.select_a_frame_name)
                .setPositiveButton(R.string.create_frame, { dialog, id ->
                    model.requestSaveCurrentFrame(getTitle())
                    dialog.dismiss()
                })
                .setNegativeButton(R.string.cancel, { dialog, id ->
                    dialog.dismiss()
                })
                .setView(inflater.inflate(R.layout.dialog_create_frame, null))
        displayedDialog = builder.create()
        return displayedDialog

    }

    private fun getTitle(): String {
        return displayedDialog.findViewById<EditText>(R.id.new_frame_name).text.toString()
    }

    companion object {
        val TAG = "CreateFrameDialog"
    }
}

