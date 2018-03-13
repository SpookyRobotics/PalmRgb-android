package nyc.jsjrobotics.palmrgb.injection

import dagger.Component
import dagger.android.AndroidInjectionModule
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.viewFrames.SavedFrameViewHolder
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        FragmentModule::class,
        PresenterModule::class,
        ServiceModule::class,
        ModelModule::class
))

interface ApplicationComponent {
    fun inject(app: Application)
    fun inject(viewHolder: SavedFrameViewHolder)
}