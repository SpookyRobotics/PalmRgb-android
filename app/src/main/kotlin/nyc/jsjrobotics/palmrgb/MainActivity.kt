package nyc.jsjrobotics.palmrgb

import android.app.Activity
import android.os.Bundle


class MainActivity : DefaultActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showFragment(FragmentId.CREATE_FRAME_FRAGMENT)
        }
    }
}