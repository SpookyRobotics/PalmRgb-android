package nyc.jsjrobotics.palmrgb.fragments.viewFrames.dialog

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.support.v4.content.LocalBroadcastManager
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import nyc.jsjrobotics.palmrgb.dataStructures.RgbFrame
import nyc.jsjrobotics.palmrgb.database.AppDatabase
import nyc.jsjrobotics.palmrgb.executeInThread
import nyc.jsjrobotics.palmrgb.fragments.viewFrames.ViewFramesPresenter
import nyc.jsjrobotics.palmrgb.runOnMainThread
import nyc.jsjrobotics.palmrgb.service.lightsRemoteInterface.HackdayLightsInterface
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
                fragmentNeeded.onNext{ it.finish() }
                return@executeInThread
            }
            runOnMainThread { view.setData(frame.immutable()) }
        }

        val onDisplayDisposable = view.onDisplayFrameClicked.subscribe {
                HackdayLightsInterface.upload(it)
        }

        val onDeleteDisposable = view.onDeleteFrameClicked.subscribe(this::deleteFrame)


        disposables.addAll(onDeleteDisposable, onDisplayDisposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }

    private fun deleteFrame(frame: RgbFrame) {
        executeInThread {
            appDatabase.rgbFramesDao().delete(frame.mutable())

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
