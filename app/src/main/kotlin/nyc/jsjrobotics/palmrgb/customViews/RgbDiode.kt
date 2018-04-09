package nyc.jsjrobotics.palmrgb.customViews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.Parcelable
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

    // Overridden setter to guarantee value stays between 0 and displayedPalette.size
    // Setting the current color index will trigger color changed
    private var currentColorIndex = 0 ; private set(value) {
        if (value < 0 || value >= colorStateList.size) {
            field = 0
        } else {
            field = value
        }
        val color = colorStateList[field]
        rgbPaint.color = color
    }

    private var midX: Float = 0f
    private var midH: Float = 0f
    private var radius: Float = 0f

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        blackOutlinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        blackOutlinePaint.color = Color.BLACK
        blackOutlinePaint.style = Paint.Style.FILL_AND_STROKE
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
        notifyColorChanged()
        invalidate()
    }

    private fun notifyColorChanged() {
        colorChanged.onNext(rgbPaint.color)
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
        if (colorStateList.isNotEmpty()) {
            currentColorIndex = bundle.getInt("colorIndex")
        }
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
        rectangle = Rect(0,
                0,
                width-1,
                height-1)

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearSubscriptions()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(midX, midH, radius, rgbPaint )

        // Left
        canvas.drawRect(0f, 0f, 3f, height.toFloat(), blackOutlinePaint)

        //Top
        canvas.drawRect(0f, 0f, width.toFloat(), 3f, blackOutlinePaint)

        //Right
        canvas.drawRect(width.toFloat() - 3f,0f, width.toFloat(), height.toFloat(), blackOutlinePaint)

        //Bottom
        canvas.drawRect(0f,height.toFloat() - 3f, width.toFloat(), height.toFloat(), blackOutlinePaint)
    }

    fun currentColor(): Int = rgbPaint.color


    fun setCurrentColor(nextColor: Int) {
        val requestedColorIndex = colorStateList.indexOf(nextColor)
        if (requestedColorIndex != -1) {
            currentColorIndex = requestedColorIndex
        } else {
            rgbPaint.color = nextColor
        }
        notifyColorChanged()
        invalidate()
    }

    fun subscribeOnColorChanged(callback: () -> Unit) {
        disposables.add(onColorChanged.subscribe { callback() })
    }

    fun clearSubscriptions() {
        disposables.clear()
    }

    fun setGreenComponent(nextGreen: Int) {
        val green = safeRgb(nextGreen)
        val currentColor = rgbPaint.color
        val alpha = Color.alpha(currentColor)
        val red = Color.red(currentColor)
        val blue = Color.blue(currentColor)
        rgbPaint.color = Color.argb(alpha, red, green, blue)
        notifyColorChanged()
        invalidate()
    }

    fun setRedComponent(nextRed: Int) {
        val red = safeRgb(nextRed)
        val currentColor = rgbPaint.color
        val alpha = Color.alpha(currentColor)
        val green = Color.green(currentColor)
        val blue = Color.blue(currentColor)
        rgbPaint.color = Color.argb(alpha, red, green, blue)
        notifyColorChanged()
        invalidate()
    }

    fun setBlueComponent(nextBlue: Int) {
        val blue = safeRgb(nextBlue)
        val currentColor = rgbPaint.color
        val alpha = Color.alpha(currentColor)
        val red = Color.red(currentColor)
        val green = Color.green(currentColor)
        rgbPaint.color = Color.argb(alpha, red, green, blue)
        notifyColorChanged()
        invalidate()
    }

    private fun safeRgb(value: Int): Int {
        if (value < 0) {
            return  0
        } else if (value > 255) {
            return 255
        }
        return value
    }

}