package nyc.jsjrobotics.palmrgb.androidInterfaces


import android.os.Bundle
import android.support.v4.app.Fragment
import nyc.jsjrobotics.palmrgb.BuildConfig
import nyc.jsjrobotics.palmrgb.fragments.connectionStatus.ConnectionStatusFragment
import nyc.jsjrobotics.palmrgb.fragments.createColor.CreateColorFragment
import nyc.jsjrobotics.palmrgb.fragments.createFrame.CreateFrameFragment
import nyc.jsjrobotics.palmrgb.fragments.createMessage.CreateMessageFragment
import nyc.jsjrobotics.palmrgb.fragments.createPalette.CreatePaletteFragment
import nyc.jsjrobotics.palmrgb.fragments.viewFrames.ViewFramesFragment
import nyc.jsjrobotics.palmrgb.fragments.viewFrames.dialog.RgbFrameDialogFragment

/**
 * All fragments in the app should be instantiated by calling a FragmentId.supplier
 */
enum class FragmentId(
        val tag: String,
        val supplier: (fragmentArguments: Bundle?) -> Fragment
) {
    CREATE_FRAME_FRAGMENT(
            CreateFrameFragment.TAG,
            { addArguments(CreateFrameFragment(), it) }
    ),

    RGB_FRAME_DIALOG_FRAGMENT(
            RgbFrameDialogFragment.TAG,
            { addArguments(RgbFrameDialogFragment(), it)}
    ),

    VIEW_FRAMES_FRAGMENT(
            ViewFramesFragment.TAG,
            { addArguments(ViewFramesFragment(), it) }
    ),
    CONNECTION_STATUS(
            ConnectionStatusFragment.TAG,
            { addArguments(ConnectionStatusFragment(), it) }
    ),
    CREATE_COLOR(
            CreateColorFragment.TAG,
            { addArguments(CreateColorFragment(), it)}
    ),
    CREATE_PALETTE(
            CreatePaletteFragment.TAG,
            { addArguments(CreatePaletteFragment(), it)}
    ),
    CREATE_MESSAGE(
            CreateMessageFragment.TAG,
            { addArguments(CreateMessageFragment(), it)}
    );

    companion object {
        init {
            if (BuildConfig.DEBUG) {
                val ids = FragmentId.values()
                val tagList = ids.map { it.tag }
                tagList.forEach { id ->
                    val fragmentsWithId = ids.filter { id.equals(it.tag) }
                    if (fragmentsWithId.size > 1) {
                        throw IllegalStateException("Each fragment id must have a unique tag. Duplicated: $id")
                    }
                }
            }
        }

        private fun addArguments(fragment: Fragment, bundle: Bundle?): Fragment {
            fragment.arguments = bundle
            return fragment
        }
    }
}
