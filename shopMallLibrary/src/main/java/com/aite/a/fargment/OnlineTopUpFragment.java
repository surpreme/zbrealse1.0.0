package com.aite.a.fargment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import static com.aite.a.base.Mark.online_top_up_err;
import static com.aite.a.base.Mark.online_top_up_id;

/**
 * @author:TQX
 * @Date: 2019/10/25
 * @description:
 */
@SuppressLint("ValidFragment")
public class OnlineTopUpFragment extends Fragment {
    private View view;
    private NetRun netRun;
    private TextView tvTtile, tv1;
    private EditText etNum;
    private Button btCommit;
    /**
     * 当前页面位置
     */
    private int currentIndex = 0;
    private int type = 1;//1在线充值 2 充值卡充值

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() { // NO_UCD (use private)
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case online_top_up_id:

                    break;
                case online_top_up_err:

                    break;
            }
        }
    };


    public OnlineTopUpFragment(int type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_online_top_up, container, false);
        netRun = new NetRun(getActivity(), handler);
        findViewById();
        return view;
    }

    protected void findViewById() {
        tvTtile = view.findViewById(R.id.tv_title);
        tv1 = view.findViewById(R.id.tv_1);
        etNum = view.findViewById(R.id.et_money);
        btCommit = view.findViewById(R.id.bt_commit);
        if (type == 2) {
            tvTtile.setText("平台充值卡号");
            tv1.setVisibility(View.INVISIBLE);
            etNum.setHint("请输入充值卡号");
        }
        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                netRun.OnlineTopup();
            }
        });
    }


}
