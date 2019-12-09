package livestream.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import livestream.mode.BeautifyInfo;

/**
 * 美颜
 */
public class BeautifyPopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private SeekBar sb_beautify3, sb_beautify2, sb_beautify1, sb_filtration;
    private TextView tv_level3, tv_level2, tv_level1, tv_beautifytype1, tv_beautifytype2, tv_beautifytype3, tv_beautify, tv_filter, tv_filtrationlevel;
    private RecyclerView rv_preview;
    private RelativeLayout rl_my,rl_ljprogress;
    private LinearLayout ll_filtration;

    private BeautifyInfo beautifyInfo;
    private FiltrationAdapter filtrationAdapter;
    private int[] beautifyimg = new int[]{R.drawable.zb_orginal, R.drawable.zb_qingliang, R.drawable.zb_qingxin, R.drawable.zb_rixi,
            R.drawable.zb_weimei, R.drawable.zb_huaijiu, R.drawable.zb_landiao, R.drawable.zb_langman, R.drawable.zb_fennen};

    public BeautifyPopu(Activity activity, BeautifyInfo beautifyInfo) {
        mActivity = activity;
        this.beautifyInfo = beautifyInfo;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_zbbeautify, null);
        sb_beautify3 = (SeekBar) view.findViewById(R.id.sb_beautify3);
        sb_beautify2 = (SeekBar) view.findViewById(R.id.sb_beautify2);
        sb_beautify1 = (SeekBar) view.findViewById(R.id.sb_beautify1);
        sb_filtration = (SeekBar) view.findViewById(R.id.sb_filtration);
        tv_level3 = (TextView) view.findViewById(R.id.tv_level3);
        tv_level2 = (TextView) view.findViewById(R.id.tv_level2);
        tv_level1 = (TextView) view.findViewById(R.id.tv_level1);
        tv_beautifytype1 = (TextView) view.findViewById(R.id.tv_beautifytype1);
        tv_beautifytype2 = (TextView) view.findViewById(R.id.tv_beautifytype2);
        tv_beautifytype3 = (TextView) view.findViewById(R.id.tv_beautifytype3);
        tv_beautify = (TextView) view.findViewById(R.id.tv_beautify);
        tv_filter = (TextView) view.findViewById(R.id.tv_filter);
        tv_filtrationlevel = (TextView) view.findViewById(R.id.tv_filtrationlevel);
        rv_preview = (RecyclerView) view.findViewById(R.id.rv_preview);
        rl_my = (RelativeLayout) view.findViewById(R.id.rl_my);
        rl_ljprogress = (RelativeLayout) view.findViewById(R.id.rl_ljprogress);
        ll_filtration = (LinearLayout) view.findViewById(R.id.ll_filtration);

        sb_beautify3.setProgress(beautifyInfo.ruddyLevel);
        sb_beautify2.setProgress(beautifyInfo.whiteningLevel);
        sb_beautify1.setProgress(beautifyInfo.beautyLevel);
        tv_level3.setText(beautifyInfo.ruddyLevel+"");
        tv_level2.setText(beautifyInfo.whiteningLevel+"");
        tv_level1.setText(beautifyInfo.beautyLevel+"");
        tv_filtrationlevel.setText(beautifyInfo.specialratio*10+"");

        Float f = new Float(beautifyInfo.specialratio*10);
        int i = f.intValue();
        sb_filtration.setProgress(i);
        if (beautifyInfo.choosestyle==0){
            rl_ljprogress.setVisibility(View.GONE);
        }else{
            rl_ljprogress.setVisibility(View.VISIBLE);
        }

        tv_beautify.setOnClickListener(this);
        tv_filter.setOnClickListener(this);
        tv_beautifytype1.setOnClickListener(this);
        tv_beautifytype2.setOnClickListener(this);
        tv_beautifytype3.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_preview.setLayoutManager(linearLayoutManager);
        filtrationAdapter=new FiltrationAdapter();
        rv_preview.setAdapter(filtrationAdapter);
        click();
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置点击视图之外的地方是否取消当前的PopupWindow
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        // 设置PopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopup);
    }

    Handler h = new Handler() {
        // 显示玩popup后，改变背景透明度
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    WindowManager.LayoutParams lp = mActivity.getWindow()
                            .getAttributes();
                    lp.alpha = 0.8f;
                    mActivity.getWindow().setAttributes(lp);
                    break;
            }
        }
    };

    private void showEvent() {
        h.sendEmptyMessageDelayed(0, 500);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showEvent();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showEvent();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showEvent();
    }

    /**
     * 监听
     */
    private void click(){
        sb_beautify3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //红润等级
                beautifyInfo.ruddyLevel=progress;
                tv_level3.setText(progress+"");
                mdate.onItemClick(beautifyInfo);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        sb_beautify2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //美白等级
                beautifyInfo.whiteningLevel=progress;
                tv_level2.setText(progress+"");
                mdate.onItemClick(beautifyInfo);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        sb_beautify1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //磨皮等级
                beautifyInfo.beautyLevel=progress;
                tv_level1.setText(progress+"");
                mdate.onItemClick(beautifyInfo);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        sb_filtration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //滤镜程度
                beautifyInfo.specialratio=(float) progress/10;
                tv_filtrationlevel.setText(progress+"");
                mdate.onItemClick(beautifyInfo);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_beautify) {//美颜
            ll_filtration.setVisibility(View.GONE);
            rl_my.setVisibility(View.VISIBLE);
            tv_filter.setTextColor(Color.WHITE);
            tv_beautify.setTextColor(Color.RED);
        } else if (id == R.id.tv_filter) {//滤镜
            rl_my.setVisibility(View.GONE);
            ll_filtration.setVisibility(View.VISIBLE);
            tv_filter.setTextColor(Color.RED);
            tv_beautify.setTextColor(Color.WHITE);
        } else if (id == R.id.tv_beautifytype1) {//光滑
            beautifyInfo.style = 0;
            tv_beautifytype1.setTextColor(Color.RED);
            tv_beautifytype2.setTextColor(Color.WHITE);
            tv_beautifytype3.setTextColor(Color.WHITE);
            mdate.onItemClick(beautifyInfo);
        } else if (id == R.id.tv_beautifytype2) {//自然
            beautifyInfo.style = 1;
            tv_beautifytype2.setTextColor(Color.RED);
            tv_beautifytype1.setTextColor(Color.WHITE);
            tv_beautifytype3.setTextColor(Color.WHITE);
            mdate.onItemClick(beautifyInfo);
        } else if (id == R.id.tv_beautifytype3) {//朦胧
            beautifyInfo.style = 2;
            tv_beautifytype3.setTextColor(Color.RED);
            tv_beautifytype1.setTextColor(Color.WHITE);
            tv_beautifytype2.setTextColor(Color.WHITE);
            mdate.onItemClick(beautifyInfo);
        }
    }


    private date mdate = null;

    public void setdate(date p) {
        mdate = p;
    }

    public interface date {
        void onItemClick(BeautifyInfo beautifyInfo);
    }


    /**
     * 滤镜适配
     */
    private class FiltrationAdapter extends RecyclerView.Adapter<FiltrationHolder> {

        @Override
        public FiltrationHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            FiltrationHolder holder = new FiltrationHolder(LayoutInflater.from(mActivity)
                    .inflate(R.layout.item_zb_filtration, viewGroup, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(FiltrationHolder filtrationHolder, final int i) {
            filtrationHolder.iv_img.setImageResource(beautifyimg[i]);
            if (beautifyInfo.choosestyle==i){
                filtrationHolder.iv_chcoose.setVisibility(View.VISIBLE);
            }else{
                filtrationHolder.iv_chcoose.setVisibility(View.GONE);
            }
            filtrationHolder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    beautifyInfo.choosestyle=i;
                    if (beautifyInfo.choosestyle==0){
                        rl_ljprogress.setVisibility(View.GONE);
                    }else{
                        rl_ljprogress.setVisibility(View.VISIBLE);
                    }
                    mdate.onItemClick(beautifyInfo);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return beautifyimg.length;
        }
    }

    class FiltrationHolder extends RecyclerView.ViewHolder {
        ImageView iv_img, iv_chcoose;

        public FiltrationHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            iv_chcoose = (ImageView) itemView.findViewById(R.id.iv_chcoose);
        }
    }

}
