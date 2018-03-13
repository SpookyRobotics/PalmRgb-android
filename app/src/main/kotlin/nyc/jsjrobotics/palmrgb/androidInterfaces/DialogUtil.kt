package nyc.jsjrobotics.palmrgb.androidInterfaces

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import nyc.jsjrobotics.palmrgb.R

object DialogUtil {

    fun showDialog(dialog: Fragment, activity: FragmentActivity, tag: String) {
        val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
        val fragmentDisplayed: Fragment? = activity.supportFragmentManager.findFragmentByTag(tag)
        if (fragmentDisplayed != null) {
            transaction.remove(fragmentDisplayed)
        }
        transaction.add(R.id.fragment_container, dialog, tag).commit()

    }

    fun dismissDialog(activity: FragmentActivity, tag: String) {
        val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
        val fragmentDisplayed: Fragment? = activity.supportFragmentManager.findFragmentByTag(tag)
        if (fragmentDisplayed != null) {
            transaction.remove(fragmentDisplayed).commit()
        }

    }

}