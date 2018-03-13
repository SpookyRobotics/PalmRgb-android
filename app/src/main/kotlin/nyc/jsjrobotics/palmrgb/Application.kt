package nyc.jsjrobotics.palmrgb

import android.app.Activity
import android.app.Service
import android.arch.persistence.room.Room
import android.support.v4.app.Fragment
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.injection.ApplicationComponent
import nyc.jsjrobotics.palmrgb.injection.ApplicationModule
import nyc.jsjrobotics.palmrgb.injection.DaggerApplicationComponent
import nyc.jsjrobotics.palmrgb.viewFrames.SavedFrameViewHolder
import javax.inject.Inject


class Application : android.app.Application(), HasActivityInjector, HasSupportFragmentInjector, HasServiceInjector {
    var injector: ApplicationComponent? = null
        private set

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    lateinit var appDatabase : AppDatabase; private set


    override fun onCreate() {
        super.onCreate()
        instance = this
        injector = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        injector!!.inject(this)
        appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, AppDatabase.FILENAME).fallbackToDestructiveMigration()
                .build()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return serviceInjector
    }

    companion object {
        private lateinit var instance: Application

        fun instance(): Application {
            return instance
        }

        fun inject(activity: Activity) {
            instance().activityInjector().inject(activity)
        }

        fun inject(fragment: Fragment) {
            instance().supportFragmentInjector().inject(fragment)
        }

        fun inject(service: Service) {
            instance().serviceInjector().inject(service)
        }

        fun inject(savedFrameViewHolder: SavedFrameViewHolder) {
            instance().injector?.inject(savedFrameViewHolder)
        }
    }
}