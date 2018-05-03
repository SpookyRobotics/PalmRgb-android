package nyc.jsjrobotics.palmrgb.service.remoteInterface

import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.dataStructures.RgbFrame


object HackdayLightsInterface {
    fun startRequest(request: RequestType) {
        val application = Application.instance()
        val intent = HackdayLightsBackend.intent(request)
        application.startService(intent)
    }

    fun upload(frame: RgbFrame) {
        upload(frame.colorList)
    }

    fun upload(rgbValues: List<Int>) {
        val application = Application.instance()
        val intent = HackdayLightsBackend.uploadIntent(rgbValues)
        application.startService(intent)
    }
}