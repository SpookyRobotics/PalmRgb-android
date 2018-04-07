package nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import javax.inject.Inject

class SaveColorDialog : DialogFragmentWithPresenter() {
    companion object {
        val TAG = "SaveColorDialog"
    }

    @Inject
    lateinit var model: SaveColorDialogModel
    private lateinit var displayedDialog : AlertDialog

    override fun tag(): String = TAG

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val builder = AlertDialog.Builder(activity)
        val view = inflater.inflate(R.layout.dialog_enter_name, null)
        val editText = view.findViewById<EditText>(R.id.save_name)
        editText.hint = activity!!.getString(R.string.color_name)

        builder.setView(view)
                .setMessage(R.string.enter_color_name)
                .setPositiveButton(R.string.create_color, buildPositiveButtonHandler())
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
        return displayedDialog.findViewById<EditText>(R.id.save_name).text.toString()
    }
}

