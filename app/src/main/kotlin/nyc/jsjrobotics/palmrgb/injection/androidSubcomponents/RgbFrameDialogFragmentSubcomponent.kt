package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.viewFrames.dialog.RgbFrameDialogFragment

@Subcomponent
interface RgbFrameDialogFragmentSubcomponent : AndroidInjector<RgbFrameDialogFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<RgbFrameDialogFragment>()

}
