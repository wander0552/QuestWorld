package com.wander.MyView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wander on 2015/5/20.
 * Email 18955260352@163.com
 */
public class WaterView extends ImageView {
    public WaterView(Context context) {
        super(context);
    }

    public WaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int size = MeasureSpec.getSize(widthMeasureSpec);//获取可用的宽度

            int hSize = size * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();//得到固有的高度和宽度
            setMeasuredDimension(size, hSize);//只能在onMeasure总调用此方法，设置测量好的宽高
        }

    }
}
