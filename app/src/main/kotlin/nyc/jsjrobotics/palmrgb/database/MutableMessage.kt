package nyc.jsjrobotics.palmrgb.database

import nyc.jsjrobotics.palmrgb.dataStructures.Message


data class MutableMessage(
        var text: String,
        var senderId: String,
        var messageId: String,
        var timeStamp: Long
) {

    fun immutable(): Message {
        return Message(
                text,
                senderId,
                messageId,
                timeStamp
        )
    }
}



