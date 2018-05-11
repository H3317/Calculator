package com.hl.calculator

import android.content.Context
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import net.sourceforge.jeval.EvaluationException
import net.sourceforge.jeval.Evaluator


/**
 * Created by yi17.zhang on 2017/5/19.
 */
class CustomKeyboard(context: Context,keyboardView:MyKeyboardView,private val mEdittext: EditText,private val rEdittext: TextView) {
    private val mKeyboard: Keyboard
    private val mKeyboardView: MyKeyboardView
    var context:Context

    private val actionListener = object : KeyboardView.OnKeyboardActionListener {
        override fun onPress(primaryCode: Int) {}

        override fun onRelease(primaryCode: Int) {

        }

        override fun onKey(primaryCode: Int, keyCodes: IntArray) {
            val editable = mEdittext.text
            val index = mEdittext.selectionStart//光标位置
            when (primaryCode) {
                Keyboard.KEYCODE_DELETE//回退
                ->
                    if (editable != null && editable.length > 0) {
                        if (index > 0) {
                            editable.delete(index - 1, index)
                        }
                    }
                61->
                    Calc()
                else ->
                    editable!!.insert(index, Character.toString(primaryCode.toChar()))
            }
        }

        override fun onText(text: CharSequence) {

        }

        override fun swipeLeft() {

        }

        override fun swipeRight() {

        }

        override fun swipeDown() {

        }

        override fun swipeUp() {

        }
    }

    fun Calc(){
        val evaluator = Evaluator()//创建一个对象
        try {
            //val X = 1.235f//变量X为浮点数1.235
            //evaluator.putVariable("X", X.toString())//设置"X"为表达式字符串中的变量，并赋值，注意赋值量是字符串，需要转换
            rEdittext.setText(evaluator.evaluate(mEdittext.getText().toString()))//在结果文本框中显示，蓝色部分即表达式字符串
        }catch (ee: EvaluationException) {
            rEdittext.setText("")
            Toast.makeText(context, context.getString(R.string.errorexpression), Toast.LENGTH_SHORT).show()
        }
    }

    init {
        this.context = context
        mKeyboard = Keyboard(this.context, R.xml.keyboard)//从xml中加载自定义的键盘
        mKeyboardView = keyboardView
        //mKeyboardView.setContext(context)
        mKeyboardView.keyboard = mKeyboard
        mKeyboardView.setEditText(mEdittext)
        mKeyboardView.isPreviewEnabled = false
        mKeyboardView.setOnKeyboardActionListener(actionListener)

    }

    fun showKeyboard() {
        if (mKeyboardView.visibility != View.VISIBLE) {
            mKeyboardView.visibility = View.VISIBLE
        }
    }

    fun hideKeyboard() {
        if (mKeyboardView.visibility == View.VISIBLE) {
            mKeyboardView.visibility = View.GONE
        }
    }

    fun isShowKeyboard(): Boolean {
        return mKeyboardView.visibility == View.VISIBLE
    }

}
