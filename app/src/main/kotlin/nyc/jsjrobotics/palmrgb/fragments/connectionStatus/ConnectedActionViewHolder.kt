package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.inflate
import nyc.jsjrobotics.palmrgb.service.remoteInterface.RequestType

class ConnectedActionViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(createView(parent)){
    private val action : Button = itemView.findViewById(R.id.connected_action)

    fun bind(requestType: RequestType, onClicked : () -> Unit) {
        action.text = requestType.name
        action.setOnClickListener { onClicked.invoke() }
    }


    companion object {
        fun createView(parent: ViewGroup): View {
            return parent.inflate(R.layout.view_holder_connected_action)
        }

    }
}
