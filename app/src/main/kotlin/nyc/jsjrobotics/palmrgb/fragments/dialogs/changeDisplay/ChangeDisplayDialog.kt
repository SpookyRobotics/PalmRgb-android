package nyc.jsjrobotics.palmrgb.fragments.dialogs.changeDisplay

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.fragments.dialogs.DialogFragmentWithPresenter
import javax.inject.Inject

class ChangeDisplayDialog : DialogFragmentWithPresenter() {
    @Inject
    lateinit var model : ChangeDisplayDialogModel

    companion object {
        val TAG = "ChangeDisplayDialog"
    }

    override fun tag(): String = TAG

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.select_rgb_display)
                .setPositiveButton(R.string.sixty_four_diodes, buildLargeMatrixButtonHandler())
                .setNegativeButton(R.string.thirty_two_diodes, buildSmallMatrixButtonHandler())
        return builder.create()
    }

    private fun buildSmallMatrixButtonHandler(): DialogInterface.OnClickListener? {
        return DialogInterface.OnClickListener { dialog, id ->
            model.requestChangeDisplay(false)
            dialog.dismiss()
        }
    }


    private fun buildLargeMatrixButtonHandler(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, id ->
            model.requestChangeDisplay(true)
            dialog.dismiss()
        }
    }

}

