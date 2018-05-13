package nyc.jsjrobotics.palmrgb.firebase.eventlisteners

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.DEBUG
import nyc.jsjrobotics.palmrgb.ERROR
import nyc.jsjrobotics.palmrgb.dataStructures.Message
import nyc.jsjrobotics.palmrgb.firebase.constants.FirebaseDatabaseReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageEventListener @Inject constructor() {

    private var isInitialLoad: Boolean = true

    private val receivedMessageSubject: PublishSubject<Message> = PublishSubject.create()

    private val query: Query = FirebaseDatabaseReference
            .messageReference
            .limitToLast(1)

    private val childEventListener: ChildEventListener =
            query.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    if (isInitialLoad) {
                        isInitialLoad = false
                    } else {
                        processSnapshot(dataSnapshot)
                    }
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {}

                override fun onCancelled(databaseError: DatabaseError) {
                    ERROR("onCancelled Called : $databaseError")
                }
            })

    private fun processSnapshot(dataSnapshot: DataSnapshot) {
        val message = dataSnapshot.getValue(Message::class.java)
        message?.let {
            DEBUG("Message received from ${it.senderId} : ${it.text}")
            notifyMessageReceived(message)
        }
    }

    private fun notifyMessageReceived(receivedMessage: Message) {
        receivedMessageSubject.onNext(receivedMessage)
    }

    fun onMessageReceived(): Observable<Message> {
        return receivedMessageSubject
    }

    fun removeListener() {
        DEBUG("Removing childEventListener...")
        FirebaseDatabaseReference.messageReference.removeEventListener(childEventListener)
    }

}
