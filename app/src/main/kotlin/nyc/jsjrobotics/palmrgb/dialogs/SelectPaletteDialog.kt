package nyc.jsjrobotics.palmrgb.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import nyc.jsjrobotics.palmrgb.R

class SelectPaletteDialog : DialogFragmentWithPresenter() {
    companion object {
        val TAG = "SelectPaletteDialog"
    }

    override fun tag(): String = TAG

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater

        val builder = AlertDialog.Builder(activity)
        val view = inflater.inflate(R.layout.dialog_select_palette, null)
        builder.setView(view)
                .setMessage(R.string.select_a_palette)
                .setPositiveButton(R.string.select, buildPositiveButtonHandler())
                .setNeutralButton(R.string.create_palette, buildNeutralButtonHandler())
                .setNegativeButton(R.string.cancel, buildNegativeButtonHandler())
        return builder.create()
    }

    private fun buildNegativeButtonHandler(): DialogInterface.OnClickListener? {
        return DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
        }
    }

    private fun buildNeutralButtonHandler(): DialogInterface.OnClickListener? {
        return DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
        }
    }

    private fun buildPositiveButtonHandler(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
        }
    }

}

