package nyc.jsjrobotics.palmrgb.injection

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import nyc.jsjrobotics.palmrgb.fragments.connectionStatus.ConnectionStatusFragment
import nyc.jsjrobotics.palmrgb.fragments.createFrame.CreateFrameFragment
import nyc.jsjrobotics.palmrgb.fragments.createPalette.CreatePaletteFragment
import nyc.jsjrobotics.palmrgb.fragments.dialogs.changeDisplay.ChangeDisplayDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette.SelectPaletteDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveFrame.SaveRgbFrameDialog
import nyc.jsjrobotics.palmrgb.injection.androidSubcomponents.*
import nyc.jsjrobotics.palmrgb.fragments.viewFrames.ViewFramesFragment
import nyc.jsjrobotics.palmrgb.fragments.viewFrames.dialog.RgbFrameDialogFragment

@Module(subcomponents = arrayOf(
        CreateFrameFragmentSubcomponent::class,
        ViewFramesFragmentSubcomponent::class,
        RgbFrameDialogFragmentSubcomponent::class,
        ConnectionStatusFragmentSubcomponent::class,
        SaveRgbFrameDialogSubcomponent::class,
        SaveColorDialogSubcomponent::class,
        SelectPaletteDialogSubcomponent::class,
        ChangeDisplayDialogSubcomponent::class,
        CreatePaletteFragmentSubcomponent::class
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
    @FragmentKey(SaveColorDialog::class)
    internal abstract fun bindSaveColorDialogInjectorFactory(builder: SaveColorDialogSubcomponent.Builder): AndroidInjector.Factory<out Fragment>


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
    @FragmentKey(CreatePaletteFragment::class)
    internal abstract fun bindCreatePaletteFragmentInjectorFactory(builder: CreatePaletteFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

}
