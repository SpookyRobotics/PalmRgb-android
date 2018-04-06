package nyc.jsjrobotics.palmrgb.fragments.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.androidInterfaces.IDefaultFragment

abstract class DefaultDialogFragment : DialogFragment(), IDefaultFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        Application.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun fragment(): Fragment {
        return this
    }

    override fun finish() {
        dismiss()
    }
}