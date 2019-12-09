package com.aite.a.view;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
/**
 * Created by Administrator on 2017/9/12.
 */
public class PullableRecyclerview extends RecyclerView implements Pullable{
    public PullableRecyclerview(Context context) {
        super(context);
    }

    public PullableRecyclerview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableRecyclerview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (getChildCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (getChildAt(0).getTop() >= 0) {
            // 滑到顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getChildCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getChildAt((getChildCount()-1)).getBottom() <= getMeasuredHeight()) {
            return true;
        }
        return false;
    }

}
