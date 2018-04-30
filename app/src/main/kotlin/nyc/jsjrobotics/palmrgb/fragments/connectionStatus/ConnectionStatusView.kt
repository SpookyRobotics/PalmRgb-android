package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.dataStructures.IpAddressInput
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class ConnectionStatusView @Inject constructor() {
    lateinit var rootXml: View
    private val connectClicked : PublishSubject<IpAddressInput> = PublishSubject.create()

    val onConnectClicked : Observable<IpAddressInput> = connectClicked
    private lateinit var connectionStatus: TextView
    private lateinit var connect : Button
    private lateinit var ipInput : EditText
    private lateinit var portInput : EditText

    fun initView(container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_connection_status)
        ipInput = rootXml.findViewById(R.id.palmrgb_ip_address)
        portInput = rootXml.findViewById(R.id.palmrgb_port)

        connectionStatus = rootXml.findViewById(R.id.connection_status)
        connect = rootXml.findViewById(R.id.connect_button)
        connect.setOnClickListener {
            val input = ipInput.editableText.toString()
            val port = portInput.editableText.toString()
            val ip = IpAddressInput(input, port)
            connectClicked.onNext(ip)
        }
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

    fun displayCheckingConnection() {
        connectionStatus.text = rootXml.context.getString(R.string.checking_connection)
    }


}
