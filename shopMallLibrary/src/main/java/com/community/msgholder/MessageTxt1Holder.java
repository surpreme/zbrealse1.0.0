package com.community.msgholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

/**
 * Created by mayn on 2018/9/5.
 */

public class MessageTxt1Holder extends RecyclerView.ViewHolder{
    public TextView tv_txt;
    public MessageTxt1Holder(View itemView) {
        super(itemView);
        tv_txt= (TextView) itemView.findViewById(R.id.tv_txt);
    }
}
