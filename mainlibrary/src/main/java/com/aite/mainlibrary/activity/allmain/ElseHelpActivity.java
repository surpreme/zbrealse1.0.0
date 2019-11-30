package com.aite.mainlibrary.activity.allmain;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Constant.MainUIConstant;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allstep.StepActivity;
import com.aite.mainlibrary.adapter.ElseServiceUIAdapter;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;

public class ElseHelpActivity extends BaseActivity {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    private ElseServiceUIAdapter elseServiceUIAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.else_help_layout;
    }

    @Override
    protected void initView() {
        initToolbar("其他服务");
        GridLayoutManager layoutManage = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManage);
        elseServiceUIAdapter = new ElseServiceUIAdapter(context, MainUIConstant.ElseHelpConstant.settingImg, MainUIConstant.ElseHelpConstant.settingTv, MainUIConstant.ElseHelpConstant.backgrondImg);
        recyclerView.setAdapter(elseServiceUIAdapter);
        elseServiceUIAdapter.setOnClickLstenerInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                if (postion == 5)
                    startActivity(StepActivity.class);
            }
        });


    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

}
