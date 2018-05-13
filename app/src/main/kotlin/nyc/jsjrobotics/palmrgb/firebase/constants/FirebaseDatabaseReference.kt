package nyc.jsjrobotics.palmrgb.firebase.constants

import com.google.firebase.database.FirebaseDatabase


object FirebaseDatabaseReference {

    val database = FirebaseDatabase.getInstance()
    val messageReference = database.getReference(FirebasePath.MESSAGES)

}
