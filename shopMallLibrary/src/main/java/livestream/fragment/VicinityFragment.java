package livestream.fragment;

import android.graphics.Color;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.adapter.MyFragmentAdapter;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近
 * Created by Administrator on 2017/9/12.
 */
public class VicinityFragment extends BaseFragment implements View.OnClickListener {
    private ImageView iv_sx;
    private TextView tv_menu1, tv_menu2, tv_menu3, tv_add;
    private ViewPager vp_pager;
    private List<Fragment> list;

    @Override
    protected int getlayoutResId() {
        return R.layout.fragment_zb_vicinity;
    }

    private void findviewbyid() {
        iv_sx = (ImageView) layout.findViewById(R.id.iv_sx);
        tv_menu1 = (TextView) layout.findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) layout.findViewById(R.id.tv_menu2);
        tv_menu3 = (TextView) layout.findViewById(R.id.tv_menu3);
        tv_add = (TextView) layout.findViewById(R.id.tv_add);
        vp_pager = (ViewPager) layout.findViewById(R.id.vp_pager);
    }

    @Override
    protected void initView() {
        findviewbyid();
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        tv_menu3.setOnClickListener(this);
        iv_sx.setOnClickListener(this);
        tv_add.setOnClickListener(this);

        list = new ArrayList<>();
        list.add(new FragmentVicinity1());
        list.add(new FragmentVicinity2());
        list.add(new FragmentVicinity3());
        FragmentManager childFragmentManager = getChildFragmentManager();

        vp_pager.setAdapter(new MyFragmentAdapter(childFragmentManager, list));
        vp_pager.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_menu1) {//直播
            vp_pager.setCurrentItem(0);
        } else if (id == R.id.tv_menu2) {//动态
            vp_pager.setCurrentItem(1);
        } else if (id == R.id.tv_menu3) {//人
            vp_pager.setCurrentItem(2);
        } else if (id == R.id.tv_add) {//更多
        } else if (id == R.id.iv_sx) {//筛选
        }
    }

    private int pagernum = 0;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int index) {
            pagernum = index;
            switch (index) {
                case 0:
                    tv_menu1.setTextColor(Color.BLACK);
                    tv_menu2.setTextColor(0XFF808080);
                    tv_menu3.setTextColor(0XFF808080);

                    break;
                case 1:
                    tv_menu2.setTextColor(Color.BLACK);
                    tv_menu1.setTextColor(0XFF808080);
                    tv_menu3.setTextColor(0XFF808080);
                    break;
                case 2:
                    tv_menu3.setTextColor(Color.BLACK);
                    tv_menu1.setTextColor(0XFF808080);
                    tv_menu2.setTextColor(0XFF808080);
                    break;
            }
        }

        @Override
        public void onPageScrolled(int index, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
}
