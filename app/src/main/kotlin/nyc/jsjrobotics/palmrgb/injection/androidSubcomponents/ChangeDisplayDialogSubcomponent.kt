package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.dialogs.changeDisplay.ChangeDisplayDialog

@Subcomponent
interface ChangeDisplayDialogSubcomponent : AndroidInjector<ChangeDisplayDialog> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ChangeDisplayDialog>()

}
