package nyc.jsjrobotics.palmrgb.androidInterfaces

import android.os.Bundle
import android.support.v4.app.Fragment
import nyc.jsjrobotics.palmrgb.Application

/***
 * A fragment that injects dependencies before onCreate
 */
abstract class DefaultFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Application.inject(this)
        super.onCreate(savedInstanceState)
    }


}
