package nyc.jsjrobotics.palmrgb.service.remoteInterface

enum class RequestType(val rpcFunction: String, val isSavedRemoteAction: Boolean ) {
    LEFT_IDLE("SHOW_IDLE_LEFT", true),
    RIGHT_IDLE("SHOW_IDLE_RIGHT", true),
    CLEAR_SCREEN("CLEAR_SCREEN", true),
    CHECK_CONNECTION ("CONNECTION_CHECK", false),
    DISPLAY_FRAME("DISPLAY_FRAME", false);

}