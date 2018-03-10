package nyc.jsjrobotics.palmrgb.injection

import dagger.Component
import dagger.android.AndroidInjectionModule
import nyc.jsjrobotics.palmrgb.Application
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        FragmentModule::class,
        PresenterModule::class,
        ModelModule::class
))

interface ApplicationComponent {
    fun inject(app: Application)
}