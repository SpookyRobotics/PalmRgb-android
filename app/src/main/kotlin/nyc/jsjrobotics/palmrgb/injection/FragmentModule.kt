package nyc.jsjrobotics.palmrgb.injection

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import nyc.jsjrobotics.palmrgb.connectionStatus.ConnectionStatusFragment
import nyc.jsjrobotics.palmrgb.createColor.CreateColorFragment
import nyc.jsjrobotics.palmrgb.createFrame.CreateFrameFragment
import nyc.jsjrobotics.palmrgb.dialogs.changeDisplay.ChangeDisplayDialog
import nyc.jsjrobotics.palmrgb.dialogs.selectPalette.SelectPaletteDialog
import nyc.jsjrobotics.palmrgb.dialogs.saveFrame.SaveRgbFrameDialog
import nyc.jsjrobotics.palmrgb.injection.androidSubcomponents.*
import nyc.jsjrobotics.palmrgb.viewFrames.ViewFramesFragment
import nyc.jsjrobotics.palmrgb.viewFrames.dialog.RgbFrameDialogFragment

@Module(subcomponents = arrayOf(
        CreateFrameFragmentSubcomponent::class,
        ViewFramesFragmentSubcomponent::class,
        RgbFrameDialogFragmentSubcomponent::class,
        ConnectionStatusFragmentSubcomponent::class,
        SaveRgbFrameDialogSubcomponent::class,
        SelectPaletteDialogSubcomponent::class,
        ChangeDisplayDialogSubcomponent::class,
        CreateColorFragmentSubcomponent::class
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

    @Binds
    @IntoMap
    @FragmentKey(SaveRgbFrameDialog::class)
    internal abstract fun bindSaveRgbFrameDialogInjectorFactory(builder: SaveRgbFrameDialogSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(SelectPaletteDialog::class)
    internal abstract fun bindSelectPaletteDialogInjectorFactory(builder: SelectPaletteDialogSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(ChangeDisplayDialog::class)
    internal abstract fun bindChangeDisplayDialogInjectorFactory(builder: ChangeDisplayDialogSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(ConnectionStatusFragment::class)
    internal abstract fun bindConnectionStatusFragmentInjectorFactory(builder: ConnectionStatusFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(CreateColorFragment::class)
    internal abstract fun bindCreateColorFragmentInjectorFactory(builder: CreateColorFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

}
