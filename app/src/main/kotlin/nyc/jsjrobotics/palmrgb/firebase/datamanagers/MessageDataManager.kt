package nyc.jsjrobotics.palmrgb.firebase.datamanagers

import nyc.jsjrobotics.palmrgb.database.MutableMessage
import nyc.jsjrobotics.palmrgb.firebase.constants.FirebaseDatabaseReference
import javax.inject.Inject

class MessageDataManager
@Inject constructor() {

    fun uploadMessage(message: MutableMessage) {
        message.messageId = generatePushKey()
        FirebaseDatabaseReference
                .messageReference
                .child(message.messageId)
                .setValue(message)
    }

    private fun generatePushKey(): String = FirebaseDatabaseReference.messageReference.push().key
}
