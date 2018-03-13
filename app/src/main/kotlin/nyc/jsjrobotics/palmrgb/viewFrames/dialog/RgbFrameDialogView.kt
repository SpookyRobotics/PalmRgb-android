package nyc.jsjrobotics.palmrgb.viewFrames.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.androidInterfaces.DialogUtil
import nyc.jsjrobotics.palmrgb.androidInterfaces.IDefaultActivity
import javax.inject.Inject

class RgbFrameDialogView @Inject constructor() {

    lateinit var rootXml: View
    private lateinit var title: TextView
    private lateinit var servingSize: TextView
    private lateinit var recipeImage: ImageView
    private val clickSubject = PublishSubject.create<String>()
    private val DIALOG = "DIALOG"
    val clickEvent: Observable<String> = clickSubject


    fun init(inflater: LayoutInflater, container: ViewGroup?) {
        rootXml = inflater.inflate(R.layout.rgb_frame_dialog, container, false)
        rootXml.setOnClickListener { clickSubject.onNext(DIALOG) }
    }

    fun dismiss(tag: String, activity: IDefaultActivity) {
        DialogUtil.dismissDialog(activity.getActivity(), tag)
    }
}