package courier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.base.Mark;
import com.aiteshangcheng.a.R;

import courier.fragment.CourierHomeFragment;
import courier.fragment.CourierMeFragment;
import courier.fragment.CourierSendFragment;
import courier.fragment.CourierWarehouseFragment;

/**
 * 驿站首页
 * Created by Administrator on 2018/1/8.
 */
public class CourierHomeTabActivity extends FragmentActivity implements View.OnClickListener, Mark {

    private FrameLayout fm_main;
    private LinearLayout ll_menu_1, ll_menu_2, ll_menu_3, ll_menu_4;
    private ImageView iv_menu_home, iv_menu_storage, iv_menu_send, iv_menu_me;
    private TextView tv_menu_home, tv_menu_storage, tv_menu_send, tv_menu_me;
    private int lastIndex = -1;
    private Fragment[] fragments = new Fragment[4];//页面数组
    private boolean mReceiverTag = false; // 广播接受者标识
    private RadioListening receiver = new RadioListening();//广播接收
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HOME_SETMENU:
                    if (msg.obj != null) {
                        showFragment(1);
                        iv_menu_home.setImageResource(R.drawable.courier_menua1);
                        iv_menu_storage.setImageResource(R.drawable.courier_menub2);
                        iv_menu_send.setImageResource(R.drawable.courier_menuc1);
                        iv_menu_me.setImageResource(R.drawable.courier_menud1);
                        tv_menu_home.setTextColor(0xff808080);
                        tv_menu_storage.setTextColor(0xff17B8FF);
                        tv_menu_send.setTextColor(0xff808080);
                        tv_menu_me.setTextColor(0xff808080);
                        String id = (String) msg.obj;
                        CourierWarehouseFragment fragment= (CourierWarehouseFragment) fragments[1];
                        fragment.setid(id);
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_couriertabhome);
        findViewById();
    }

    private void findViewById() {
        fm_main = (FrameLayout) findViewById(R.id.fm_main);
        ll_menu_1 = (LinearLayout) findViewById(R.id.ll_menu_1);
        ll_menu_2 = (LinearLayout) findViewById(R.id.ll_menu_2);
        ll_menu_3 = (LinearLayout) findViewById(R.id.ll_menu_3);
        ll_menu_4 = (LinearLayout) findViewById(R.id.ll_menu_4);
        iv_menu_home = (ImageView) findViewById(R.id.iv_menu_home);
        iv_menu_storage = (ImageView) findViewById(R.id.iv_menu_storage);
        iv_menu_send = (ImageView) findViewById(R.id.iv_menu_send);
        iv_menu_me = (ImageView) findViewById(R.id.iv_menu_me);
        tv_menu_home = (TextView) findViewById(R.id.tv_menu_home);
        tv_menu_storage = (TextView) findViewById(R.id.tv_menu_storage);
        tv_menu_send = (TextView) findViewById(R.id.tv_menu_send);
        tv_menu_me = (TextView) findViewById(R.id.tv_menu_me);
        initView();
    }

    private void initView() {
        ll_menu_1.setOnClickListener(this);
        ll_menu_2.setOnClickListener(this);
        ll_menu_3.setOnClickListener(this);
        ll_menu_4.setOnClickListener(this);
        registerBR();
        showFragment(0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_menu_1) {//首页
            showFragment(0);
            iv_menu_home.setImageResource(R.drawable.courier_menua2);
            iv_menu_storage.setImageResource(R.drawable.courier_menub1);
            iv_menu_send.setImageResource(R.drawable.courier_menuc1);
            iv_menu_me.setImageResource(R.drawable.courier_menud1);

            tv_menu_home.setTextColor(0xff17B8FF);
            tv_menu_storage.setTextColor(0xff808080);
            tv_menu_send.setTextColor(0xff808080);
            tv_menu_me.setTextColor(0xff808080);
        } else if (id == R.id.ll_menu_2) {//入库
            showFragment(1);
            iv_menu_home.setImageResource(R.drawable.courier_menua1);
            iv_menu_storage.setImageResource(R.drawable.courier_menub2);
            iv_menu_send.setImageResource(R.drawable.courier_menuc1);
            iv_menu_me.setImageResource(R.drawable.courier_menud1);

            tv_menu_home.setTextColor(0xff808080);
            tv_menu_storage.setTextColor(0xff17B8FF);
            tv_menu_send.setTextColor(0xff808080);
            tv_menu_me.setTextColor(0xff808080);
            CourierWarehouseFragment fragment = (CourierWarehouseFragment) fragments[1];
            fragment.setid(null);
        } else if (id == R.id.ll_menu_3) {//寄件
            showFragment(2);
            iv_menu_home.setImageResource(R.drawable.courier_menua1);
            iv_menu_storage.setImageResource(R.drawable.courier_menub1);
            iv_menu_send.setImageResource(R.drawable.courier_menuc2);
            iv_menu_me.setImageResource(R.drawable.courier_menud1);

            tv_menu_home.setTextColor(0xff808080);
            tv_menu_storage.setTextColor(0xff808080);
            tv_menu_send.setTextColor(0xff17B8FF);
            tv_menu_me.setTextColor(0xff808080);
        } else if (id == R.id.ll_menu_4) {//我的
            showFragment(3);
            iv_menu_home.setImageResource(R.drawable.courier_menua1);
            iv_menu_storage.setImageResource(R.drawable.courier_menub1);
            iv_menu_send.setImageResource(R.drawable.courier_menuc1);
            iv_menu_me.setImageResource(R.drawable.courier_menud2);

            tv_menu_home.setTextColor(0xff808080);
            tv_menu_storage.setTextColor(0xff808080);
            tv_menu_send.setTextColor(0xff808080);
            tv_menu_me.setTextColor(0xff17B8FF);
        }
    }

    /**
     * 加载Fragment
     *
     * @param index
     */
    public void showFragment(int index) {
        if (index == lastIndex) {
            return;
        }
        FragmentTransaction bt = getSupportFragmentManager().beginTransaction();
        if (lastIndex != -1) {
            bt.hide(fragments[lastIndex]);
        }
        if (fragments[index] == null) {
            bt.add(R.id.fm_main, createFragment(index));
        } else {
            bt.show(fragments[index]);
        }
//        bt.commit();
        bt.commitAllowingStateLoss();
        lastIndex = index;
    }

    private Fragment createFragment(int index) {
        switch (index) {
            case 0:
                return (fragments[index] = new CourierHomeFragment());
            case 1:
                return (fragments[index] = new CourierWarehouseFragment());
            case 2:
                return (fragments[index] = new CourierSendFragment());
            case 3:
                return (fragments[index] = new CourierMeFragment());
        }
        return null;
    }

    /**
     * 动态注册
     */
    private void registerBR() {
        // 过滤器
        IntentFilter filter = new IntentFilter();
        filter.addAction("homemenu");
        filter.addAction("cartnum");
        // 优先级
        filter.setPriority(777);
        // 种类
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        // 登记接受
        if (!mReceiverTag) { // 避免重复注册广播
            mReceiverTag = true;
            registerReceiver(receiver, filter);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);//刚添加的;
        super.onDestroy();
    }

    /**
     * 广播
     */
    public class RadioListening extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null)
                return;
            if (intent.getAction().equals("homemenu")) {
                //修改选中
                Bundle bundle = intent.getExtras();
                String id = bundle.getString("id");
                handler.sendMessage(handler.obtainMessage(HOME_SETMENU, id));
            }
        }
    }
}
