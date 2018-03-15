package nyc.jsjrobotics.palmrgb.connectionStatus

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class ConnectionStatusView @Inject constructor() {
    lateinit var rootXml: View
    private lateinit var toolbar: Toolbar

    fun initView(container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_connection_status)
        toolbar = rootXml.findViewById(R.id.connection_status_toolbar)
        displayConnected(false)
    }


    fun displayConnected(connected: Boolean) {
        if (connected) {
            toolbar.title = rootXml.context.getString(R.string.disconnected)
        } else {
            toolbar.title = rootXml.context.getString(R.string.connected)
        }
    }

}
