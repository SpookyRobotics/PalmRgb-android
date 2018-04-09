package nyc.jsjrobotics.palmrgb.customViews

import android.app.Activity
import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import nyc.jsjrobotics.palmrgb.R


class SubActivityToolbar(context : Context, attrs: AttributeSet?, defStyleAttr: Int) : Toolbar(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, R.attr.toolbarStyle)
    init {
        setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        setTitleTextColor(resources.getColor(android.R.color.white))
    }

    fun setNavigateUpActivity(activity: Activity) {
        setNavigationOnClickListener { activity.onBackPressed() }
    }
}