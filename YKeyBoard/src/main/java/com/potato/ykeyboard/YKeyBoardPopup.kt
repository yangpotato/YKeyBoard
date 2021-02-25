package com.potato.ykeyboard

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView

class YKeyBoardPopup(private var context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    PopupWindow(context, attrs, defStyleAttr) {
    public var mEdtFormula : EditText

    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isOutsideTouchable = false
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_formula, null, false)
        mEdtFormula = contentView.findViewById(R.id.tv_formula)
    }

    fun setFormula(formula: String){
        mEdtFormula.setText(formula)
    }

    fun show(parent: View, height: Int){
        showAsDropDown(parent, 0, - height - dip2px(40f))
        Log.i("YPOTATO", "contentView.measuredHeight: ${contentView.measuredHeight}")
    }

    fun dip2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}