package nyc.jsjrobotics.palmrgb.fragments.createMessage

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.text.Editable
import android.text.TextWatcher
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.DEBUG
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultPresenter
import javax.inject.Inject

class CreateMessagePresenter @Inject constructor(val model: CreateMessageModel) : DefaultPresenter() {

    lateinit var view: CreateMessageView
    private val textWatcher: TextWatcher = buildTextWatcher()
    private val disposables: CompositeDisposable = CompositeDisposable()

    fun init(view: CreateMessageView) {
        this.view = view
        view.setInputListener(textWatcher)
        subscribeSendClicked()
    }

    private fun buildTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // ignored
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // ignored
            }

            override fun afterTextChanged(s: Editable) {
                val text = s.toString()
                model.displayText(text)

                if (text.isEmpty()) {
                    view.disableSendButton()
                } else{
                    view.enableSendButton()
                }

            }
        }
    }

    private fun subscribeSendClicked() {
        DEBUG("Send button clicked...")
        val sendClickedDisposable = view.onSendClicked.subscribe { messageTitle ->
            model.uploadMessageToFirebase(messageTitle)
        }
        disposables.add(sendClickedDisposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe() {
        disposables.clear()
    }
}
