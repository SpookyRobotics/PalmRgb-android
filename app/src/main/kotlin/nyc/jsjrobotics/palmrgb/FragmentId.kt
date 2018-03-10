package nyc.jsjrobotics.palmrgb


import android.os.Bundle
import android.support.v4.app.Fragment

enum class FragmentId(
        val tag: String,
        val supplier: (fragmentArguments: Bundle?) -> Fragment
) {
    CREATE_FRAME_FRAGMENT(
            CreateFrameFragment.TAG,
            { addArgments(CreateFrameFragment(), it) }
    );

    companion object {
        init {
            if (BuildConfig.DEBUG) {
                val ids = FragmentId.values()
                val tagList = ids.map { it.tag }
                tagList.forEach { id ->
                    val fragmentsWithId = ids.filter { id.equals(it.tag) }
                    if (fragmentsWithId.size > 1) {
                        throw IllegalStateException("Each fragment id must have a unique tag. Duplicated: $id")
                    }
                }
            }
        }

        private fun addArgments(fragment: Fragment, bundle: Bundle?): Fragment {
            fragment.arguments = bundle
            return fragment
        }
    }
}
