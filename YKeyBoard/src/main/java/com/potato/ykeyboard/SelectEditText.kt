package com.potato.ykeyboard

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

class SelectEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {
    private var listener : EditSelectionChange? = null

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        listener?.change(selStart, selEnd)
    }

    fun setEditSelectionChange(listener : EditSelectionChange){
        this.listener = listener
    }

    public interface EditSelectionChange {
        fun change(selStart: Int, selEnd: Int)
    }
}