package nyc.jsjrobotics.palmrgb.service

import android.app.Service
import nyc.jsjrobotics.palmrgb.Application

abstract class DefaultService : Service(){
    override fun onCreate() {
        super.onCreate()
        Application.inject(this)
    }
}
