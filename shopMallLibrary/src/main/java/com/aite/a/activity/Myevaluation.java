package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.Myevaluationinfo;
import com.aite.a.model.User;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的评价
 *
 * @author Administrator
 */
public class Myevaluation extends BaseActivity implements OnClickListener {
    private TextView _tv_name, tv_praise, tv_amount, tv_evausername,
            tv_qupingjia;
    private ImageView _iv_back;
    private ListView lv_myeva;
    private CircleImageView iv_portrait;
    private NetRun netRun;
    protected User user;
    private int hp = 0;

    private List<Myevaluationinfo> myeva = new ArrayList<Myevaluationinfo>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case member_id:
                    if (msg.obj != null) {
                        user = (User) msg.obj;
                        bitmapUtils.display(iv_portrait, user.avator);
                        tv_evausername.setText(user.user_name);
                    }
                    netRun.getEvaluation();
                    break;
                case comment_id:
                    if (msg.obj != null) {
                        hp = 0;
                        myeva = (List<Myevaluationinfo>) msg.obj;
                        tv_amount.setText(myeva.size() + "");
                        for (int i = 0; i < myeva.size(); i++) {
                            int j = Integer.valueOf(myeva.get(i).geval_scores)
                                    .intValue();
                            if (j >= 4) {

                                hp++;
                            }
                        }
                        tv_praise.setText(hp + "");
                        myadapderr = new myadapder();
                        lv_myeva.setAdapter(myadapderr);
                    }
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myevaluation);
        netRun = new NetRun(Myevaluation.this, handler);
        findViewById();
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (State.UserKey == null) {
            Intent intent2 = new Intent(Myevaluation.this, LoginActivity.class);
            startActivity(intent2);
            finish();
        } else {
            netRun.getMember();
        }
    }

    @Override
    public void ReGetData() {

    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_praise = (TextView) findViewById(R.id.tv_praise);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_qupingjia = (TextView) findViewById(R.id.tv_qupingjia);
        tv_qupingjia.setOnClickListener(this);
        tv_evausername = (TextView) findViewById(R.id.tv_evausername);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _iv_back.setOnClickListener(this);
        iv_portrait = (CircleImageView) findViewById(R.id.iv_portrait);
        lv_myeva = (ListView) findViewById(R.id.lv_myeva);
        bitmapUtils = new BitmapUtils(this);
        lv_myeva.setOnItemClickListener(listener);
    }

    public OnItemClickListener listener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            String goods_id = myeva.get(position).geval_goodsid;
            Intent intent2 = new Intent(Myevaluation.this,
                    ProductDetailsActivity.class);
            intent2.putExtra("goods_id", goods_id);
            startActivity(intent2);
        }
    };

    private myadapder myadapderr;

    @Override
    protected void initView() {
        String touxiang = getIntent().getStringExtra("touxiang");
        String names = getIntent().getStringExtra("names");
        if (touxiang != null) {
            bitmapUtils.display(iv_portrait, touxiang);
        }
        if (names != null) {
            tv_evausername.setText(names);
        }
    }

    @Override
    protected void initData() {

        _tv_name.setText(getI18n(R.string.myevaluation));
    }

    public class myadapder extends BaseAdapter {

        @Override
        public int getCount() {
            return myeva.size();
        }

        @Override
        public Object getItem(int position) {

            return myeva.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(Myevaluation.this,
                        R.layout.myevaluationlist_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();

            bitmapUtils.display(holder.iv_goodsimg,
                    myeva.get(position).geval_goodsimage);
            holder.tv_evagoodsname.setText(myeva.get(position).geval_goodsname);
            holder.tv_evaluationcontent.setText(getString(R.string.myevaluation) + " :\t"
                    + myeva.get(position).geval_content);
            holder.tv_timeeva.setText(TimeStamp2Date(
                    myeva.get(position).geval_addtime, "yyyy-MM-dd HH:mm:ss"));
            holder.rb_score
                    .setRating(Float.parseFloat(myeva.get(position).geval_scores));

            if (myeva.get(position).geval_image == null) {
                holder.tv_shaidan.setVisibility(View.VISIBLE);
                holder.mgv_sd.setVisibility(View.GONE);
            } else {
                if (myeva.get(position).geval_image.size() == 0) {
                    holder.tv_shaidan.setVisibility(View.VISIBLE);
                    holder.mgv_sd.setVisibility(View.GONE);
                } else {
                    holder.tv_shaidan.setVisibility(View.GONE);
                    holder.mgv_sd.setVisibility(View.VISIBLE);
                    SdAdapter sdAdapter = new SdAdapter(
                            myeva.get(position).geval_image);
                    holder.mgv_sd.setAdapter(sdAdapter);
                }
            }
            holder.tv_shaidan.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(Myevaluation.this,
                            ShaiDanActivity.class);
//					intent2.putExtra("data", myeva.get(position));
                    intent2.putExtra("geval_id", myeva.get(position).geval_id);
                    startActivity(intent2);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_goodsimg;
            TextView tv_evagoodsname;
            TextView tv_evaluationcontent;
            TextView tv_timeeva, tv_shaidan;
            RatingBar rb_score;
            MyGridView mgv_sd;

            public ViewHolder(View convertView) {
                iv_goodsimg = (ImageView) convertView
                        .findViewById(R.id.iv_goodsimg);
                tv_evagoodsname = (TextView) convertView
                        .findViewById(R.id.tv_evagoodsname);
                tv_evaluationcontent = (TextView) convertView
                        .findViewById(R.id.tv_evaluationcontent);
                tv_timeeva = (TextView) convertView
                        .findViewById(R.id.tv_timeeva);
                tv_shaidan = (TextView) convertView
                        .findViewById(R.id.tv_shaidan);
                rb_score = (RatingBar) convertView.findViewById(R.id.rb_score);
                mgv_sd = (MyGridView) convertView.findViewById(R.id.mgv_sd);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 晒单适配
     *
     * @author Administrator
     */
    private class SdAdapter extends BaseAdapter {
        List<String> geval_image;

        public SdAdapter(List<String> geval_image) {
            this.geval_image = geval_image;
        }

        @Override
        public int getCount() {
            return geval_image.size();
        }

        @Override
        public Object getItem(int position) {
            return geval_image == null ? null : geval_image.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(Myevaluation.this, R.layout.sd_item,
                        null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            bitmapUtils.display(holder.iv_img, geval_image.get(position));
            return convertView;
        }

        class ViewHolder {
            ImageView iv_img;

            public ViewHolder(View convertView) {
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 时间戳转时间
     *
     * @param timestampString 时间戳
     * @param formats         格式(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_qupingjia:
//                Intent intent = new Intent(Myevaluation.this,
//                        BuyerOrderFgActivity.class);
//                intent.putExtra("viewPager", 4);
//                startActivity(intent);
//                finish();
//                break;
//
//        }
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_qupingjia) {
            Intent intent = new Intent(Myevaluation.this,
                    BuyerOrderFgActivity.class);
            intent.putExtra("viewPager", 4);
            startActivity(intent);
            finish();
        }
    }
}
