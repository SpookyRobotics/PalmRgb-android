package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveColor.SaveColorDialog

@Subcomponent
interface SaveColorDialogSubcomponent : AndroidInjector<SaveColorDialog> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SaveColorDialog>()

}
