package com.aite.a.activity.li.activity;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aite.a.activity.NearbyActivity;
import com.aite.a.activity.li.adapter.AroundCityAdapter;
import com.aite.a.activity.li.p.Model;
import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.R2;
import com.gjiazhe.wavesidebar.WaveSideBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AroundLocationChoiceActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView iv_back;
    @BindView(R2.id.loction_tv)
    TextView loction_tv;
    @BindView(R2.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R2.id.side_bar)
    WaveSideBar side_bar;
    @BindView(R2.id.lv_addresslist)
    ListView lv_addresslist;
    private NearbyActivity.SelectCityadapder selectcityadapder;


    @Override
    protected int getLayoutResId() {
        return R.layout.aroundlocationchoice_activity_layout;
    }

    @Override
    protected void initView() {
        String loction = getIntent().getStringExtra("location");
        loction_tv.setText("当前定位" + loction);
//        startActivity(WelcomeActivity.class);

    }

    @Override
    protected void initModel() {
        Model model = new Model(context);
        model.aAroundLocationChoiceActivity(new Model.ModelInteface.AmClassBrandFragmentInterface() {
            @Override
            public void amClassBrandFragmentInterfaceModelSuccess(Context context, List<?> datas) {
                recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recycler_view.setAdapter(new AroundCityAdapter(context, (List<String>) datas));

            }

            @Override
            public void amClassBrandFragmentInterfaceModelFail(String error) {

            }
        });
    }

    @OnClick(R2.id.iv_back)
    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_back:
//                onBackPressed();
//                break;
//            default:
//                break;
//        }
        if(v.getId()==R.id.iv_back){
            onBackPressed();
        }

    }
}
