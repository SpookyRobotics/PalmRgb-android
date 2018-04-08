package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.SubActivityToolbar
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class CreatePaletteView @Inject constructor(){
    lateinit var rootXml: View

    private lateinit var createColorSubview : CreateColorSubview
    private lateinit var chooseColorSubview : ChooseColorSubview

    fun initView(activity: Activity, container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_create_palette)
        val toolbar : SubActivityToolbar = rootXml.findViewById(R.id.toolbar)
        toolbar.setNavigateUpActivity(activity)
        createColorSubview = CreateColorSubview(rootXml)
        chooseColorSubview = ChooseColorSubview(rootXml)
    }

    fun createColorSelected(): Observable<Boolean> = createColorSubview.onCreateColorSelected
    fun getCreateColorInput(): Int = createColorSubview.getCreateColorInput()
}
