package nyc.jsjrobotics.palmrgb.androidInterfaces

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.R
import nyc.jsjrobotics.palmrgb.customViews.SubActivityToolbar
import nyc.jsjrobotics.palmrgb.fragments.NavigationBarSettings
import javax.inject.Inject

/**
 * Default Activity subscribes to abtest changes for recreate and
 * manages connection state for BrokenHungryBackground
 */
abstract class DefaultActivity : FragmentActivity() , IDefaultActivity {

    private val disposables: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var navigationBarSettings : NavigationBarSettings

    override fun getActivity(): FragmentActivity = this

    override fun applicationContext(): Context = applicationContext

    override fun onCreate(savedInstanceState: Bundle?) {
        Application.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()

    }

    override fun showFragment(fragmentToShow: FragmentId, fragmentArguments : Bundle?, addToBackStack: String?) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var fragmentDisplayed: Fragment? = supportFragmentManager.findFragmentByTag(fragmentToShow.tag)
        if (fragmentDisplayed == null) {
            fragmentDisplayed = fragmentToShow.supplier(fragmentArguments)
            transaction.add(R.id.fragment_container, fragmentDisplayed, fragmentToShow.tag)
        } else {
            transaction.show(fragmentDisplayed)
        }
        FragmentId.values().filter { it != fragmentToShow }
                .mapNotNull { supportFragmentManager.findFragmentByTag(it.tag) }
                .filter { it.isVisible }
                .forEach { transaction.hide(it) }

        if (addToBackStack != null) {
            transaction.addToBackStack(addToBackStack)
        }
        transaction.commit()

        val hideNavigationBar = navigationBarSettings.HIDE_NAVIGATION_BAR
                .contains(fragmentToShow)
        showNavigationBar(!hideNavigationBar)
    }

    override fun showNavigationBar(show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.GONE
        findViewById<View>(R.id.bottomNavigationView)?.visibility = visibility
    }

    protected fun selectBottonNavigationItemId(itemId: Int): Boolean {
        val navigationOptions = listOf(
                Pair(R.id.tab_create_frame, Runnable { showFragment(FragmentId.CREATE_FRAME_FRAGMENT) }),
                Pair(R.id.tab_connection_status, Runnable { showFragment(FragmentId.CONNECTION_STATUS) }),
                Pair(R.id.tab_view_frames, Runnable { showFragment(FragmentId.VIEW_FRAMES_FRAGMENT) }),
                Pair(R.id.tab_create_color, Runnable { showFragment(FragmentId.CREATE_COLOR) }),
                Pair(R.id.tab_create_palette, Runnable { showFragment(FragmentId.WHEELED_PLATFORM) })
                //Pair(R.id.tab_wheeled_platform, Runnable { showFragment(FragmentId.WHEELED_PLATFORM) })

        )

        val runnable = navigationOptions.filter { it.first == itemId }
                .map { it.second }
                .firstOrNull()

        runnable?.let {
            it.run()
            return true
        }
        return false
    }

    open fun removeFragment(fragmentToShow: FragmentId) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentDisplayed: Fragment? = supportFragmentManager.findFragmentByTag(fragmentToShow.tag)
        if (fragmentDisplayed != null) {
            transaction.remove(fragmentDisplayed)
            transaction.commit()
        }
    }

    fun getFragment(fragmentId : FragmentId): Fragment? {
        return supportFragmentManager.findFragmentByTag(fragmentId.tag)
    }

    protected fun setupSubActivityToolbar(@StringRes title: Int) {
        val toolbar : SubActivityToolbar = findViewById(R.id.toolbar)
        toolbar.title = resources.getString(title)
        toolbar.setNavigateUpActivity(this)
    }
}