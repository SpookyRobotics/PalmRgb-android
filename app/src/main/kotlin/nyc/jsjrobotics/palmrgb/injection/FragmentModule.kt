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
import nyc.jsjrobotics.palmrgb.injection.androidSubcomponents.RgbFrameDialogFragmentSubcomponent
import nyc.jsjrobotics.palmrgb.injection.androidSubcomponents.ViewFramesFragmentSubcomponent
import nyc.jsjrobotics.palmrgb.viewFrames.ViewFramesFragment
import nyc.jsjrobotics.palmrgb.viewFrames.dialog.RgbFrameDialogFragment
import javax.inject.Singleton

@Module(subcomponents = arrayOf(
        CreateFrameFragmentSubcomponent::class,
        ViewFramesFragmentSubcomponent::class,
        RgbFrameDialogFragmentSubcomponent::class
))
internal abstract class FragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(CreateFrameFragment::class)
    internal abstract fun bindAddFragmentInjectorFactory(builder: CreateFrameFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(ViewFramesFragment::class)
    internal abstract fun bindViewFramesFragmentInjectorFactory(builder: ViewFramesFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(RgbFrameDialogFragment::class)
    internal abstract fun bindRgbFrameDialogFragmentInjectorFactory(builder: RgbFrameDialogFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>
}
