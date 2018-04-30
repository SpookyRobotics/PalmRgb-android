package nyc.jsjrobotics.palmrgb.service.remoteInterface

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import com.google.gson.GsonBuilder
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
class HackdayLightsBackend : DefaultService() {

    private var retrofit: Retrofit? = null
    private var backendApi : HackDayLightsApi? = null
    private val disposables : CompositeDisposable = CompositeDisposable()
    private val downloadsInProgress : AtomicInteger = AtomicInteger(0)

    @Inject
    lateinit var hardwareState: HardwareState

    companion object {
        private val RPC_TYPE = "RPC_TYPE"
        val CONNECTION_CHECK_RESPONSE = "CONNECTION_CHECK_RESPONSE"

        fun connectionCheckIntent() : Intent {
            return Intent(CONNECTION_CHECK_RESPONSE)
        }

        fun intent(requestType: RequestType) : Intent {
            val intent = Intent()
            intent.component = ComponentName("nyc.jsjrobotics.palmrgb", "nyc.jsjrobotics.palmrgb.service.remoteInterface.HackdayLightsBackend")
            intent.putExtra(RPC_TYPE, requestType.name)
            return intent
        }
    }
    override fun onCreate() {
        super.onCreate()
        DEBUG("Starting HackdayLightsBackend")

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
            backendApi = retrofit?.create(HackDayLightsApi::class.java)
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
            val requestType = intent.getStringExtra(RPC_TYPE)
            startRequestThread(requestType)
        }
        return START_NOT_STICKY
    }

    private fun startRequestThread(request: String) {
        downloadsInProgress.getAndIncrement()
        val requestType : RequestType? = RequestType.values().firstOrNull() { it.name == request }
        if (requestType == null) {
            return
        }
        Thread {
            if (requestType == RequestType.CHECK_CONNECTION) {
                connectionCheck()
            } else {
                val rpcFunction = requestType.rpcFunction
                rainbowRequest(rpcFunction)
            }
        }.start()

    }

    private fun connectionCheck() {
        backendApi?.apply {
            val request = connectionCheck()
            try {
                val response = request.execute()
                DEBUG("Result: ${response.isSuccessful}")
                broadcastConnectionCheckResult(response.isSuccessful)
            } catch (e : Exception) {
                ERROR("Failed to connection check: $e")
                broadcastConnectionCheckResult(false)
            }
        } ?: broadcastConnectionCheckResult(false)

        checkStopSelf()
    }

    private fun broadcastConnectionCheckResult(successful: Boolean) {
        val intent = connectionCheckIntent()
        intent.putExtra(CONNECTION_CHECK_RESPONSE, successful)
        LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcastSync(intent)
    }

    private fun rainbowRequest(rpcFunction: String) {
        backendApi?.apply {
            val request = triggerFunction(rpcFunction)
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