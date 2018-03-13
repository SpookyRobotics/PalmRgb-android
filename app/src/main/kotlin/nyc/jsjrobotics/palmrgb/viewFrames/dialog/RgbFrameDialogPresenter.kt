package nyc.jsjrobotics.palmrgb.viewFrames.dialog

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import io.reactivex.functions.Consumer
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.runOnMainThread
import nyc.jsjrobotics.palmrgb.viewFrames.ViewFramesPresenter
import javax.inject.Inject

class RgbFrameDialogPresenter @Inject constructor(val appDatabase: AppDatabase) : DefaultPresenter(){
    private lateinit var view: RgbFrameDialogView

    fun init(view: RgbFrameDialogView, frameId: Long?) {
        this.view = view
        if (frameId == null) {
            return
        }
        executeInThread {
            val frame = appDatabase.rgbFramesDao().getFrame(frameId)
            if (frame == null) {
                fragmentNeeded.onNext(Consumer { it.finish() })
                return@executeInThread
            }
            runOnMainThread( Runnable { view.setData(frame) } )
        }

        view.onDeleteFrameClicked.subscribe {
            executeInThread {
                appDatabase.rgbFramesDao().delete(it)

                fragmentNeeded.onNext(Consumer { fragment ->
                    sendBroadcast(fragment.fragment().activity!!)
                    fragment.finish()
                })
            }
        }
    }

    private fun sendBroadcast(context: Context) {
        val broadcastManager = LocalBroadcastManager.getInstance(context.applicationContext)
        val intent = ViewFramesPresenter.updateViewFramesIntent()
        broadcastManager.sendBroadcast(intent)
    }

}
