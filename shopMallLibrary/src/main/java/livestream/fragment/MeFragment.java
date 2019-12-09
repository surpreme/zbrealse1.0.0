package livestream.fragment;

import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;

/**
 * 我的
 * Created by Administrator on 2017/10/13.
 */
public class MeFragment extends com.aite.a.fargment.BaseFragment{
    private RelativeLayout rl_title,rl_menu1,rl_menu2;
    private ImageView iv_return;
    private TextView tv_name,tv_eitnumber,tv_edit,tv_level,tv_motto,tv_gz,tv_fs,tv_dz;
    private CircleImageView iv_iocn;
    private View v_isnew;
    private ViewPager vp_pager;

    private void findviewbyid(){
        rl_title= (RelativeLayout) layout.findViewById(R.id.rl_title);
        rl_menu1= (RelativeLayout) layout.findViewById(R.id.rl_menu1);
        rl_menu2= (RelativeLayout) layout.findViewById(R.id.rl_menu2);
        iv_return= (ImageView) layout.findViewById(R.id.iv_return);
        tv_name= (TextView) layout.findViewById(R.id.tv_name);
        tv_eitnumber= (TextView) layout.findViewById(R.id.tv_eitnumber);
        tv_edit= (TextView) layout.findViewById(R.id.tv_edit);
        tv_level= (TextView) layout.findViewById(R.id.tv_level);
        tv_motto= (TextView) layout.findViewById(R.id.tv_motto);
        tv_gz= (TextView) layout.findViewById(R.id.tv_gz);
        tv_fs= (TextView) layout.findViewById(R.id.tv_fs);
        tv_dz= (TextView) layout.findViewById(R.id.tv_dz);
        iv_iocn= (CircleImageView) layout.findViewById(R.id.iv_iocn);
        v_isnew=layout.findViewById(R.id.v_isnew);
        vp_pager= (ViewPager) layout.findViewById(R.id.vp_pager);
    }

    @Override
    protected void initView() {
        findviewbyid();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_zb_me;
    }
}
