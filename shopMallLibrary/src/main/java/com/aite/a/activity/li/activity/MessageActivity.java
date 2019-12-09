package com.aite.a.activity.li.activity;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.aite.a.activity.StationLetterListActivity;
import com.aite.a.activity.li.adapter.MsgListAdapter;
import com.aite.a.activity.li.bean.IconBean;
import com.aite.a.activity.li.bean.MsgBean;
import com.aite.a.activity.li.p.Model;
import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.R2;

import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity {
    @BindView(R2.id.app_msg)
    LinearLayout app_msg;
    @BindView(R2.id.base_msg)
    LinearLayout base_msg;
    @BindView(R2.id.setting_msg)
    LinearLayout setting_msg;
    @BindView(R2.id.msgList)
    RecyclerView msgrecy;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_message2;
    }

    @Override
    protected void initView() {
        app_msg.setOnClickListener(this);
        base_msg.setOnClickListener(this);
        setting_msg.setOnClickListener(this);
        msgrecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initModel() {
        Model model = new Model(context);
        model.messageModel(new Model.ModelInteface() {
            @Override
            public void messageActivityModelSuccess(Context context, List<MsgBean.DatasBean.MessageArrayBean> datas, List<IconBean.DatasBean> icbean) {
                msgrecy.setAdapter(new MsgListAdapter(context, datas, icbean));
            }

            @Override
            public void messageActivityModelFail(String error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.app_msg:
//                startActivity(StationLetterListActivity.class,"msgType", "app_mag");
//                break;
//            case R.id.setting_msg:
//                startActivity(StationLetterListActivity.class,"msgType", "setting_msg");
//                break;
//            case R.id.base_msg:
//                startActivity(StationLetterListActivity.class,"msgType", "base_msg");
//                break;
//        }

        if(R.id.app_msg==v.getId()){
            startActivity(StationLetterListActivity.class,"msgType", "app_mag");
        }else if(R.id.setting_msg==v.getId()){
            startActivity(StationLetterListActivity.class,"msgType", "setting_msg");
        }else if(R.id.base_msg==v.getId()){
            startActivity(StationLetterListActivity.class,"msgType", "base_msg");
        }

    }
}
