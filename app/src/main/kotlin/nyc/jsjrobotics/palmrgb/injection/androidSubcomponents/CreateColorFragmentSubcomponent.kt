package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.createColor.CreateColorFragment
import nyc.jsjrobotics.palmrgb.viewFrames.dialog.RgbFrameDialogFragment

@Subcomponent
interface CreateColorFragmentSubcomponent : AndroidInjector<CreateColorFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CreateColorFragment>()

}
