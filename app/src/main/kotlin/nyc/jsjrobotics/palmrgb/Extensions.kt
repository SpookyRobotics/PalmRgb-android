package nyc.jsjrobotics.palmrgb

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Any.DEBUG(message: String) {
    Log.d(javaClass.simpleName, message)
}

fun Any.ERROR(message: String) {
    Log.e(javaClass.simpleName, message)
}

fun ViewGroup.inflate(layoutId: Int, attachToView : Boolean = false): View {
    val inflater = LayoutInflater.from(context)
    return inflater.inflate(layoutId, this, attachToView )
}