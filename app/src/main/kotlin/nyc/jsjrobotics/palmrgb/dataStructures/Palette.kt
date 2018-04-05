package nyc.jsjrobotics.palmrgb.dataStructures

class Palette (val name : String, val colors: List<Int>) {

    companion object {
        val UNNAMED = "UNAMED_PALETTE"
    }

    constructor(vararg colors : Int) : this(UNNAMED, colors.toMutableList())
}
