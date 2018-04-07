package nyc.jsjrobotics.palmrgb.customViews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.IntRange
import android.util.AttributeSet
import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import nyc.jsjrobotics.palmrgb.R

/**
 * TODO: state should be restored in writeStateToModel and onRestoreInstanceState
 * Currently state is restored by the parent view calling setCurrentColorIndex
 */
class RgbDiode(context: Context, attrs: AttributeSet?, style: Int) : View(context, attrs, style) {
    private lateinit var rgbPaint: Paint
    private lateinit var blackOutlinePaint: Paint
    private lateinit var rectangle: Rect
    private val colorChanged: PublishSubject<Int> = PublishSubject.create()
    val onColorChanged: Observable<Int> = colorChanged
    private val disposables : CompositeDisposable = CompositeDisposable()

    var indexInMatrix: Int = -1
    var colorStateList: MutableList<Int> = mutableListOf()

    // Overridd en setter to guarantee value stays between 0 and displayedPalette.size
    var currentColorIndex = 0 ; private set(value) {
        if (value >= colorStateList.size) {
            field = 0
        } else {
            field = value
        }
        val color = currentColor()
        rgbPaint.color = color
        colorChanged.onNext(color)
    }

    private var midX: Float = 0f
    private var midH: Float = 0f
    private var radius: Float = 0f

    constructor(context: Context) : this(context, null, 0)

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

        if (colorStateList.isNotEmpty()) {
            rgbPaint.color = colorStateList[0]
            currentColorIndex = 0
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
        if (colorStateList.isEmpty()) {
            return
        }
        currentColorIndex += 1
        colorStateList.filterIndexed { index, color -> index == currentColorIndex }
                .firstOrNull()
                ?.let { rgbPaint.color = it }
        invalidate()
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("parent", super.onSaveInstanceState())
        bundle.putInt("colorIndex", currentColorIndex)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as Bundle
        super.onRestoreInstanceState(bundle.getParcelable("parent"))
        currentColorIndex = bundle.getInt("colorIndex")
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearSubscriptions()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(midX, midH, radius, rgbPaint )
        canvas.drawCircle(midX, midH, radius, blackOutlinePaint )
        canvas.drawRect(rectangle, blackOutlinePaint)
    }

    fun currentColor(): Int {
        return colorStateList[currentColorIndex]
    }

    fun setCurrentColor(nextColor: Int) {
        val colorIndex = colorStateList.indexOf(nextColor)
        if (colorIndex != -1) {
            currentColorIndex = colorIndex
        } else {
            // Bypass saving of color state and just display the color
            rgbPaint.color = nextColor
        }


    }

    fun subscribeOnColorChanged(callback: () -> Unit) {
        disposables.add(onColorChanged.subscribe { callback() })
    }

    fun clearSubscriptions() {
        disposables.clear()
    }

    fun setGreenComponent(@IntRange(from = 0, to = 255) green: Int) {
        val currentColor = rgbPaint.color
        val alpha = Color.alpha(currentColor)
        val red = Color.red(currentColor)
        val blue = Color.blue(currentColor)
        rgbPaint.color = Color.argb(alpha, red, green, blue)
        invalidate()
    }

    fun setRedComponent(@IntRange(from = 0, to = 255) red: Int) {
        val currentColor = rgbPaint.color
        val alpha = Color.alpha(currentColor)
        val green = Color.green(currentColor)
        val blue = Color.blue(currentColor)
        rgbPaint.color = Color.argb(alpha, red, green, blue)
        invalidate()
    }

    fun setBlueComponent(@IntRange(from = 0, to = 255) blue: Int) {
        val currentColor = rgbPaint.color
        val alpha = Color.alpha(currentColor)
        val red = Color.red(currentColor)
        val green = Color.green(currentColor)
        rgbPaint.color = Color.argb(alpha, red, green, blue)
        invalidate()
    }

}