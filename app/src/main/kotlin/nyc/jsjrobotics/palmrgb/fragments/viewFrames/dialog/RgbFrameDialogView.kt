package nyc.jsjrobotics.palmrgb.fragments.viewFrames.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.androidInterfaces.DialogUtil
import nyc.jsjrobotics.palmrgb.androidInterfaces.IDefaultActivity
import nyc.jsjrobotics.palmrgb.customViews.XmlDiodeArray
import nyc.jsjrobotics.palmrgb.database.MutableRgbFrame
import javax.inject.Inject

class RgbFrameDialogView @Inject constructor(val diodeArray: XmlDiodeArray) {

    lateinit var rootXml: View
    private lateinit var title: TextView
    private lateinit var displayButton: Button
    private lateinit var deleteButton: Button
    private val displayFrame : PublishSubject<MutableRgbFrame> = PublishSubject.create()
    private val deleteFrame : PublishSubject<MutableRgbFrame> = PublishSubject.create()
    val onDisplayFrameClicked : Observable<MutableRgbFrame> = displayFrame
    val onDeleteFrameClicked : Observable<MutableRgbFrame> = deleteFrame

    fun init(inflater: LayoutInflater, container: ViewGroup?) {
        rootXml = inflater.inflate(R.layout.rgb_frame_dialog, container, false)
        title = rootXml.findViewById(R.id.frame_title)
        diodeArray.setView(rootXml.findViewById(R.id.saved_rgb_matrix))
        diodeArray.setDiodesClickable(false)
        displayButton = rootXml.findViewById(R.id.display)
        deleteButton = rootXml.findViewById(R.id.delete)
    }

    fun dismiss(tag: String, activity: IDefaultActivity) {
        DialogUtil.dismissDialog(activity.getActivity(), tag)
    }

    fun setData(frame: MutableRgbFrame) {
        val frameId = frame.frameId!!
        title.text = frame.frameName
        diodeArray.showColors(frame.colorList)
        displayButton.setOnClickListener { displayFrame.onNext(frame) }
        deleteButton.setOnClickListener { deleteFrame.onNext(frame) }
    }
}