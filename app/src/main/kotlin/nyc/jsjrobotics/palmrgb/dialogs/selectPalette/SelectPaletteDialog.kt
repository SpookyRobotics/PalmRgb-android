package nyc.jsjrobotics.palmrgb.dialogs.selectPalette

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import nyc.jsjrobotics.palmrgb.PaletteOptions
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import nyc.jsjrobotics.palmrgb.dialogs.DialogFragmentWithPresenter
import javax.inject.Inject

class SelectPaletteDialog : DialogFragmentWithPresenter() {
    companion object {
        val TAG = "SelectPaletteDialog"
    }

    override fun tag(): String = TAG

    @Inject
    lateinit var model : SelectPaletteModel

    @Inject
    lateinit var paletteOptions : PaletteOptions

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
            model.selectCreatePalette()
            dialog.dismiss()
        }
    }

    private fun buildPositiveButtonHandler(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, id ->
            model.selectPalette(Palette("test", listOf(0x555555)))
            dialog.dismiss()
        }
    }

}

