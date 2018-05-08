package nyc.jsjrobotics.palmrgb.fragments.createMessage

import android.text.Editable
import android.text.TextWatcher
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import javax.inject.Inject

class CreateMessagePresenter @Inject constructor(val model : CreateMessageModel): DefaultPresenter(){
    private val textWatcher : TextWatcher = buildTextWatcher()

    fun init(view: CreateMessageView) {
        view.setInputListener(textWatcher)
    }

    private fun buildTextWatcher(): TextWatcher {
        return object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // ignored
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // ignored
            }

            override fun afterTextChanged(s: Editable) {
                model.displayText(s.toString())
            }
        }
    }
}
