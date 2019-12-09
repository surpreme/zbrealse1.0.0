package com.aite.a.view;

import android.graphics.Rect;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/9/11.
 */

public abstract class RvItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetectorCompat;
    private Rect frameRect = new Rect();
    private RecyclerView.ViewHolder viewHolder;
    private View itemView;
    private int itemPosition;

    public RvItemClickListener(final RecyclerView recyclerView) {

        mGestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                getItemClick(e, recyclerView);
                itemSingleClick(viewHolder, itemView,itemPosition );
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                getItemClick(e,recyclerView);
                itemLongPress(viewHolder, itemView,itemPosition);
            }
        });

    }

    private void getItemClick(MotionEvent e, RecyclerView recyclerView) {

//            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());

            if (itemView != null){
                viewHolder = recyclerView.getChildViewHolder(itemView);

                itemPosition = recyclerView.getChildLayoutPosition(itemView)  ;
                Log.i("---itemClick---", "AdapterPosition " + recyclerView.getChildAdapterPosition(itemView) );
                Log.i("---itemclcik---", "childLayoutPosition: " + itemPosition );
            }

            /*int childCount = recyclerView.getChildCount();
            for (int i = 0 ; i < childCount; i++) {
                View childViewVisible = recyclerView.getChildAt(i);
                if (childViewVisible.getVisibility() == View.VISIBLE) {
                    childViewVisible.getHitRect(frameRect);
                    if (frameRect.contains((int) e.getX(),(int) e.getY())) {
                        itemPosition = firstVisibleItemPosition + i;
                    }
                }
            }*/
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void itemSingleClick (RecyclerView.ViewHolder viewHolder , View itemView , int itemPosition );
    public abstract void itemLongPress (RecyclerView.ViewHolder childViewHolder , View childView , int itemPosition);
}
