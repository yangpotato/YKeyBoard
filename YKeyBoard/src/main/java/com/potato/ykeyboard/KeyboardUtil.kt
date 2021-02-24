package com.potato.ykeyboard

import android.annotation.SuppressLint
import android.content.Context
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


class KeyboardUtil(
    context: Context?,
    keyboardView: KeyboardView?,
    editText: EditText?
) {

    private var mContext: Context? = null
    private var mKeyboardView: KeyboardView? = null
    private var mEditText: EditText? = null


    init {
        mContext = context
        mKeyboardView = keyboardView
        mEditText = editText
        initKeyboard()
        mEditText!!.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                hideSystemKeyboard(v as EditText)
                mKeyboardView!!.visibility = View.VISIBLE
            }
            false
        }
        mEditText!!.setOnFocusChangeListener { v, hasFocus ->
            if (v is EditText) {
                if (!hasFocus) {
                    mKeyboardView!!.visibility = View.GONE
                } else {
                    hideSystemKeyboard(v as EditText)
                    mKeyboardView!!.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun hideSystemKeyboard(v: EditText) {
        mEditText = v
        val imm: InputMethodManager =
            mContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                ?: return
        val isOpen: Boolean = imm.isActive()
        if (isOpen) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            v.showSoftInputOnFocus = false
        } else {
            v.inputType = 0
        }
    }

    private fun initKeyboard() {
        val keyboard = Keyboard(mContext, R.xml.y_keyboard)
        mKeyboardView!!.keyboard = keyboard
        mKeyboardView!!.isEnabled = true
        mKeyboardView!!.setOnKeyboardActionListener(listener)
    }

    private val listener: OnKeyboardActionListener = object : OnKeyboardActionListener {
        override fun onPress(primaryCode: Int) {
            mKeyboardView!!.isPreviewEnabled = !(primaryCode == -4 || primaryCode == -5)
        }

        override fun onRelease(primaryCode: Int) {}
        override fun onKey(primaryCode: Int, keyCodes: IntArray) {
            val editable = mEditText!!.text
            val start = mEditText!!.selectionStart
            val end = mEditText!!.selectionEnd
            if (primaryCode == Keyboard.KEYCODE_DONE) {
                mKeyboardView!!.visibility = View.GONE
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
                if (editable != null && editable.length > 0) {
                    if (start == end) {
                        editable.delete(start - 1, start)
                    } else {
                        editable.delete(start, end)
                    }
                }
            } else {
                editable!!.replace(
                    start,
                    end,
                    Character.toString(primaryCode.toChar())
                )
            }
        }

        override fun onText(text: CharSequence) {}
        override fun swipeLeft() {}
        override fun swipeRight() {}
        override fun swipeDown() {}
        override fun swipeUp() {}
    }
}