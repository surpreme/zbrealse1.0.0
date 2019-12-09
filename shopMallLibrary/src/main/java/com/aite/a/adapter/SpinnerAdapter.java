package com.aite.a.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayn on 2018/8/10.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> items = new ArrayList<String>();

    public SpinnerAdapter(final Context context,
                          final int textViewResourceId, final List<String> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    android.R.layout.simple_spinner_item, parent, false);
        }

        TextView tv = (TextView) convertView
                .findViewById(android.R.id.text1);
        tv.setText(items.get(position));
        tv.setGravity(Gravity.CENTER);

//         tv.setTextColor(Color.BLUE);
        // tv.setTextSize(12, TypedValue.COMPLEX_UNIT_SP);
        tv.setTextSize(12);
        int px2dip = dp2px(context, 10);
        tv.setPadding(0, px2dip, 0, px2dip);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    android.R.layout.simple_spinner_item, parent, false);
        }
        TextView tv = (TextView) convertView
                .findViewById(android.R.id.text1);
        tv.setText(items.get(position)+"\t");
        tv.setGravity(Gravity.CENTER);
//        tv.setTextColor(Color.WHITE);
        // tv.setTextColor(Color.BLUE);
        tv.setTextSize(12);
        // int px2dip = dip2px(context, 10);
        // tv.setPadding(0, px2dip, 0, px2dip);
        return convertView;
    }


    /**
     * convert dp to its equivalent px
     *
     * 将dp转换为与之相等的px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
