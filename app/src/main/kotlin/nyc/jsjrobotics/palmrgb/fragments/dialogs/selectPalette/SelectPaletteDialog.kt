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
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import nyc.jsjrobotics.palmrgb.globalState.SavedPaletteModel
import javax.inject.Inject

class SelectPaletteDialog : DialogFragmentWithPresenter() {
    companion object {
        val TAG = "SelectPaletteDialog"
    }

    override fun tag(): String = TAG

    @Inject
    lateinit var model : SelectPaletteModel

    @Inject
    lateinit var savedPaletteModel : SavedPaletteModel

    var allPaletteList: List<Palette> = emptyList()

    lateinit var spinner : Spinner

    lateinit var selectPaletteAdapter : ArrayAdapter<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val builder = AlertDialog.Builder(activity)
        val view = inflater.inflate(R.layout.dialog_select_palette, null)
        builder.setView(view)
                .setTitle(R.string.select_a_palette)
                .setNegativeButton(R.string.cancel, buildNegativeButtonHandler())

        spinner = view.findViewById(R.id.palette_options)
        val currentOptions = savedPaletteModel.loadedPaletteList()
        if (currentOptions.isEmpty()) {
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
        val paletteList = mutableListOf(savedPaletteModel.getStandardPalette())
        allPaletteList = paletteList
        paletteList.addAll(savedPaletteModel.loadedPaletteList())
        val paletteArray = paletteList.map { it.name }.toTypedArray()
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

    private fun buildPositiveButtonHandler(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, id ->
            if (allPaletteList.isEmpty()) {
                return@OnClickListener
            }
            val paletteName = spinner.selectedItem as String
            val selection = allPaletteList.filter { it.name == paletteName }.firstOrNull()
            selection?.let { model.selectPalette(it) }
            dialog.dismiss()
        }
    }

}

