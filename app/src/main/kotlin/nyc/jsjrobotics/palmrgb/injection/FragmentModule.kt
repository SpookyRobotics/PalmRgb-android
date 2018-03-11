package nyc.jsjrobotics.palmrgb.injection

import android.support.v4.app.Fragment

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import nyc.jsjrobotics.palmrgb.createFrame.CreateFrameDialogModel
import nyc.jsjrobotics.palmrgb.createFrame.CreateFrameFragment
import nyc.jsjrobotics.palmrgb.injection.androidSubcomponents.CreateFrameFragmentSubcomponent
import javax.inject.Singleton

@Module(subcomponents = arrayOf(CreateFrameFragmentSubcomponent::class))
internal abstract class FragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(CreateFrameFragment::class)
    internal abstract fun bindAddFragmentInjectorFactory(builder: CreateFrameFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>
}
