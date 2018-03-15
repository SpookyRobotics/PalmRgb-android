package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.connectionStatus.ConnectionStatusFragment
import nyc.jsjrobotics.palmrgb.createFrame.CreateFrameFragment

@Subcomponent
interface ConnectionStatusFragmentSubcomponent : AndroidInjector<ConnectionStatusFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ConnectionStatusFragment>()

}
