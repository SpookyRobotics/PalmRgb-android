package nyc.jsjrobotics.palmrgb.service.remoteInterface

enum class RequestType(val rpcFunction: String) {
    LEFT_IDLE("SHOW_IDLE_LEFT"),
    RIGHT_IDLE("SHOW_IDLE_RIGHT"),
    CLEAR_SCREEN("CLEAR_SCREEN"),
    CHECK_CONNECTION ("CONNECTION_CHECK")
}