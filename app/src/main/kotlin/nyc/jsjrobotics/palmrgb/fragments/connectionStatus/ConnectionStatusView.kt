package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class ConnectionStatusView @Inject constructor() {
    lateinit var rootXml: View
    private lateinit var connectionStatus: TextView

    fun initView(container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_connection_status)
        connectionStatus = rootXml.findViewById(R.id.connection_status)
        displayConnected(false)
    }


    fun displayConnected(connected: Boolean) {
        val status : String
        if (connected) {
            status = rootXml.context.getString(R.string.connected)
        } else {
            status = rootXml.context.getString(R.string.disconnected)
        }
        connectionStatus.text = status
    }

}
