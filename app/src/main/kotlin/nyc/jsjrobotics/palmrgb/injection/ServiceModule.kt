package nyc.jsjrobotics.palmrgb.injection

import android.app.Service
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ServiceKey
import dagger.multibindings.IntoMap
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground

@Module(subcomponents = arrayOf(
        PalmRgbBackgroundSubcomponent::class

))
abstract class ServiceModule {
    @Binds
    @IntoMap
    @ServiceKey(PalmRgbBackground::class)
    internal abstract fun bindBrokenHungryBackgroundInjectorFactory(builder: PalmRgbBackgroundSubcomponent.Builder): AndroidInjector.Factory<out Service>
}
