package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.fragments.dialogs.selectPalette.SelectPaletteDialog

@Subcomponent
interface SelectPaletteDialogSubcomponent : AndroidInjector<SelectPaletteDialog> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SelectPaletteDialog>()

}
