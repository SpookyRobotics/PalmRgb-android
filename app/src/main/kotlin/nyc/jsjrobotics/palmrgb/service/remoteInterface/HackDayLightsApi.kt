package nyc.jsjrobotics.palmrgb.service.remoteInterface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface HackDayLightsApi {
    @GET(Paths.RAINBOW_RPC_PATH)
    fun triggerFunction(@Header("FUNCTION" ) function: String): Call<String>

    @GET(Paths.RAINBOW_PATH)
    fun connectionCheck(): Call<String>

    @GET(Paths.RAINBOW_DISPLAY_FRAME)
    fun displayFrame(@Header("FRAME_DATA") frameData : String): Call<String>
}

