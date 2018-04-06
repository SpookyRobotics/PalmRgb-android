package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.fragments.viewFrames.ViewFramesFragment

@Subcomponent
interface ViewFramesFragmentSubcomponent : AndroidInjector<ViewFramesFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ViewFramesFragment>()

}
