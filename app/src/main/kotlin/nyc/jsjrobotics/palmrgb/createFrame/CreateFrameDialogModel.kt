package nyc.jsjrobotics.palmrgb.createFrame

class CreateFrameDialogModel {
    companion object {
        private val instance: CreateFrameDialogModel = CreateFrameDialogModel()
        fun instance() : CreateFrameDialogModel {
            return instance
        }
    }
    var currentFrame : List<Int> = emptyList()
    fun saveCurrentFrame(title: String) {
        print(title)
    }
}