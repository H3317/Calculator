package com.hl.calculator

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    var exitTime: Long = 0
    var context: Context? = null
    var mCustomKeyboard: CustomKeyboard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        context = this
        //btnsave.setOnClickListener{Save()}
        expr.setTextChangeListener{ClearResult()}
        val digits = "0123456789.+-*/()"

        //1 屏蔽掉系统默认输入法
        if (Build.VERSION.SDK_INT <= 10) {
            expr.setInputType(InputType.TYPE_NULL)
        }
        else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            try {
                val cls = EditText::class.java
                val setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
                setShowSoftInputOnFocus.isAccessible = true
                setShowSoftInputOnFocus.invoke(expr, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        //2 初试化键盘
        mCustomKeyboard = CustomKeyboard(this@MainActivity,customKeyboard,expr,result)
        mCustomKeyboard!!.showKeyboard()

        expr.setKeyListener(DigitsKeyListener.getInstance(digits))

        expr.setOnClickListener {mCustomKeyboard!!.showKeyboard()}
    }

    fun EditText.setTextChangeListener(body: (key: String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                body(s.toString())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_save ->
            {
                Save()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (mCustomKeyboard!!.isShowKeyboard()){
            mCustomKeyboard!!.hideKeyboard();
        }
        else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(context, R.string.pressagainexit, Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                super.onBackPressed()
            }
        }
    }

    fun Save(){

    }

    fun ClearResult(){
        result.setText("")
    }
}
