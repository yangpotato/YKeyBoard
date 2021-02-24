package com.potato.ykeyboarddemo

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.potato.ykeyboard.KeyboardUtil
import com.potato.ykeyboard.YKeyBoardView
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), KeyboardView.OnKeyboardActionListener  {
    private lateinit var mKeyBoardView : YKeyBoardView
    private lateinit var mEdt : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mKeyBoardView = findViewById(R.id.key_board)
        mEdt = findViewById(R.id.edt)
//        val mKeyboder = Keyboard(this, R.xml.y_keyboard)
//
//        val keys = mKeyboder.keys
//        val newkeyList: MutableList<Keyboard.Key> = ArrayList()
//        for (key in keys) {
//            if (key.label != null ) {
//                newkeyList.add(key)
//            }
//        }
//        Log.e("text", mKeyboder.toString())
//        mKeyBoardView.keyboard = mKeyboder;
//        mKeyBoardView.isEnabled = true;
//        mKeyBoardView.isPreviewEnabled = false;
//        mKeyBoardView.visibility = View.VISIBLE;
//        mKeyBoardView.setOnKeyboardActionListener(this);
        KeyboardUtil(this, mKeyBoardView, mEdt)
    }

    override fun swipeRight() {
        TODO("Not yet implemented")
    }

    override fun onPress(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onRelease(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun swipeLeft() {
        TODO("Not yet implemented")
    }

    override fun swipeUp() {
        TODO("Not yet implemented")
    }

    override fun swipeDown() {
        TODO("Not yet implemented")
    }

    override fun onKey(p0: Int, p1: IntArray?) {
        TODO("Not yet implemented")
    }

    override fun onText(p0: CharSequence?) {
        TODO("Not yet implemented")
    }
}