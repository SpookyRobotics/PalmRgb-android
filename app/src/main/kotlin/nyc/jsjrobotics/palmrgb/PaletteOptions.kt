package nyc.jsjrobotics.palmrgb

import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaletteOptions @Inject constructor() {
    val paletteList : MutableList<Palette> = mutableListOf()
}
