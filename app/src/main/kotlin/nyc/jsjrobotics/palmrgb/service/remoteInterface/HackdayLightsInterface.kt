package nyc.jsjrobotics.palmrgb.service.remoteInterface

import nyc.jsjrobotics.palmrgb.Application


object HackdayLightsInterface {
    fun startRequest(request: RequestType) {
        val application = Application.instance()
        val intent = HackdayLightsBackend.intent(request)
        application.startService(intent)
    }
}