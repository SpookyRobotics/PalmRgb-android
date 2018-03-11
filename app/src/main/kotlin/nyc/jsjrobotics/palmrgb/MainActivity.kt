package nyc.jsjrobotics.palmrgb

import android.os.Bundle
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultActivity
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentId


class MainActivity : DefaultActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showFragment(FragmentId.CREATE_FRAME_FRAGMENT)
        }
    }
}