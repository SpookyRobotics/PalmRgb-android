package nyc.jsjrobotics.palmrgb.firebase.constants

import com.google.firebase.database.FirebaseDatabase


object FirebaseDatabaseReference {

    private val MESSAGES = "messages"

    val database = FirebaseDatabase.getInstance()
    val messageReference = database.getReference(MESSAGES)

}
