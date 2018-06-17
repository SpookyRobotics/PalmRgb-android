package nyc.jsjrobotics.palmrgb.service.wheeledPlatformRemoteInterface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface WheeledPlatformApi {
    @GET(Paths.MOTOR_PATH)
    fun motors(@Header("FUNCTION" ) function: String, @Header("MILLISECONDS" ) seconds: String): Call<String>

    @GET(Paths.TOWER_PATH)
    fun tower(@Header("FUNCTION" ) function: String, @Header("SECONDS" ) seconds: String): Call<String>

}

