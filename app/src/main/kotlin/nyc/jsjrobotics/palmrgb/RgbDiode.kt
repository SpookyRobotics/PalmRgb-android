package nyc.jsjrobotics.palmrgb

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class RgbDiode(context: Context, attrs: AttributeSet, style: Int) : View(context, attrs, style) {
    private lateinit var rgbPaint: Paint
    private lateinit var blackOutlinePaint: Paint
    private lateinit var rectangle: Rect
    var colorStateList: MutableList<Int> = mutableListOf()
    private var currentColorIndex = 0;

    private var midX: Float = 0f
    private var midH: Float = 0f
    private var radius: Float = 0f


    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        blackOutlinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        blackOutlinePaint.color = Color.BLACK
        blackOutlinePaint.style = Paint.Style.STROKE
        blackOutlinePaint.strokeWidth = 2.0f

        rgbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        rgbPaint.setStyle(Paint.Style.FILL)
        rgbPaint.color = Color.BLACK

        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RgbDiode)
        val initialColor = getOptionalColor(typedArray, R.styleable.RgbDiode_prgb_initialColor)
        val secondColor = getOptionalColor(typedArray, R.styleable.RgbDiode_prgb_secondColor)
        val thirdColor = getOptionalColor(typedArray, R.styleable.RgbDiode_prgb_thirdColor)
        typedArray.recycle()

        colorStateList.addAll(listOfNotNull(initialColor, secondColor, thirdColor))

        colorStateList.firstOrNull()?.let {
            rgbPaint.color = it
            currentColorIndex += 1
        }

    }

    private fun getOptionalColor(typedArray: TypedArray, attributeId: Int): Int? {
        if (typedArray.hasValue(attributeId)) {
            return typedArray.getColor(attributeId, Color.TRANSPARENT)
        }
        return null
    }

    init {
        setOnClickListener { displayNextColor() }
    }

    fun displayNextColor() {
        colorStateList.filterIndexed { index, color -> index == currentColorIndex }
                .firstOrNull()
                ?.let { rgbPaint.color = it }
        currentColorIndex += 1
        if (colorStateList.size <= currentColorIndex) {
            currentColorIndex = 0
        }
        invalidate()
    }

    public override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        midX = (width/2).toFloat()
        midH = (height/2).toFloat()
        radius = if (width < height) (width/3).toFloat() else (height/3).toFloat()
        var borderPadding = (radius * 0.1).toInt()

        while (midX - radius - borderPadding < 0) {
            radius = (radius * 0.9).toFloat()
            borderPadding = (radius * 0.1).toInt()
        }

        val rectLeft : Int = (midX - radius - borderPadding).toInt()
        val rectTop : Int = (midH - radius - borderPadding).toInt()
        val rectRight : Int = (midX + radius + borderPadding).toInt()
        val rectBottom : Int = (midH + radius + borderPadding).toInt()
        rectangle = Rect(rectLeft,
                rectTop,
                rectRight,
                rectBottom)

    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(midX, midH, radius, rgbPaint )
        canvas.drawCircle(midX, midH, radius, blackOutlinePaint )
        canvas.drawRect(rectangle, blackOutlinePaint)
    }
}