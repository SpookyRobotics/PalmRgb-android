package nyc.jsjrobotics.palmrgb

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class RgbDiode(context: Context, attrs: AttributeSet, style: Int) : View(context, attrs, style) {
    private lateinit var rgbPaint: Paint
    private lateinit var rectangle: Rect
    var initialColor: Int = Color.TRANSPARENT; set(value) {
        field = value
        rgbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        rgbPaint.setColor(value)
        rgbPaint.setStyle(Paint.Style.FILL)
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RgbDiode)
        initialColor = typedArray.getColor(R.styleable.RgbDiode_prgb_initialColor, Color.TRANSPARENT)
        typedArray.recycle()
    }

    public override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        rectangle = Rect(0, 0, width, height)

    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRGB(Color.red(initialColor), Color.green(initialColor), Color.blue(initialColor))
    }
}