package nyc.jsjrobotics.palmrgb.injection

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.samples.SvgDecoder
import com.bumptech.glide.samples.SvgDrawableTranscoder
import com.caverock.androidsvg.SVG
import java.io.InputStream

@GlideModule
/**
 * Includes SvgModule code
 */
class BrokenHungryGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide,
                                    registry: Registry) {
        registry.register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
                .append(InputStream::class.java, SVG::class.java, SvgDecoder())
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}