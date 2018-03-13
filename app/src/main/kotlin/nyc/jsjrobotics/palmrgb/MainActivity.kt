package nyc.jsjrobotics.palmrgb

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import nyc.jsjrobotics.palmrgb.androidInterfaces.DefaultActivity
import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentId


class MainActivity : DefaultActivity(), BottomNavigationView.OnNavigationItemSelectedListener  {
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.bottomNavigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.tab_view_frames
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return selectBottonNavigationItemId(item.itemId)
    }
}