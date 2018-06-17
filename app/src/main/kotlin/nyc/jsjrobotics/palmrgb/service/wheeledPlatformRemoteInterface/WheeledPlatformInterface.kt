package nyc.jsjrobotics.palmrgb.service.wheeledPlatformRemoteInterface

import nyc.jsjrobotics.palmrgb.Application


object WheeledPlatformInterface {
    fun motorsForward() {
        val application = Application.instance()
        val intent = WheeledPlatformBackend.intent(RequestType.MOTOR_FORWARD)
        application.startService(intent)
    }

    fun motorsBackward() {
        val application = Application.instance()
        val intent = WheeledPlatformBackend.intent(RequestType.MOTOR_BACKWARD)
        application.startService(intent)
    }

    fun motorsStop() {
        val application = Application.instance()
        val intent = WheeledPlatformBackend.intent(RequestType.MOTOR_STOP)
        application.startService(intent)
    }

    fun towerSpinA() {
        val application = Application.instance()
        val intent = WheeledPlatformBackend.intent(RequestType.TOWER_SPIN_A)
        application.startService(intent)
    }

    fun towerSpinB() {
        val application = Application.instance()
        val intent = WheeledPlatformBackend.intent(RequestType.TOWER_SPIN_B)
        application.startService(intent)
    }

    fun towerStop() {
        val application = Application.instance()
        val intent = WheeledPlatformBackend.intent(RequestType.TOWER_STOP)
        application.startService(intent)
    }

    fun startRequest(request: RequestType) {
        when (request) {
            RequestType.MOTOR_FORWARD -> motorsForward()
            RequestType.MOTOR_BACKWARD -> motorsBackward()
            RequestType.MOTOR_STOP -> motorsStop()
            RequestType.TOWER_STOP -> towerStop()
            RequestType.TOWER_SPIN_B -> towerSpinB()
            RequestType.TOWER_SPIN_A -> towerSpinA()
        }
    }
}