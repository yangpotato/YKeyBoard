package com.potato.ykeyboard

import android.content.Context
import android.graphics.*
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.Editable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import java.util.regex.Matcher
import java.util.regex.Pattern


class YKeyBoardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : KeyboardView(context, attrs, defStyleAttr), KeyboardView.OnKeyboardActionListener {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBounds = Rect()
    private var mLabelTextSize = 0
    private lateinit var mEditText : EditText
    private var mPopup: YKeyBoardPopup? = null
    private var formula: String = ""
    private var mEditable : Editable? = null
    init {
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.color = Color.BLACK

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.YKeyboardView)
        val count = attributes.indexCount
        for(i in 0 until count){
            when(val attr = attributes.getIndex(i)){
                R.styleable.YKeyboardView_text_size -> {
                    mLabelTextSize = attributes.getDimensionPixelSize(attr, 14)
                }
            }
        }
        attributes.recycle()
        mPopup = YKeyBoardPopup(context)
        mEditable = mPopup?.mEdtFormula?.text
        initKeyBoard()
    }

    private fun initKeyBoard() {
        keyboard = Keyboard(context, R.xml.y_keyboard)
        isEnabled = true
        onKeyboardActionListener = this
    }

    public fun putEditText(editText: EditText){
        mEditText = editText
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
        if(mLabelTextSize == 0) {
            val field = KeyboardView::class.java.getDeclaredField("mLabelTextSize")
            field.isAccessible = true
            mLabelTextSize = field.get(this) as Int
        }
        mPaint.textSize = mLabelTextSize.toFloat()
        mPaint.typeface = Typeface.DEFAULT
        mPaint.getTextBounds(label, 0, label.length, mBounds)
        canvas?.drawText(label, (key.x + key.width / 2).toFloat(),
            (key.y + key.height / 2 + mBounds.height() / 2).toFloat(), mPaint)
    }

    override fun onPress(primaryCode: Int) {

    }

    override fun onRelease(primaryCode: Int) {
        Log.e("1", "1")
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        Log.e("YPOTATO", "primaryCode: $primaryCode, keyCodes: $keyCodes")
        if(!::mEditText.isInitialized)
            return
        val editable = mEditText.text
        val start = mEditText.selectionStart
        val end = mEditText.selectionEnd
        when(primaryCode){
            Keyboard.KEYCODE_DELETE -> {
                //删除键
                if (editable != null && editable.isNotEmpty()) {
                    if (start == end) {
                        editable.delete(start - 1, start)
                    } else {
                        editable.delete(start, end)
                    }
                    formula = editable.toString()
                    mPopup?.setFormula(formula)
                }
            }
            43 -> {
                //加号
                addFloatView(editable, start, end, primaryCode.toChar().toString())
            }
            else -> {
                setEditable(editable, start, end, primaryCode)

                formula = editable.toString()
                mPopup?.setFormula(formula)

                var matcher  = Pattern.compile("^[0-9]*\$").matcher(formula).matches();
                Log.i("YPOTATO", "matcher: $matcher")

            }
        }
    }

    private fun setEditable(editable: Editable?, start: Int, end: Int, primaryCode: Int) {
        editable!!.replace(
            start,
            end,
            primaryCode.toChar().toString()
        )
    }

    private fun addFloatView(editable: Editable, start: Int, end: Int, text: CharSequence ) {
        val string = editable
        string.replace(
            start,
            end,
            text
        )

        mPopup?.setFormula(string.toString())
        mPopup?.show(this, measuredHeight)
    }

    override fun onText(text: CharSequence?) {
        Log.e("1", "1")
    }

    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}
}