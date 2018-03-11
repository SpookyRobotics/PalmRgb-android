package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.createFrame.CreateFrameFragment

@Subcomponent
interface CreateFrameFragmentSubcomponent : AndroidInjector<CreateFrameFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CreateFrameFragment>()

}
