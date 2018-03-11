package nyc.jsjrobotics.palmrgb.androidInterfaces

import android.content.Context
import android.support.v4.app.FragmentActivity

interface IDefaultActivity {
    fun applicationContext(): Context
    fun getActivity(): FragmentActivity
    fun finish()
}