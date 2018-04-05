package nyc.jsjrobotics.palmrgb.dataStructures

class MutablePalette (var name : String, var colors: MutableList<Int>) {

    companion object {
        val UNNAMED = "UNAMED__MUTABLE_PALETTE"
    }

    constructor(vararg colors : Int) : this(UNNAMED, colors.toMutableList())
}
