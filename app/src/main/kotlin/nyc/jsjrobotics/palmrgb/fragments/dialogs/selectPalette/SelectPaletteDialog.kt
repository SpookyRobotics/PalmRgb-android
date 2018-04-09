package nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import nyc.jsjrobotics.palmrgb.dataStructures.PaletteOptions
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
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

    lateinit var spinner : Spinner

    lateinit var selectPaletteAdapter : ArrayAdapter<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val builder = AlertDialog.Builder(activity)
        val view = inflater.inflate(R.layout.dialog_select_palette, null)
        builder.setView(view)
                .setTitle(R.string.select_a_palette)
                .setNeutralButton(R.string.create_palette, buildNeutralButtonHandler())
                .setNegativeButton(R.string.cancel, buildNegativeButtonHandler())

        spinner = view.findViewById(R.id.palette_options)
        if (paletteOptions.currentPaletteList().isEmpty()) {
            spinner.visibility = View.GONE
        } else {
            builder.setPositiveButton(R.string.select, buildPositiveButtonHandler())
            val noPalettes = view.findViewById<TextView>(R.id.no_saved_color_palettes)
            noPalettes.visibility = View.GONE
            selectPaletteAdapter = buildAdapter(inflater.context)
            spinner.adapter = selectPaletteAdapter
        }

        return builder.create()
    }

    private fun buildAdapter(context: Context): ArrayAdapter<String> {
        val paletteArray = paletteOptions.currentPaletteList().map { it.name }.toTypedArray()
        return ArrayAdapter(context,
                R.layout.item_select_palette,
                R.id.palette_option_name,
                paletteArray)
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
            val selection = spinner.selectedItem as Palette
            model.selectPalette(selection)
            dialog.dismiss()
        }
    }

}

