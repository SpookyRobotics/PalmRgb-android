package nyc.jsjrobotics.palmrgb.injection

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import nyc.jsjrobotics.palmrgb.MainActivity
import nyc.jsjrobotics.palmrgb.injection.androidSubcomponents.MainActivitySubcomponent

@Module(subcomponents = arrayOf(
        MainActivitySubcomponent::class

))
abstract class ActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>

}
