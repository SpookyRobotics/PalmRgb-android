package nyc.jsjrobotics.palmrgb

import android.app.Service
import android.content.Intent
import android.os.IBinder
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.database.MutableRgbFrame
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class PalmRgbBackground : Service() {

    @Inject
    lateinit var appDatabase : AppDatabase

    companion object {
        val EXTRA_RGB_MATRIX = "extra_rgb_matrix"
        val EXTRA_TITLE = "extra_title"
    }

    private val taskQueue = ArrayBlockingQueue<Runnable>(10)
    private val executor = ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, taskQueue)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val title : String? = intent?.getStringExtra(EXTRA_TITLE)
        val rgbMatrix : List<Int>? = intent?.getIntegerArrayListExtra(EXTRA_RGB_MATRIX)
        // The service is starting, due to a call to startService()
        runInBackground {
            if (title != null && rgbMatrix != null) {
                DEBUG("Saving $title")
                val frame = MutableRgbFrame(title, rgbMatrix)
                appDatabase.rgbFramesDao().insert(frame)
            }
            stopSelf(startId)
        }
        return Service.START_NOT_STICKY
    }


    override fun onCreate() {
        Application.inject(this)
        super.onCreate()
        DEBUG("Starting PalmRgbBackground")
    }


    private fun runInBackground(function: () -> Unit) {
        executor.execute(function)
    }

    override fun onDestroy() {
        super.onDestroy()
        DEBUG("Shutting down PalmRgbBackground")
    }
}