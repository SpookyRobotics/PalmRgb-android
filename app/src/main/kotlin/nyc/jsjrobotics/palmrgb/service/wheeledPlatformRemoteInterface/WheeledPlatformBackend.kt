package nyc.jsjrobotics.palmrgb.service.wheeledPlatformRemoteInterface

import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.DEBUG
import nyc.jsjrobotics.palmrgb.ERROR
import nyc.jsjrobotics.palmrgb.service.DefaultService
import nyc.jsjrobotics.palmrgb.service.HardwareState
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject


/**
 * Service to download values from HackdayLightsBackend.
 * Always stop self after completing tasks, and do not allow binding
 */
class WheeledPlatformBackend : DefaultService() {

    private var retrofit: Retrofit? = null
    private val downloadsInProgress : AtomicInteger = AtomicInteger(0)
    private var backendApi : WheeledPlatformApi? = null
    private val disposables : CompositeDisposable = CompositeDisposable()
    private val requestMap : Map<RequestType, () -> Unit > = mapOf(
            Pair(RequestType.MOTOR_BACKWARD, { motorRequest(false)}),
            Pair(RequestType.MOTOR_FORWARD, { motorRequest(true)}),
            Pair(RequestType.MOTOR_STOP, { motorStop()}),
            Pair(RequestType.TOWER_SPIN_A, { towerRequest(true)}),
            Pair(RequestType.TOWER_SPIN_B, {towerRequest(false)}),
            Pair(RequestType.TOWER_STOP, { towerStop()})
    )

    @Inject
    lateinit var hardwareState: HardwareState

    companion object {
        private val RPC_TYPE = "RPC_TYPE"
        fun intent(requestType: RequestType) : Intent {
            val intent = Intent().apply {
                component = serviceComponentName()
                putExtra(RPC_TYPE, requestType.name)
            }
            return intent
        }

        private fun serviceComponentName(): ComponentName {
            return ComponentName("nyc.jsjrobotics.palmrgb", "nyc.jsjrobotics.palmrgb.service.wheeledPlatformRemoteInterface.WheeledPlatformBackend")
        }
    }
    override fun onCreate() {
        super.onCreate()
        DEBUG("Starting WheeledPlatformBackend")

        createRetrofit()
        val onUrlChanged = hardwareState.onUrlChanged.subscribe { createRetrofit() }
        disposables.add(onUrlChanged)

    }

    private fun createRetrofit() {
        val url = hardwareState.getUrl()
        if (url.isNotBlank()) {
            retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
            backendApi = retrofit?.create(WheeledPlatformApi::class.java)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        disposables.dispose()
        DEBUG("Shutting down HackdayLightsBackend")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            startRequestThread(intent)
        }
        return START_NOT_STICKY
    }

    private fun startRequestThread(intent: Intent) {

        val requestType : RequestType? = RequestType.values().firstOrNull() { it.name == intent.getStringExtra(RPC_TYPE) }
        if (requestType == null) {
            ERROR("Received WheeledPlatform request for unknown request type")
            return
        }
        Thread {
            downloadsInProgress.getAndIncrement()
            requestMap.get(requestType)?.invoke()
        }.start()

    }

    private fun removeAlpha(color: Int): Int {
        return color and 0xFFFFFF
    }


    private fun motorRequest(isForward: Boolean) {
        backendApi?.apply {
            val function = if (isForward) "FORWARD" else "BACKWARD"
            val request = motors(function, "2")
            try {
                val response = request.execute()
                DEBUG("Result:${response.body()}")
            } catch (e : Exception) {
                ERROR("Failed to trigger rainbow: $e")
            }
        }
        checkStopSelf()
    }

    private fun motorStop() {
        backendApi?.apply {
            val request = motors("STOP", "2")
            try {
                val response = request.execute()
                DEBUG("Result:${response.body()}")
            } catch (e : Exception) {
                ERROR("Failed to trigger rainbow: $e")
            }
        }
        checkStopSelf()
    }

    private fun towerStop() {
        backendApi?.apply {
            val request = tower("STOP", "2")
            try {
                val response = request.execute()
                DEBUG("Result:${response.body()}")
            } catch (e : Exception) {
                ERROR("Failed to trigger rainbow: $e")
            }
        }
        checkStopSelf()
    }

    private fun towerRequest(isSpinA: Boolean) {
        backendApi?.apply {
            val function = if (isSpinA) "SPIN_A" else "SPIN_B"
            val request = tower(function, "2")
            try {
                val response = request.execute()
                DEBUG("Result:${response.body()}")
            } catch (e : Exception) {
                ERROR("Failed to trigger rainbow: $e")
            }
        }
        checkStopSelf()
    }

    private fun checkStopSelf() {
        if (downloadsInProgress.decrementAndGet() == 0) {
            stopSelf()
        }
    }
}