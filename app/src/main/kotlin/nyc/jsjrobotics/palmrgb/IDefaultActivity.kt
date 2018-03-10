package nyc.jsjrobotics.palmrgb

import android.content.Context
import android.support.v4.app.FragmentActivity

interface IDefaultActivity {
    fun applicationContext(): Context
    fun getActivity(): FragmentActivity
    fun finish()
}