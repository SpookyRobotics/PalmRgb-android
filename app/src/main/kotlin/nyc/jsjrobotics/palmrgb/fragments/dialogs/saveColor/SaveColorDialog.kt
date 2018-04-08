package nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import nyc.jsjrobotics.palmrgb.runOnMainThread
import javax.inject.Inject

class SaveColorDialog : DialogFragmentWithPresenter() {
    companion object {
        val TAG = "SaveColorDialog"
    }

    @Inject
    lateinit var model: SaveColorDialogModel

    @Inject
    lateinit var appDatabase: AppDatabase

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
            executeInThread {
                val alreadyExistingTitles = appDatabase.savedColorsDao().getAll().map { it.title }
                val title = getTitle()
                if (alreadyExistingTitles.contains(title)) {
                    displayAlreadyExistsError()
                    return@executeInThread
                }
                model.requestSaveColorOption(getTitle())
                dialog.dismiss()
            }
        }
    }

    private fun displayAlreadyExistsError() {
        runOnMainThread {
            Toast.makeText(context, R.string.color_name_already_exists, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTitle(): String {
        return displayedDialog.findViewById<EditText>(R.id.save_name).text.toString()
    }
}

