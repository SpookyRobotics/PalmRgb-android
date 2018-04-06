package nyc.jsjrobotics.palmrgb.fragments

import android.app.Activity
import android.content.Context
import android.support.v4.app.NavUtils
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import nyc.jsjrobotics.palmrgb.R

class UpNavigationToolbar(context : Context, attrs: AttributeSet?, defStyleAttr: Int) : Toolbar(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, R.attr.toolbarStyle)
    init {
        setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        setTitleTextColor(resources.getColor(R.color.white))
    }

    fun setNavigateUpActivity(activity: Activity) {
        setNavigationOnClickListener { NavUtils.navigateUpFromSameTask(activity) }
    }
}