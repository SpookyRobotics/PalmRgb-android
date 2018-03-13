package nyc.jsjrobotics.palmrgb.androidInterfaces


interface HasPresenter {
    fun setPresenter(presenter: DefaultPresenter)
    fun subscribeToPresenter(presenter: DefaultPresenter)
}