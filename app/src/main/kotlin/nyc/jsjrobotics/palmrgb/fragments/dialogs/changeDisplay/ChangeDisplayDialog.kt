package nyc.jsjrobotics.palmrgb.fragments.dialogs.changeDisplay

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter

class ChangeDisplayDialog : DialogFragmentWithPresenter() {
    companion object {
        val TAG = "ChangeDisplayDialog"
    }

    override fun tag(): String = TAG

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.select_rgb_display)
                .setPositiveButton(R.string.sixty_four_diodes, buildPositiveButtonHandler())
                .setNegativeButton(R.string.thirty_two_diodes, buildNegativeButtonHandler())
        return builder.create()
    }

    private fun buildNegativeButtonHandler(): DialogInterface.OnClickListener? {
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

