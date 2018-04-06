package nyc.jsjrobotics.palmrgb.androidInterfaces

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationBarSettings @Inject constructor(){
    val HIDE_NAVIGATION_BAR : List<FragmentId> = listOf(
            FragmentId.CREATE_COLOR
    )
}
