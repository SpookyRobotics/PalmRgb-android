package nyc.jsjrobotics.palmrgb.viewFrames.dialog

import android.content.Context
import android.support.v4.content.LocalBroadcastManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.database.MutableRgbFrame
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.runOnMainThread
import nyc.jsjrobotics.palmrgb.service.remoteInterface.HackdayLightsInterface
import nyc.jsjrobotics.palmrgb.service.remoteInterface.RequestType
import nyc.jsjrobotics.palmrgb.viewFrames.ViewFramesPresenter
import javax.inject.Inject

class RgbFrameDialogPresenter @Inject constructor(val appDatabase: AppDatabase) : DefaultPresenter(){
    private lateinit var view: RgbFrameDialogView
    private val disposables: CompositeDisposable = CompositeDisposable()

    fun init(view: RgbFrameDialogView, frameId: Long?) {
        this.view = view
        if (frameId == null) {
            return
        }
        executeInThread {
            val frame = appDatabase.rgbFramesDao().getFrame(frameId)
            if (frame == null) {
                fragmentNeeded.onNext{it.finish() }
                return@executeInThread
            }
            runOnMainThread { view.setData(frame) }
        }

        val onDisplayDisposable = view.onDisplayFrameClicked.subscribe {
                HackdayLightsInterface.startRequest(RequestType.CHECK_CONNECTION)
        }

        val onDeleteDisposable = view.onDeleteFrameClicked.subscribe(this::deleteFrame)


        disposables.addAll(onDeleteDisposable, onDisplayDisposable)
    }

    private fun deleteFrame(frame: MutableRgbFrame) {
        executeInThread {
            appDatabase.rgbFramesDao().delete(frame)

            fragmentNeeded.onNext{ fragment ->
                sendBroadcast(fragment.fragment().activity!!)
                fragment.finish()
            }
        }
    }

    private fun sendBroadcast(context: Context) {
        val broadcastManager = LocalBroadcastManager.getInstance(context.applicationContext)
        val intent = ViewFramesPresenter.updateViewFramesIntent()
        broadcastManager.sendBroadcast(intent)
    }

}
