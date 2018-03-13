package nyc.jsjrobotics.palmrgb.injection

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground

@Subcomponent
interface PalmRgbBackgroundSubcomponent : AndroidInjector<PalmRgbBackground> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PalmRgbBackground>()
}
