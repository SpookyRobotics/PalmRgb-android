package nyc.jsjrobotics.palmrgb.injection

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nyc.jsjrobotics.palmrgb.service.lightsRemoteInterface.HackdayLightsBackend

@Subcomponent
interface HackdayLightsBackendSubcomponent : AndroidInjector<HackdayLightsBackend> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<HackdayLightsBackend>()
}
