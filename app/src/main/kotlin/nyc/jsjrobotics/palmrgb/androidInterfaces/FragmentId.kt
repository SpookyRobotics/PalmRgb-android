package nyc.jsjrobotics.palmrgb.androidInterfaces


import android.os.Bundle
import android.support.v4.app.Fragment
import nyc.jsjrobotics.palmrgb.BuildConfig
import nyc.jsjrobotics.palmrgb.fragments.createColor.CreateColorFragment
import nyc.jsjrobotics.palmrgb.fragments.connectionStatus.ConnectionStatusFragment
import nyc.jsjrobotics.palmrgb.fragments.createFrame.CreateFrameFragment
import nyc.jsjrobotics.palmrgb.fragments.createPalette.CreatePaletteFragment
import nyc.jsjrobotics.palmrgb.fragments.viewFrames.ViewFramesFragment
import nyc.jsjrobotics.palmrgb.fragments.viewFrames.dialog.RgbFrameDialogFragment

enum class FragmentId(
        val tag: String,
        val supplier: (fragmentArguments: Bundle?) -> Fragment
) {
    CREATE_FRAME_FRAGMENT(
            CreateFrameFragment.TAG,
            { addArgments(CreateFrameFragment(), it) }
    ),

    RGB_FRAME_DIALOG_FRAGMENT(
            RgbFrameDialogFragment.TAG,
            { addArgments(RgbFrameDialogFragment(), it)}
    ),

    VIEW_FRAMES_FRAGMENT(
            ViewFramesFragment.TAG,
            { addArgments(ViewFramesFragment(), it) }
    ),
    CONNECTION_STATUS(
            ConnectionStatusFragment.TAG,
            { addArgments(ConnectionStatusFragment(), it) }
    ),
    CREATE_COLOR(
            CreateColorFragment.TAG,
            { addArgments(CreateColorFragment(), it)}
    ),
    CREATE_PALETTE(
            CreatePaletteFragment.TAG,
            { addArgments(CreatePaletteFragment(), it)}
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

        private fun addArgments(fragment: Fragment, bundle: Bundle?): Fragment {
            fragment.arguments = bundle
            return fragment
        }
    }
}
