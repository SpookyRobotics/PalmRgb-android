package nyc.jsjrobotics.palmrgb

import android.os.Bundle
import android.support.v4.app.Fragment

/***
 * A fragment that injects dependencies before onCreate
 */
abstract class DefaultFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Application.inject(this)
        super.onCreate(savedInstanceState)
    }


}
