package com.hl.calculator

import android.content.Context
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet
import android.widget.EditText


/**
 * Created by yi17.zhang on 2017/5/19. 用于以后扩展功能
 */
class MyKeyboardView(context: Context, attrs: AttributeSet) : KeyboardView(context, attrs) {
    private lateinit var edittext: EditText

    fun setEditText(edittext: EditText){
        this.edittext = edittext
    }

//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        /* List<Keyboard.Key> keys = getKeyboard().getKeys();
//        for(Keyboard.Key key: keys) {
//            if(key.label.equals("delete"))
//                resetOKBtn(key, canvas);
//        }*/
//    }

    override fun onLongPress(popupKey: Keyboard.Key): Boolean {
        // TODO Auto-generated method stub

        if (popupKey.codes[0] == Keyboard.KEYCODE_DELETE) {
            clearEditTextContent()
            //可使用OnKeyboardActionListener中的各种方法实现该功能
            //			getOnKeyboardActionListener().onKey(Keyboard.KEYCODE_DELETE, null);

        }

        return super.onLongPress(popupKey)
    }


    fun clearEditTextContent(){
        edittext.text.clear()
    }

//    /**
//     * 绘制OK键的点9图
//     * @author Song
//     * @param key
//     * @param canvas
//     */
//    private fun resetOKBtn(key: Keyboard.Key, canvas: Canvas) {
//        //将OK键重新绘制
//        /* Drawable npd = (Drawable) context.getResources().getDrawable(R.mipmap.icon_number_del);
//        npd.setBounds(key.x, key.y + 1, key.x + key.width, key.y + key.height + 1);
//        npd.draw(canvas);*/
//    }
}