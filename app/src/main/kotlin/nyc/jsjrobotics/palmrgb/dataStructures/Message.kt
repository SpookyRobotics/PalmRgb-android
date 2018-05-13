package nyc.jsjrobotics.palmrgb.dataStructures

import nyc.jsjrobotics.palmrgb.database.MutableMessage


data class Message(
        val text: String = EMPTY_MESSAGE,
        val senderId: String = UNKNOWN_ID,
        val messageId: String = UNKNOWN_ID,
        val timeStamp: Long = System.currentTimeMillis()
) {

    fun mutable(): MutableMessage {
        return MutableMessage(
                text,
                senderId,
                messageId,
                timeStamp
        )
    }

    companion object {
        private val UNKNOWN_ID = "not_set"
        private val EMPTY_MESSAGE = "empty_message"
    }

}



