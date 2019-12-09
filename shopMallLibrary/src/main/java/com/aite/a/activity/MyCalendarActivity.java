package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.MyCalendarInfo;
import com.aite.a.model.SigninInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyDialog;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;
import com.lidroid.xutils.BitmapUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 签到
 * Created by Administrator on 2017/11/8.
 */
public class MyCalendarActivity extends BaseActivity implements View.OnClickListener {
    private GridView gv_calendar;
    private TextView  tv_dates,tv_continuous,tv_total;
    private ImageView iv_goback,iv_signin,iv_errorimage;
    private MyAdapter myAdapter;
    private SigninInfo date;
    private MyDialog errordialog;
    private View inflate;
    private NetRun netRun;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case member_sign_id:
                    //签到
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            errordialog.show();
                            netRun.SignDate();
                            Toast.makeText(MyCalendarActivity.this, getString(R.string.member_centre9), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyCalendarActivity.this, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case member_sign_err:
                    Toast.makeText(MyCalendarActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case sign_date_id://签到日期
                    if (msg.obj != null) {
                        date = (SigninInfo) msg.obj;
                        if (date.thisMonthInfo!=null){
                            myAdapter.refreshData(date.thisMonthInfo);
                        }
                        tv_continuous.setText(getString(R.string.goodsdatails_reminder17)+(date.continuousCount==null?"0":date.continuousCount)+getString(R.string.day));
                        tv_total.setText(getString(R.string.goodsdatails_reminder18)+(date.signCount==null?"0":date.signCount)+getString(R.string.day));
                    }
                    break;
                case sign_date_err:
                    Toast.makeText(MyCalendarActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        findViewById();
    }

    @Override
    protected void findViewById() {
        gv_calendar = findViewById(R.id.gv_calendar);
        iv_signin = findViewById(R.id.iv_signin);
        tv_dates = findViewById(R.id.tv_dates);
        iv_goback = findViewById(R.id.iv_goback);
        tv_total = findViewById(R.id.tv_total);
        tv_continuous = findViewById(R.id.tv_continuous);
        inflate= View.inflate(this, R.layout.dialog_error,null);
        iv_errorimage = (ImageView)inflate. findViewById(R.id.iv_errorimage);
        initView();
    }

    @Override
    protected void initView() {
        iv_errorimage.setImageResource(R.drawable.signin_icon5);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_signin.getLayoutParams();
        layoutParams.width= (int) (ClutterUtils.getScreenWidth(this)-ClutterUtils.dp2px(this,40)/6.536);
        iv_signin.setLayoutParams(layoutParams);
        iv_goback.setOnClickListener(this);
        bitmapUtils = new BitmapUtils(this);
        netRun = new NetRun(this, handler);

        errordialog = new MyDialog(this, ClutterUtils.getScreenWidth(this),150,
                inflate, R.style.loading_dialog);
        errordialog.setCanceledOnTouchOutside(false);
        iv_signin.setOnClickListener(this);
        iv_errorimage.setOnClickListener(this);
        myAdapter = new MyAdapter(getCurrentMonthLastDay());
        gv_calendar.setAdapter(myAdapter);
        initData();
    }

    @Override
    protected void initData() {
        netRun.SignDate();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_signin:
//                //签到
//                netRun.MemberSign();
//                break;
//            case R.id.iv_errorimage:
//                errordialog.dismiss();
//                break;
//            case R.id.iv_goback://返回
//                finish();
//                break;
//        }
        if(v.getId()==R.id.iv_signin){
            //签到
            netRun.MemberSign();
        }else if(v.getId()==R.id.iv_errorimage){
            errordialog.dismiss();
        }else if(v.getId()==R.id.iv_goback){
            finish();
        }

    }

    /**
     * 日期适配
     */
    private class MyAdapter extends BaseAdapter {
        List<MyCalendarInfo> data;

        public MyAdapter(List<MyCalendarInfo> data) {
            this.data = data;
        }

        /**
         * 刷新签到
         *
         * @param choose
         */
        public void refreshData(List<String> choose) {
            for (int i = 0; i < choose.size(); i++) {
                for (int j = 0; j < data.size(); j++) {
                    if (choose.get(i).equals(data.get(j).day)) {
                        data.get(j).ishoose = true;
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data == null ? null : data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(MyCalendarActivity.this, R.layout.item_day, null);
                new ViewHodler(convertView);
            }
            ViewHodler holder = (ViewHodler) convertView.getTag();
            MyCalendarInfo myCalendarInfo = data.get(position);
            if (myCalendarInfo.ishoose) {
                holder.tv_day.setTextColor(Color.WHITE);
                holder.iv_choose.setVisibility(View.VISIBLE);
            } else {
                holder.tv_day.setTextColor(Color.BLACK);
                holder.iv_choose.setVisibility(View.GONE);
            }
            holder.tv_day.setText(myCalendarInfo.day);
            return convertView;
        }

        class ViewHodler {
            ImageView iv_choose;
            TextView tv_day;

            public ViewHodler(View convertView) {
                iv_choose = (ImageView) convertView.findViewById(R.id.iv_choose);
                tv_day = (TextView) convertView.findViewById(R.id.tv_day);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 取得当月天数
     */
    public List<MyCalendarInfo> getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);//当月天数

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        int year = a.get(Calendar.YEAR);
        int month = a.get(Calendar.MONTH) + 1;
        tv_dates.setText(year + getString(R.string.member_centre10) + month + getString(R.string.member_centre11));
        String datetime = year + "-" + (month > 10 ? "0" + month : month) + "-01";
        Date datet = null;
        try {
            datet = f.parse(datetime);
            a.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = a.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0) w = 0;//空白位置数量
        List<MyCalendarInfo> data = new ArrayList<>();
        for (int i = 0; i < w; i++) {
            data.add(new MyCalendarInfo("", false));
        }
        for (int i = 0; i < maxDate; i++) {
            data.add(new MyCalendarInfo((i + 1) + "", false));
        }
        return data;
    }
}
