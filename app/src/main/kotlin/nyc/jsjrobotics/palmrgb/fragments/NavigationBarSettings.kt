package nyc.jsjrobotics.palmrgb.fragments

import nyc.jsjrobotics.palmrgb.androidInterfaces.FragmentId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationBarSettings @Inject constructor(){
    val HIDE_NAVIGATION_BAR : List<FragmentId> = listOf(
            FragmentId.CREATE_PALETTE
    )
}
