package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

class ConnectionStatusView @Inject constructor(val connectedActionsAdapter: ConnectedActionsAdapter) {
    lateinit var rootXml: View
    private val connectClicked : PublishSubject<IpAddressInput> = PublishSubject.create()
    val onConnectClicked : Observable<IpAddressInput> = connectClicked

    private val disconnectClicked : PublishSubject<Boolean> = PublishSubject.create()
    val onDisconnectClicked : Observable<Boolean> = disconnectClicked

    private lateinit var connectionStatus: TextView
    private lateinit var connectDisconnectButton : Button
    private lateinit var ipInput : EditText
    private lateinit var portInput : EditText
    private lateinit var connectedActions : RecyclerView

    fun initView(container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_connection_status)
        ipInput = rootXml.findViewById(R.id.palmrgb_ip_address)
        portInput = rootXml.findViewById(R.id.palmrgb_port)
        connectedActions = rootXml.findViewById(R.id.connect_actions)
        connectedActions.adapter = connectedActionsAdapter
        connectedActions.layoutManager = LinearLayoutManager(rootXml.context, LinearLayoutManager.HORIZONTAL, false)
        connectionStatus = rootXml.findViewById(R.id.connection_status)
        connectDisconnectButton = rootXml.findViewById(R.id.connect_button)
        displayConnected(false)
    }


    fun displayConnected(connected: Boolean) {
        val status : String
        if (connected) {
            status = rootXml.context.getString(R.string.connected)
            connectDisconnectButton.text = rootXml.context.getString(R.string.disconnect)
            connectDisconnectButton.setOnClickListener {
                disconnectClicked.onNext(true)
            }
            connectedActions.visibility = View.VISIBLE
        } else {
            status = rootXml.context.getString(R.string.disconnected)
            connectDisconnectButton.text = rootXml.context.getString(R.string.connect)
            connectDisconnectButton.setOnClickListener {
                val input = ipInput.editableText.toString()
                val port = portInput.editableText.toString()
                val ip = IpAddressInput(input, port)
                connectClicked.onNext(ip)
            }
            connectedActions.visibility = View.GONE
        }
        connectionStatus.text = status

    }

    fun displayCheckingConnection() {
        connectionStatus.text = rootXml.context.getString(R.string.checking_connection)
    }

    fun onConnectedActionSelected() = connectedActionsAdapter.onConnectedActionSelected
}
