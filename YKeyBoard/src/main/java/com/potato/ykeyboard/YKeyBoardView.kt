package com.potato.ykeyboard

import android.content.Context
import android.graphics.*
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log

class YKeyBoardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : KeyboardView(context, attrs, defStyleAttr) {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBounds = Rect()
    private var mLabelTextSize = 0

    init {
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.color = Color.BLACK


        val attributes = context.obtainStyledAttributes(attrs, R.styleable.YKeyboardView)
        val count = attributes.indexCount
        for(i in 0 until count){
            when(val attr = attributes.getIndex(i)){
                R.styleable.YKeyboardView_textSize -> {
                    mLabelTextSize = attributes.getDimensionPixelSize(attr, 14)
                }
            }
        }
        attributes.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val keys = keyboard.keys
        for(key in keys){
            drawText(canvas, key)
        }
    }

    private fun drawText(canvas: Canvas?, key: Keyboard.Key?) {
        if(TextUtils.isEmpty(key?.label))
            return
        val label : String = key!!.label as String
        Log.e("YPOTATO", label)
        val field = KeyboardView::class.java.getDeclaredField("mLabelTextSize")
        field.isAccessible = true
        mLabelTextSize = field.get(this) as Int
        mPaint.textSize = mLabelTextSize.toFloat()
        mPaint.typeface = Typeface.DEFAULT
        mPaint.getTextBounds(label, 0, label.length, mBounds)
        canvas?.drawText(label, (key.x + key.width / 2).toFloat(),
            (key.y + key.height / 2 + mBounds.height() / 2).toFloat(), mPaint)
    }
}