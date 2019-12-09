package courier.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.OnlineTopUpActivity;
import com.aite.a.fargment.BaseFragment;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import courier.AllIncomeActivity;
import courier.CollectionWayActivity;
import courier.EditCostActivity;
import courier.IntegralMallActivity;
import courier.SiteActivity;
import courier.ConsumeLogActivity;
import courier.TopUpLogActivity;
import courier.model.CourierMeInfo;

/**
 * 我的
 * Created by Administrator on 2018/1/8.
 */
public class CourierMeFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView iv_icon;
    private TextView tv_phone, tv_name, tv_integral, tv_balance, tv_deduct, tv_topup;
    private RelativeLayout rl_menu1, rl_menu2, rl_menu3, rl_menu4, rl_menu5, rl_menu6, rl_menu7;
    private CourierMeInfo courierMeInfo;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case courier_me_id://我的驿站
                    if (msg.obj != null) {
                        courierMeInfo = (CourierMeInfo) msg.obj;
                        Glide.with(getActivity()).load(courierMeInfo.member_avatar).into(iv_icon);
                        tv_name.setText(courierMeInfo.dlyp_address_name);
                        tv_phone.setText(courierMeInfo.dlyp_mobile);
                        tv_integral.setText("积分 : " + courierMeInfo.member_points);
                        tv_balance.setText(courierMeInfo.available_predeposit);
                        tv_deduct.setText(courierMeInfo.sms);
                    }
                    break;
                case courier_me_err:
                    Toast.makeText(getActivity(), "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void findviewbrid() {
        iv_icon = (CircleImageView) layout.findViewById(R.id.iv_icon);
        tv_phone = (TextView) layout.findViewById(R.id.tv_phone);
        tv_name = (TextView) layout.findViewById(R.id.tv_name);
        tv_integral = (TextView) layout.findViewById(R.id.tv_integral);
        tv_balance = (TextView) layout.findViewById(R.id.tv_balance);
        tv_deduct = (TextView) layout.findViewById(R.id.tv_deduct);
        tv_topup = (TextView) layout.findViewById(R.id.tv_topup);
        rl_menu1 = (RelativeLayout) layout.findViewById(R.id.rl_menu1);
        rl_menu2 = (RelativeLayout) layout.findViewById(R.id.rl_menu2);
        rl_menu3 = (RelativeLayout) layout.findViewById(R.id.rl_menu3);
        rl_menu4 = (RelativeLayout) layout.findViewById(R.id.rl_menu4);
        rl_menu5 = (RelativeLayout) layout.findViewById(R.id.rl_menu5);
        rl_menu6 = (RelativeLayout) layout.findViewById(R.id.rl_menu6);
        rl_menu7 = (RelativeLayout) layout.findViewById(R.id.rl_menu7);
    }

    @Override
    protected void initView() {
        findviewbrid();
        tv_topup.setOnClickListener(this);
        rl_menu1.setOnClickListener(this);
        rl_menu2.setOnClickListener(this);
        rl_menu3.setOnClickListener(this);
        rl_menu4.setOnClickListener(this);
        rl_menu5.setOnClickListener(this);
        rl_menu6.setOnClickListener(this);
        rl_menu7.setOnClickListener(this);
        netRun = new NetRun(getActivity(), handler);
    }

    @Override
    protected void initData() {
        netRun.CourierMe();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            netRun.CourierMe();
        }
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_courierme;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_topup) {//充值
            Intent intent0 = new Intent(getActivity(), OnlineTopUpActivity.class);
            startActivity(intent0);
        } else if (id == R.id.rl_menu1) {//站点信息
            Intent intent = new Intent(getActivity(), SiteActivity.class);
            startActivity(intent);
        } else if (id == R.id.rl_menu2) {//我的收入
            Intent intent2 = new Intent(getActivity(), AllIncomeActivity.class);
            startActivity(intent2);
        } else if (id == R.id.rl_menu3) {//收款方式
            Intent intent3 = new Intent(getActivity(), CollectionWayActivity.class);
            startActivity(intent3);
        } else if (id == R.id.rl_menu4) {//充值记录
            Intent intent4 = new Intent(getActivity(), TopUpLogActivity.class);
            startActivity(intent4);
        } else if (id == R.id.rl_menu5) {//消费记录
            Intent intent5 = new Intent(getActivity(), ConsumeLogActivity.class);
            startActivity(intent5);
        } else if (id == R.id.rl_menu6) {//积分商城
//                Intent intentjf = new Intent(getActivity(),IntegralShopActivity.class);
//                intentjf.putExtra("person_in", "1");
//                startActivity(intentjf);
            Intent intentjf = new Intent(getActivity(), IntegralMallActivity.class);
            startActivity(intentjf);
        } else if (id == R.id.rl_menu7) {//设置投递费用
            Intent intent7 = new Intent(getActivity(), EditCostActivity.class);
            startActivity(intent7);
        }
    }
}
