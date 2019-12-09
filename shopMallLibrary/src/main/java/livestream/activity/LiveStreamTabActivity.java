package livestream.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.tencent.rtmp.TXLiveBase;

import livestream.fragment.LiveStreamFragment;
import livestream.fragment.MeFragment;
import livestream.fragment.VicinityFragment;
import livestream.fragment.VideoFragment;
import livestream.view.PostedPopu;

/**
 * 直播主页
 * Created by Administrator on 2017/9/12.
 */
public class LiveStreamTabActivity extends FragmentActivity implements View.OnClickListener{
    private FrameLayout fm_main;
    public static LinearLayout ll_menu_home, ll_menu_classification, ll_menu_members, ll_menu_mall;
    private ImageView iv_menu_home, iv_menu_classification, iv_menu_distribution, iv_menu_members, iv_menu_mall;
    private TextView tv_menu_home, tv_menu_classification, tv_menu_distribution, tv_menu_members, tv_menu_mall;
    private int lastIndex = -1;
    private Fragment[] fragments = new Fragment[4];//页面数组
    public static RelativeLayout ll_menu_distribution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_livestreamhome);
        findViewById();
        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("------------------", "直播版本 : " + sdkver);
    }

    protected void findViewById() {
        fm_main = (FrameLayout) findViewById(R.id.fm_main);
        ll_menu_home = (LinearLayout) findViewById(R.id.ll_menu_home);
        ll_menu_classification = (LinearLayout) findViewById(R.id.ll_menu_classification);
        ll_menu_mall = (LinearLayout) findViewById(R.id.ll_menu_mall);
        ll_menu_distribution = (RelativeLayout) findViewById(R.id.ll_menu_distribution);
        ll_menu_members = (LinearLayout) findViewById(R.id.ll_menu_members);
        iv_menu_home = (ImageView) findViewById(R.id.iv_menu_home);
        iv_menu_classification = (ImageView) findViewById(R.id.iv_menu_classification);
        iv_menu_distribution = (ImageView) findViewById(R.id.iv_menu_distribution);
        iv_menu_mall = (ImageView) findViewById(R.id.iv_menu_mall);
        iv_menu_members = (ImageView) findViewById(R.id.iv_menu_members);
        tv_menu_home = (TextView) findViewById(R.id.tv_menu_home);
        tv_menu_classification = (TextView) findViewById(R.id.tv_menu_classification);
        tv_menu_distribution = (TextView) findViewById(R.id.tv_menu_distribution);
        tv_menu_members = (TextView) findViewById(R.id.tv_menu_members);
        tv_menu_mall = (TextView) findViewById(R.id.tv_menu_mall);
        initView();
    }

    protected void initView() {
        ll_menu_home.setOnClickListener(this);
        ll_menu_classification.setOnClickListener(this);
        ll_menu_distribution.setOnClickListener(this);
        ll_menu_members.setOnClickListener(this);
        ll_menu_mall.setOnClickListener(this);
        showFragment(0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_menu_home) {
            showFragment(0);
            tv_menu_home.setTextColor(Color.BLACK);
            tv_menu_classification.setTextColor(0xFF808080);
            tv_menu_distribution.setTextColor(0xFF808080);
            tv_menu_members.setTextColor(0xFF808080);
            tv_menu_mall.setTextColor(0xFF808080);

            iv_menu_home.setBackgroundResource(R.drawable.live_streammenua2);
            iv_menu_classification.setBackgroundResource(R.drawable.live_streammenub1);
            iv_menu_distribution.setBackgroundResource(R.drawable.live_streammenuc1);
            iv_menu_members.setBackgroundResource(R.drawable.live_streammenud1);
            iv_menu_mall.setBackgroundResource(R.drawable.live_streammenue1);
        } else if (id == R.id.ll_menu_classification) {
            showFragment(1);
            tv_menu_classification.setTextColor(Color.BLACK);
            tv_menu_home.setTextColor(0xFF808080);
            tv_menu_distribution.setTextColor(0xFF808080);
            tv_menu_members.setTextColor(0xFF808080);
            tv_menu_mall.setTextColor(0xFF808080);

            iv_menu_home.setBackgroundResource(R.drawable.live_streammenua1);
            iv_menu_classification.setBackgroundResource(R.drawable.live_streammenub2);
            iv_menu_distribution.setBackgroundResource(R.drawable.live_streammenuc1);
            iv_menu_members.setBackgroundResource(R.drawable.live_streammenud1);
            iv_menu_mall.setBackgroundResource(R.drawable.live_streammenue1);
        } else if (id == R.id.ll_menu_distribution) {//开趴
            showpopu();
        } else if (id == R.id.ll_menu_members) {//附近
            showFragment(2);
            tv_menu_members.setTextColor(Color.BLACK);
            tv_menu_classification.setTextColor(0xFF808080);
            tv_menu_distribution.setTextColor(0xFF808080);
            tv_menu_home.setTextColor(0xFF808080);
            tv_menu_mall.setTextColor(0xFF808080);

            iv_menu_home.setBackgroundResource(R.drawable.live_streammenua1);
            iv_menu_classification.setBackgroundResource(R.drawable.live_streammenub1);
            iv_menu_distribution.setBackgroundResource(R.drawable.live_streammenuc1);
            iv_menu_members.setBackgroundResource(R.drawable.live_streammenud2);
            iv_menu_mall.setBackgroundResource(R.drawable.live_streammenue1);
        } else if (id == R.id.ll_menu_mall) {//我的
            showFragment(3);
            tv_menu_mall.setTextColor(Color.BLACK);
            tv_menu_classification.setTextColor(0xFF808080);
            tv_menu_distribution.setTextColor(0xFF808080);
            tv_menu_home.setTextColor(0xFF808080);
            tv_menu_members.setTextColor(0xFF808080);

            iv_menu_home.setBackgroundResource(R.drawable.live_streammenua1);
            iv_menu_classification.setBackgroundResource(R.drawable.live_streammenub1);
            iv_menu_distribution.setBackgroundResource(R.drawable.live_streammenuc1);
            iv_menu_members.setBackgroundResource(R.drawable.live_streammenud1);
            iv_menu_mall.setBackgroundResource(R.drawable.live_streammenue2);
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
                return (fragments[index] = new LiveStreamFragment());
            case 1:
                return (fragments[index] = new VideoFragment());
            case 2:
                return (fragments[index] = new VicinityFragment());
            case 3:
                return (fragments[index] = new MeFragment());
        }
        return null;
    }

    /**
     * 显示弹窗
     */
    private void showpopu(){
        PostedPopu postedPopu=new PostedPopu(LiveStreamTabActivity.this);
        postedPopu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));
        postedPopu.showAtLocation(ll_menu_distribution, Gravity.BOTTOM,0,0);
    }

}
