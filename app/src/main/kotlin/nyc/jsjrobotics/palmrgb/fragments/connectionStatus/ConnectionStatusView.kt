package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.dataStructures.IpAddressInput
import nyc.jsjrobotics.palmrgb.globalState.SharedPreferencesManager
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class ConnectionStatusView @Inject constructor(val connectedActionsAdapter: ConnectedActionsAdapter,
                                               val wheeledPlatformActionsAdapter: WheeledPlatformActionsAdapter,
                                               val sharedPreferencesManager: SharedPreferencesManager) {
    lateinit var rootXml: View
    private val connectClicked : PublishSubject<IpAddressInput> = PublishSubject.create()
    val onConnectClicked : Observable<IpAddressInput> = connectClicked

    private val disconnectClicked : PublishSubject<Boolean> = PublishSubject.create()
    val onDisconnectClicked : Observable<Boolean> = disconnectClicked

    private val liveUpdatesClicked : PublishSubject<Boolean> = PublishSubject.create()
    val onLiveUpdatesClicked : Observable<Boolean> = liveUpdatesClicked

    private lateinit var connectionStatus: TextView
    private lateinit var connectDisconnectButton : Button
    private lateinit var ipInput : EditText
    private lateinit var portInput : EditText
    private lateinit var connectedActions : RecyclerView
    private lateinit var wheeledPlatformActions : RecyclerView
    private lateinit var liveUpdatesButton : CheckBox

    fun initView(container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_connection_status)
        ipInput = rootXml.findViewById(R.id.palmrgb_ip_address)
        portInput = rootXml.findViewById(R.id.palmrgb_port)
        connectedActions = rootXml.findViewById(R.id.connect_actions)
        connectedActions.adapter = connectedActionsAdapter
        connectedActions.layoutManager = LinearLayoutManager(rootXml.context, LinearLayoutManager.HORIZONTAL, false)

        wheeledPlatformActions = rootXml.findViewById(R.id.wheeled_platform_actions)
        wheeledPlatformActions.adapter = wheeledPlatformActionsAdapter
        wheeledPlatformActions.layoutManager = LinearLayoutManager(rootXml.context, LinearLayoutManager.HORIZONTAL, false)

        connectionStatus = rootXml.findViewById(R.id.connection_status)
        connectDisconnectButton = rootXml.findViewById(R.id.connect_button)
        liveUpdatesButton = rootXml.findViewById(R.id.live_updates)
        liveUpdatesButton.setOnCheckedChangeListener { _, isChecked -> liveUpdatesClicked.onNext(isChecked) }
        displayConnected(false)
    }


    fun displayConnected(connected: Boolean) {
        val status : String
        setupConnectDisconnectButton(connected)
        if (connected) {
            status = rootXml.context.getString(R.string.connected)
            connectedActions.visibility = View.VISIBLE
            liveUpdatesButton.visibility = View.VISIBLE
            liveUpdatesButton.isChecked = sharedPreferencesManager.getSendLiveUpdatesToHardware()
            ipInput.isFocusableInTouchMode = false
            portInput.isFocusableInTouchMode = false
            ipInput.isFocusable = false
            portInput.isFocusable = false
        } else {
            status = rootXml.context.getString(R.string.disconnected)
            connectedActions.visibility = View.GONE
            liveUpdatesButton.visibility = View.GONE
            ipInput.isFocusableInTouchMode = true
            portInput.isFocusableInTouchMode = true
        }
        connectionStatus.text = status

    }

    private fun setupConnectDisconnectButton(connected: Boolean) {
        if (connected) {
            connectDisconnectButton.text = rootXml.context.getString(R.string.disconnect)
            connectDisconnectButton.setOnClickListener {
                disconnectClicked.onNext(true)
            }
        } else {
            connectDisconnectButton.text = rootXml.context.getString(R.string.connect)
            connectDisconnectButton.setOnClickListener {
                val input = ipInput.editableText.toString()
                val port = portInput.editableText.toString()
                val ip = IpAddressInput(input, port)
                connectClicked.onNext(ip)
            }
        }
    }

    fun displayCheckingConnection() {
        connectionStatus.text = rootXml.context.getString(R.string.checking_connection)
    }

    fun onConnectedActionSelected() = connectedActionsAdapter.onConnectedActionSelected

    fun onWheeledPlatformActionSelected() = wheeledPlatformActionsAdapter.onActionSelected

}
