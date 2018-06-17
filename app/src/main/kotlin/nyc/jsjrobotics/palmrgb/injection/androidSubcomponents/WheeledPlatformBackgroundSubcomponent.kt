package nyc.jsjrobotics.palmrgb.injection.androidSubcomponents

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground
import nyc.jsjrobotics.palmrgb.service.wheeledPlatformRemoteInterface.WheeledPlatformBackend

@Subcomponent
interface WheeledPlatformBackgroundSubcomponent : AndroidInjector<WheeledPlatformBackend> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<WheeledPlatformBackend>()
}
