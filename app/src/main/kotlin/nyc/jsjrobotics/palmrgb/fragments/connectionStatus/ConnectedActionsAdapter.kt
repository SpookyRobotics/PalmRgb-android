package nyc.jsjrobotics.palmrgb.fragments.connectionStatus

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.service.remoteInterface.RequestType
import javax.inject.Inject

class ConnectedActionsAdapter @Inject constructor() : RecyclerView.Adapter<ConnectedActionViewHolder>() {
    private val connectedActions : List<RequestType> = RequestType.values().filter { it != RequestType.CHECK_CONNECTION }
    private val connectedActionSelected : PublishSubject<RequestType> = PublishSubject.create()
    val onConnectedActionSelected : Observable<RequestType> = connectedActionSelected
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectedActionViewHolder {
        return ConnectedActionViewHolder(parent)
    }

    override fun getItemCount(): Int = connectedActions.size

    override fun onBindViewHolder(holder: ConnectedActionViewHolder, position: Int) {
        val request : RequestType = connectedActions[position]
        holder.bind(request, {connectedActionSelected.onNext(request)})
    }

}
