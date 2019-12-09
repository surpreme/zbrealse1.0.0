package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.User;
import com.aite.a.parse.NetRun;

public class PersonalMiYaActivity extends BaseActivity implements
        OnClickListener {

    private TextView tv_user_name, tv_miya_type, tv_miyajifennum,
            tv_miyamidounum, tv_shimingrenzheng, tv_miyaphone, tv_miyalongin,
            tv_miyaregistered;
    private ImageView iv_miya_shezhi, miya_xiaoxi, iv_portrait,
            iv_miyatyprshezhi, iv_miyaguanggao;
    private LinearLayout ll_miyashoucang, ll_miyaliulan, ll_miyaquanbudingdan,
            ll_miyadaifukuan, ll_miyadaifahuo, ll_miyayifahuo, ll_tuihuo,
            ll_miyaloginfalse;
    private GridView miyagv_menu, miyagv_hot;
    private RelativeLayout rl_miyazaixiankefu, rl_miyakefudianhua, rl_miyahot,
            rl_miyalongintrue;
    private NetRun netRun;
    protected User user;
    private MenuAdapter menuadapter;
    private String kefuQQ = "1434236000";
    private String[] menutext = new String[]{"购物车", "订单", "地址", "改密", "资料",
            "评价", "虚拟订单", "服务订单"};
    private int[] menuimage = new int[]{R.drawable.gouwuche,
            R.drawable.maijiadingdan, R.drawable.dizhi, R.drawable.xiugaimima,
            R.drawable.gerenziliao, R.drawable.myevaluation,
            R.drawable.xuniorder, R.drawable.fuwuorder};
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case member_id:
                    if (msg.obj != null) {
                        user = (User) msg.obj;
                        initView();
                    }
                    break;
                case member_err:

                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miya_personal);
        findViewById();
    }

    @Override
    protected void findViewById() {
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_miya_type = (TextView) findViewById(R.id.tv_miya_type);
        tv_miyajifennum = (TextView) findViewById(R.id.tv_miyajifennum);
        tv_miyamidounum = (TextView) findViewById(R.id.tv_miyamidounum);
        tv_shimingrenzheng = (TextView) findViewById(R.id.tv_shimingrenzheng);
        tv_miyaphone = (TextView) findViewById(R.id.tv_miyaphone);
        tv_miyaregistered = (TextView) findViewById(R.id.tv_miyaregistered);
        iv_miya_shezhi = (ImageView) findViewById(R.id.iv_miya_shezhi);
        miya_xiaoxi = (ImageView) findViewById(R.id.miya_xiaoxi);
        iv_portrait = (ImageView) findViewById(R.id.iv_portrait);
        iv_miyatyprshezhi = (ImageView) findViewById(R.id.iv_miyatyprshezhi);
        iv_miyaguanggao = (ImageView) findViewById(R.id.iv_miyaguanggao);
        ll_miyashoucang = (LinearLayout) findViewById(R.id.ll_miyashoucang);
        ll_miyaliulan = (LinearLayout) findViewById(R.id.ll_miyaliulan);
        ll_miyaquanbudingdan = (LinearLayout) findViewById(R.id.ll_miyaquanbudingdan);
        ll_miyadaifukuan = (LinearLayout) findViewById(R.id.ll_miyadaifukuan);
        ll_miyadaifahuo = (LinearLayout) findViewById(R.id.ll_miyadaifahuo);
        ll_miyayifahuo = (LinearLayout) findViewById(R.id.ll_miyayifahuo);
        ll_tuihuo = (LinearLayout) findViewById(R.id.ll_tuihuo);
        ll_miyaloginfalse = (LinearLayout) findViewById(R.id.ll_miyaloginfalse);
        miyagv_menu = (GridView) findViewById(R.id.miyagv_menu);
        miyagv_hot = (GridView) findViewById(R.id.miyagv_hot);
        rl_miyazaixiankefu = (RelativeLayout) findViewById(R.id.rl_miyazaixiankefu);
        rl_miyakefudianhua = (RelativeLayout) findViewById(R.id.rl_miyakefudianhua);
        rl_miyahot = (RelativeLayout) findViewById(R.id.rl_miyahot);
        rl_miyalongintrue = (RelativeLayout) findViewById(R.id.rl_miyalongintrue);

        ll_miyashoucang.setOnClickListener(this);
        ll_miyaliulan.setOnClickListener(this);
        iv_portrait.setOnClickListener(this);
        iv_miya_shezhi.setOnClickListener(this);
        tv_shimingrenzheng.setOnClickListener(this);
        rl_miyakefudianhua.setOnClickListener(this);
        rl_miyazaixiankefu.setOnClickListener(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun = new NetRun(this, handler);
        if (State.UserKey != null) {
            rl_miyalongintrue.setVisibility(View.VISIBLE);
            ll_miyaloginfalse.setVisibility(View.GONE);
            netRun.getMember();
        } else {
            rl_miyalongintrue.setVisibility(View.GONE);
            ll_miyaloginfalse.setVisibility(View.VISIBLE);
        }
        menuadapter = new MenuAdapter();
        miyagv_menu.setAdapter(menuadapter);
        miyagv_menu.setOnItemClickListener(onitem);
    }

    @Override
    protected void initView() {
        tv_user_name.setText(user.user_name);
        tv_miyajifennum.setText(user.predepoit);
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_miyashoucang) {
            intent = new Intent(this, FavoriteListFargmentActivity.class);
            intent.putExtra("i", 1);
            startActivity(intent);
        } else if (v.getId() == R.id.ll_miyaliulan) {
            startActivity(new Intent(PersonalMiYaActivity.this,
                    MyfootprintActivity.class));
        } else if (v.getId() == R.id.iv_portrait) {
            Bundle bundle3 = new Bundle();
            bundle3.putString("icon", user.avator);
            openActivity(PersonalInformationActivity.class, bundle3);
        } else if (v.getId() == R.id.iv_miya_shezhi) {
            openActivity(MoreActivity.class);
            overrIn();
        } else if (v.getId() == R.id.tv_shimingrenzheng) {
            openActivity(AddressManageActivity.class);
            overrIn();
        } else if (v.getId() == R.id.rl_miyazaixiankefu) {
            String url = "mqqwpa://im/main_chat?chat_type=wpa&uin=" + kefuQQ;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
//		switch (v.getId()) {
//		case R.id.ll_miyashoucang:
//			intent = new Intent(this, FavoriteListFargmentActivity.class);
//			intent.putExtra("i", 1);
//			startActivity(intent);
//			break;
//		case R.id.ll_miyaliulan:
//			startActivity(new Intent(PersonalMiYaActivity.this,
//					MyfootprintActivity.class));
//			break;
//		case R.id.iv_portrait:
//			Bundle bundle3 = new Bundle();
//			bundle3.putString("icon", user.avator);
//			openActivity(PersonalInformationActivity.class, bundle3);
//			break;
//		case R.id.iv_miya_shezhi:
//			openActivity(MoreActivity.class);
//			overrIn();
//			break;
//		case R.id.tv_shimingrenzheng:
//			openActivity(AddressManageActivity.class);
//			overrIn();
//			break;
//		case R.id.rl_miyakefudianhua:
////			String myString = tv_miyaphone.getText().toString().replace("-", "");
////			Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+myString));
////            startActivity(intent);
//			break;
//		case R.id.rl_miyazaixiankefu:
//			String url = "mqqwpa://im/main_chat?chat_type=wpa&uin=" + kefuQQ;
//			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//			break;
//
//		}
    }


    /**
     * 买家中心适配器
     *
     * @author Administrator
     */
    public class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return menutext.length;
        }

        @Override
        public Object getItem(int position) {
            return menutext == null ? null : menutext[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(PersonalMiYaActivity.this,
                        R.layout.miyamenuitem, null);
                new ViewHandler(convertView);
            }
            ViewHandler handler = (ViewHandler) convertView.getTag();
            handler.iv_miyamenuiag.setBackgroundResource(menuimage[position]);
            handler.tv_miyamenutext.setText(menutext[position]);
            return convertView;
        }

        class ViewHandler {
            ImageView iv_miyamenuiag;
            TextView tv_miyamenutext;

            public ViewHandler(View convertView) {
                iv_miyamenuiag = (ImageView) convertView
                        .findViewById(R.id.iv_miyamenuiag);
                tv_miyamenutext = (TextView) convertView
                        .findViewById(R.id.tv_miyamenutext);
                convertView.setTag(this);
            }
        }
    }

    private OnItemClickListener onitem = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            switch (position) {
                case 0:
                    Bundle bundle5 = new Bundle();
                    bundle5.putString("shoopping", "shoopping");
                    openActivity(CartActivity.class, bundle5);
                    overrIn();
                    break;
                case 1:
                    openActivity(BuyerOrderFgActivity.class);
                    overrIn();
                    break;
                case 2:
                    openActivity(AddressManageActivity.class);
                    overrIn();
                    break;
                case 3:
                    openActivity(ChangePassword.class);
                    overrIn();
                    break;
                case 4:
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("icon", user.avator);
                    openActivity(PersonalInformationActivity.class, bundle3);
                    break;
                case 5:
                    Intent intent6 = new Intent(PersonalMiYaActivity.this,
                            Myevaluation.class);
                    intent6.putExtra("touxiang", user.avator);
                    intent6.putExtra("names", user.user_name);
                    startActivity(intent6);
                    break;
                case 6:
                    Intent intent3 = new Intent(PersonalMiYaActivity.this,
                            VrOrder.class);
                    intent3.putExtra("type", "0");
                    startActivity(intent3);
                    break;
                case 7:
                    Intent intent4 = new Intent(PersonalMiYaActivity.this,
                            VrOrder.class);
                    intent4.putExtra("type", "1");
                    startActivity(intent4);
                    break;
            }
        }
    };
}
