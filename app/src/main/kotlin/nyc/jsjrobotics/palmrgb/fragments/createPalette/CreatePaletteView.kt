package nyc.jsjrobotics.palmrgb.fragments.createPalette

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.SubActivityToolbar
import nyc.jsjrobotics.palmrgb.dataStructures.ColorOption
import nyc.jsjrobotics.palmrgb.inflate
import javax.inject.Inject

class CreatePaletteView @Inject constructor(private val createColorSubview : CreateColorSubview,
                                            private val chooseColorSubview : ChooseColorSubview){
    lateinit var rootXml: View

    fun initView(activity: Activity, container: ViewGroup, savedInstanceState: Bundle?) {
        rootXml = container.inflate(R.layout.fragment_create_palette)
        val toolbar : SubActivityToolbar = rootXml.findViewById(R.id.toolbar)
        toolbar.setNavigateUpActivity(activity)
        createColorSubview.init(rootXml)
        chooseColorSubview.init(rootXml)
    }

    fun onCreateColor(): Observable<Boolean> = createColorSubview.onCreateColorSelected
    fun getCreateColorInput(): Int = createColorSubview.getCreateColorInput()
    fun onAddColor(): Observable<ColorOption> = chooseColorSubview.onAddColorSelected()

    fun refreshSavedColors() {
        chooseColorSubview.refreshSavedColors()
    }
}
