package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.fragments.createPalette.CreatePaletteFragment

@Subcomponent
interface CreatePaletteFragmentSubcomponent : AndroidInjector<CreatePaletteFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CreatePaletteFragment>()

}
