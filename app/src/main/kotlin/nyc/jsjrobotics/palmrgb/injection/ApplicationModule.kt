package nyc.jsjrobotics.palmrgb.injection

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application) {
    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    fun provideResources(application: Application) : Resources = application.resources

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application) : AppDatabase = application.appDatabase

    @Provides
    @Singleton
    fun provideConnectivityManager(application: Application) : ConnectivityManager {
        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}

