package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.fragments.dialogs.changeDisplay.ChangeDisplayDialog
import nyc.jsjrobotics.palmrgb.fragments.dialogs.savePalette.SavePaletteDialog

@Subcomponent
interface SavePaletteDialogFragmentSubcomponent : AndroidInjector<SavePaletteDialog> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SavePaletteDialog>()

}
