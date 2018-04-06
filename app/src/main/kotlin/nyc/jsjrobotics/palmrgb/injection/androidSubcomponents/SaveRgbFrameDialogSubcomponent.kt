package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.fragments.dialogs.saveFrame.SaveRgbFrameDialog

@Subcomponent
interface SaveRgbFrameDialogSubcomponent : AndroidInjector<SaveRgbFrameDialog> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SaveRgbFrameDialog>()

}
