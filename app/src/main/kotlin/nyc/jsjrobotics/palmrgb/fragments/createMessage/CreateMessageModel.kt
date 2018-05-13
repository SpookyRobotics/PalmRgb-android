package nyc.jsjrobotics.palmrgb.fragments.createMessage

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.dataStructures.Message
import nyc.jsjrobotics.palmrgb.dataStructures.Palette
import nyc.jsjrobotics.palmrgb.firebase.SimpleAuth
import nyc.jsjrobotics.palmrgb.firebase.datamanagers.MessageDataManager
import nyc.jsjrobotics.palmrgb.globalState.DeviceConstants
import nyc.jsjrobotics.palmrgb.globalState.SavedPaletteModel
import nyc.jsjrobotics.palmrgb.service.PalmRgbBackground
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateMessageModel @Inject constructor(private val application: Application,
                                             private val savedPalettesModel: SavedPaletteModel,
                                             private val simpleAuth: SimpleAuth,
                                             private val messageDataManager: MessageDataManager) {
    var selectedPalette: Palette = savedPalettesModel.getStandardPalette()
    var displayedColors: MutableList<Int> = initialValues(); private set
    val displayChanged: PublishSubject<Boolean> = PublishSubject.create()
    val onDisplayChanged: Observable<Boolean> = displayChanged
    val messageSent: PublishSubject<Boolean> = PublishSubject.create()
    val onMessageSent: Observable<Boolean> = messageSent

    fun diodeRange(): IntRange = DeviceConstants.largeArrayRange

    fun uploadMessageToFirebase(messageTitle: String) {
        val message = Message(messageTitle, simpleAuth.currentUserId)
        messageDataManager.uploadMessage(message.mutable())
        messageSent.onNext(true)
    }

    fun writeMessageToDatabase(messageTitle: String) {
        val data = ArrayList<Int>()
        data.addAll(displayedColors)
        val intent = PalmRgbBackground.saveMessage(messageTitle, data)
        application.startService(intent)
    }

    private fun initialValues(): MutableList<Int> = DeviceConstants.largeArrayRange.map { selectedPalette.colors.first() }.toMutableList()

    fun setDisplayingColors(displayingColors: List<Int>) {
        displayedColors.forEachIndexed { index, _ ->
            val nextColor: Int
            if (index < displayingColors.size) {
                nextColor = displayingColors[index]
            } else {
                nextColor = selectedPalette.colors[0]
            }
            displayedColors[index] = nextColor
        }
        displayChanged.onNext(true)
    }

    fun displayText(text: String) {
        val shownText = if (text.length <= 32) {
            text.substring(0, text.length)
        } else {
            text.substring(text.lastIndex - 32, text.length)
        }
        setDisplayingColors(generateColors(shownText))
    }

    private fun generateColors(shownText: String): List<Int> {
        return shownText.map { it.toInt() }
                .map { selectedPalette.colors[it % selectedPalette.colors.size] }
    }
}
