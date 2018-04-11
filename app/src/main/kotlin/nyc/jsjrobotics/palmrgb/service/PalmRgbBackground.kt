package nyc.jsjrobotics.palmrgb.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import nyc.jsjrobotics.palmrgb.*
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.database.MutableColorOption
import nyc.jsjrobotics.palmrgb.database.MutablePalette
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
        val FUNCTION_SAVE_COLOR = "save_color_function"
        val EXTRA_FUNCTION = "extra_function"
        val FUNCTION_SAVE_RGB_FRAME = "save_rgb_frame_function"
        val EXTRA_COLOR_NAME = "extra_color_name"
        val EXTRA_COLOR_TO_SAVE = "extra_color_to_save"
        val FUNCTION_SAVE_PALETTE = "save_palette_function"
        val EXTRA_PALETTE_NAME = "extra_palette_name"
        val EXTRA_PALETTE_COLORS_TO_SAVE = "extra_palette_colors"
    }

    private val taskQueue = ArrayBlockingQueue<Runnable>(10)
    private val executor = ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, taskQueue)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return Service.START_NOT_STICKY
        }
        val function = intent.getStringExtra(EXTRA_FUNCTION)
        when (function) {
            FUNCTION_SAVE_RGB_FRAME -> saveRgbFrame(intent, startId)
            FUNCTION_SAVE_COLOR -> saveColor(intent, startId)
            FUNCTION_SAVE_PALETTE -> savePalette(intent, startId)
        }
        return Service.START_NOT_STICKY
    }

    private fun savePalette(intent: Intent, startId: Int) {
        val paletteName : String = intent.getStringExtra(PalmRgbBackground.EXTRA_PALETTE_NAME)
        val colorsToSave : IntArray = intent.getIntArrayExtra(PalmRgbBackground.EXTRA_PALETTE_COLORS_TO_SAVE)
        runInBackground {
            DEBUG("Saving palette $paletteName")
            val palette = MutablePalette(paletteName, colorsToSave.toMutableList())
            appDatabase.savedPalettesDao().insert(palette)
            val notification = applicationContext.getString(R.string.added_palette, paletteName)
            displayToast(notification)
            stopSelf(startId)
        }
    }

    private fun saveColor(intent: Intent, startId: Int) {
        val colorName : String = intent.getStringExtra(PalmRgbBackground.EXTRA_COLOR_NAME)
        val colorToSave : Int = intent.getIntExtra(PalmRgbBackground.EXTRA_COLOR_TO_SAVE, 0)
        runInBackground {
            DEBUG("Saving color $colorName")
            val colorOption = MutableColorOption(colorName, colorToSave)
            appDatabase.savedColorsDao().insert(colorOption)
            val notification = applicationContext.getString(R.string.added_color, colorName)
            displayToast(notification)
            stopSelf(startId)
        }
    }

    private fun displayToast(notification: String) {
        runOnMainThread {
            Toast.makeText(applicationContext, notification, Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveRgbFrame(intent: Intent, startId: Int) {
        val title : String? = intent.getStringExtra(EXTRA_TITLE)
        val rgbMatrix : List<Int>? = intent.getIntegerArrayListExtra(EXTRA_RGB_MATRIX)
        // The service is starting, due to a call to startService()
        runInBackground {
            if (title != null && rgbMatrix != null) {
                DEBUG("Saving RGB Frame $title")
                val frame = MutableRgbFrame(title, rgbMatrix)
                val alreadyExistingTitles = appDatabase.savedColorsDao().getAll().map { it.title }
                if (alreadyExistingTitles.contains(title)) {
                    ERROR("Attempt to insert duplicate title $title")
                    return@runInBackground
                }
                appDatabase.rgbFramesDao().insert(frame)
            }
            stopSelf(startId)
        }
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