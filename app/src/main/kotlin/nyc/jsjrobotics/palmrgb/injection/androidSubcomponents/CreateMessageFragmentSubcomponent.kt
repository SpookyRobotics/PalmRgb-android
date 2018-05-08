package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.fragments.createMessage.CreateMessageFragment

@Subcomponent
interface CreateMessageFragmentSubcomponent : AndroidInjector<CreateMessageFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CreateMessageFragment>()

}
