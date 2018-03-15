package nyc.jsjrobotics.palmrgb.service.remoteInterface

import retrofit2.Call
import retrofit2.http.*

interface HackDayLightsApi {
    @GET(Paths.RAINBOW_PATH)
    fun triggerFunction(@Header("FUNCTION" ) function: String): Call<StatusResponse>

    @GET(Paths.RAINBOW_PATH)
    fun connectionCheck(): Call<String>

}

