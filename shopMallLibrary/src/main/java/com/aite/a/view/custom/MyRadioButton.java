package com.aite.a.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.aiteshangcheng.a.R;

/**
 * Created by Changer on 2019/3/12
 */

@SuppressLint("AppCompatCustomView")
public class MyRadioButton extends RadioButton {

    private Drawable drawable;

    public MyRadioButton(Context context) {
        super(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);//获取我们定义的属性
        int dimension = typedArray.getInteger(R.styleable.MyRadioButton_bounds, 0);
        drawable = typedArray.getDrawable(R.styleable.MyRadioButton_drawableTop);
        drawable.setBounds(0, 0, dimension, dimension);
        setCompoundDrawables(null, drawable, null, null);
        typedArray.recycle();
    }

}
