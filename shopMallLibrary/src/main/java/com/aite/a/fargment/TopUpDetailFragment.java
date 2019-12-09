package com.aite.a.fargment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

/**
 * @author:TQX
 * @Date: 2019/10/25
 * @description:
 */
@SuppressLint("ValidFragment")
public class TopUpDetailFragment extends Fragment {
    private View view;
    private NetRun netRun;
    private TextView tvUseMoney, tvColdMoney;
    private ListView lvList;
    /**
     * 当前页面位置
     */
    private int currentIndex = 0;
    private int type = 1;//1.充值明细 2 充值卡余额

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    private Handler handler = new Handler() { // NO_UCD (use private)
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
            }
        }
    };


    public TopUpDetailFragment(int type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top_up_detail, container, false);
        netRun = new NetRun(getActivity(), handler);
        findViewById();
        return view;
    }

    protected void findViewById() {
        tvUseMoney = view.findViewById(R.id.tv_use_money);
        tvColdMoney = view.findViewById(R.id.tv_cold_money);
        lvList = view.findViewById(R.id.lv_list);
    }
}
