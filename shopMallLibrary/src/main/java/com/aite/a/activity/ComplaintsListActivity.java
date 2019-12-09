package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.ComplaintslistInfo;
import com.aite.a.model.ComplaintslistInfo.datas.list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.Dialog;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 投诉
 *
 * @author Administrator
 */
public class ComplaintsListActivity extends BaseActivity implements
        OnClickListener {

    private TextView _tv_name, tv_nodata;
    //    private View tv_xz, tv_ongoing, tv_complete ;
    //tv_tsgoods, tv_tstime, tv_tsstate,tv_tsoperation,
    private ListView lv_tslist;
    private ImageView _iv_back;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    public ComplaintslistInfo complaintslistInfo;
    private Madapter madapter;
    private LinearLayout ll_state;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case complaints_list_id:
                    if (msg.obj != null) {
                        complaintslistInfo = (ComplaintslistInfo) msg.obj;
                        if (complaintslistInfo.datas.list == null || complaintslistInfo.datas.list.size() == 0) {
                            tv_nodata.setVisibility(View.VISIBLE);
                        } else {
                            tv_nodata.setVisibility(View.GONE);
                        }
                        madapter = new Madapter(complaintslistInfo.datas.list);
                        lv_tslist.setAdapter(madapter);
                    }
                    break;
                case complaints_list_err:
                    tv_nodata.setVisibility(View.VISIBLE);
                    Toast.makeText(ComplaintsListActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case cancel_complaint_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(ComplaintsListActivity.this, str,
                                Toast.LENGTH_SHORT).show();
                        netRun.ComplaintsList("1", "");
                    }
                    break;
                case cancel_complaint_err:
                    Toast.makeText(ComplaintsListActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };
    private TextView stateAllTv;
    private TextView stateDoingTv;
    private TextView stateComplecteTv;
    private View stateAllLine;
    private View stateDoingLine;
    private View stateComplecteLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaintslist);
        findViewById();
    }

    @Override
    protected void findViewById() {
//        tv_state = (TextView) findViewById(R.id.tv_state);
//        tv_search = (TextView) findViewById(R.id.tv_search);
//        tv_tsgoods = (TextView) findViewById(R.id.tv_tsgoods);
//        tv_tstime = (TextView) findViewById(R.id.tv_tstime);
//        tv_tsstate = (TextView) findViewById(R.id.tv_tsstate);
//        tv_tsoperation = (TextView) findViewById(R.id.tv_tsoperation);
        lv_tslist = (ListView) findViewById(R.id.lv_tslist);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
//        ll_state = (LinearLayout) findViewById(R.id.ll_state);
        // tv_xz,tv_ongoing,tv_complete
//        tv_xz = findViewById(R.id.tv_xz);
//        tv_ongoing =  findViewById(R.id.tv_ongoing);
//        tv_complete =  findViewById(R.id.tv_complete);
        findViewById(R.id.state_all_ll).setOnClickListener(this);
        findViewById(R.id.state_doing_ll).setOnClickListener(this);
        findViewById(R.id.state_complecte_ll).setOnClickListener(this);
        stateAllTv = findViewById(R.id.state_all_tv);
        stateDoingTv = findViewById(R.id.state_doing_tv);
        stateComplecteTv = findViewById(R.id.state_complecte_tv);
        stateAllLine = findViewById(R.id.state_all_line);
        stateDoingLine = findViewById(R.id.state_doing_line);
        stateComplecteLine = findViewById(R.id.state_complecte_line);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.tradecomplaint));
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        _iv_back.setOnClickListener(this);
//        tv_state.setOnClickListener(this);
//        tv_xz.setOnClickListener(this);
//        tv_ongoing.setOnClickListener(this);
//        tv_complete.setOnClickListener(this);
//        tv_search.setOnClickListener(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun.ComplaintsList("1", "");
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.state_all_ll) {
            netRun.ComplaintsList("1", "");
            doSelectStyle(R.id.state_all_ll);
        } else if (v.getId() == R.id.state_doing_ll) {
            netRun.ComplaintsList("1", "0");
            doSelectStyle(R.id.state_doing_ll);
        } else if (v.getId() == R.id.state_complecte_ll) {
            netRun.ComplaintsList("1", "1");
            doSelectStyle(R.id.state_complecte_ll);
        }


//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//          /*  case R.id.tv_state:
//                if (ll_state.getVisibility() == View.VISIBLE) {
//                    ll_state.setVisibility(View.GONE);
//                } else {
//                    ll_state.setVisibility(View.VISIBLE);
//                }
//                break;*/
//          /*  case R.id.tv_xz:
//                ll_state.setVisibility(View.GONE);
//                tv_state.setText(getString(R.string.complaint_prompt30));
//                break;
//            case R.id.tv_ongoing:
//                ll_state.setVisibility(View.GONE);
//                tv_state.setText(getString(R.string.complaint_prompt31));
//                break;
//            case R.id.tv_complete:
//                ll_state.setVisibility(View.GONE);
//                tv_state.setText(getString(R.string.completed));
//                break;*/
//            case R.id.state_all_ll:   //全部
//                netRun.ComplaintsList("1", "");
//                doSelectStyle(R.id.state_all_ll);
//                break;
//            case R.id.state_doing_ll:       //正在进行中
//                netRun.ComplaintsList("1", "0");
//                doSelectStyle(R.id.state_doing_ll);
//                break;
//            case R.id.state_complecte_ll:          //已完成
//                netRun.ComplaintsList("1", "1");
//                doSelectStyle(R.id.state_complecte_ll);
//                break;
//           /* case R.id.tv_search:
//                String string = tv_state.getText().toString();
//                if (string.equals("选择状态")) {
//                    netRun.ComplaintsList("1", "");
//                } else if (string.equals("进行中")) {
//                    netRun.ComplaintsList("1", "0");
//                }
//                if (string.equals("已完成")) {
//                    netRun.ComplaintsList("1", "1");
//                }
//                break;*/
//        }
    }

    private void doSelectStyle(int viewId) {
        stateAllTv.setSelected(viewId == R.id.state_all_ll);
        stateDoingTv.setSelected(viewId == R.id.state_doing_ll);
        stateComplecteTv.setSelected(viewId == R.id.state_complecte_ll);
        stateAllLine.setVisibility(viewId == R.id.state_all_ll ? View.VISIBLE : View.GONE);
        stateDoingLine.setVisibility(viewId == R.id.state_doing_ll ? View.VISIBLE : View.GONE);
        stateComplecteLine.setVisibility(viewId == R.id.state_complecte_ll ? View.VISIBLE : View.GONE);
    }

    private class Madapter extends BaseAdapter {
        List<list> list;
        public Madapter(List<list> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list == null ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ComplaintsListActivity.this,
                        R.layout.complaintslist_item, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            final ComplaintslistInfo.datas.list list = this.list.get(position);
            bitmapUtils.display(holder.iv_tx, complaintslistInfo.datas.goods_list.get(position).goods_image);
            holder.goodName.setText(complaintslistInfo.datas.goods_list.get(position).goods_name);
            holder.tv_storename.setText(getString(R.string.distribution_center31)
                    + list.accused_name);
            holder.tv_time.setText("投诉时间:" + TimeStamp2Date(
                    list.complain_datetime, "yyyy-MM-dd HH:mm"));
            holder.titleTv.setText("投诉主题:" + list.complain_subject_content);
//            String complain_state = list.complain_state;
//            switch (complain_state) {
//                case "10":
//                    complain_state = getString(R.string.complaint_prompt2);
//                    break;
//                case "20":
//                    complain_state = getString(R.string.complaint_prompt32);
//                    break;
//                case "30":
//                    complain_state = getString(R.string.complaint_prompt33);
//                    break;
//                case "40":
//                    complain_state = getString(R.string.complaint_prompt34);
//                    break;
//                case "99":
//                    complain_state = getString(R.string.complaint_prompt35);
//                    break;
//            }
            holder.tv_state.setText("投诉状态:" + list.complain_state_desc);
            holder.tv_ck.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(ComplaintsListActivity.this,
                            ComplaintDetails.class);
                    intent2.putExtra("complain_id",
                            Madapter.this.list.get(position).complain_id);
                    intent2.putExtra("list", list);
                    startActivity(intent2);
                }
            });
            holder.tv_qx.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Dialog.showSnackBarWithAction("确定要取消吗?", lv_tslist, true, "是的", new Dialog.SnackbarActionListener() {
                        @Override
                        public void doAction(Snackbar snackbar) {

                            netRun.CancelComplaint(Madapter.this.list.get(position).complain_id);
                        }
                    });
                }
            });
            return convertView;
        }

        class ViewHolder {
            //            private final TextView goodNameTv;
            private final TextView titleTv;
            TextView goodName;
            ImageView iv_tx;
            TextView tv_storename, tv_time, tv_state, tv_ck, tv_qx;
            //            LinearLayout ll_item;

            public ViewHolder(View convertView) {
                iv_tx = (ImageView) convertView.findViewById(R.id.iv_tx);
                tv_storename = (TextView) convertView
                        .findViewById(R.id.tv_storename);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                tv_state = (TextView) convertView.findViewById(R.id.tv_state);
                tv_ck = (TextView) convertView.findViewById(R.id.tv_ck);
                tv_qx = (TextView) convertView.findViewById(R.id.tv_qx);
//                goodNameTv = convertView.findViewById(R.id.tv_complaint_goodname);
                titleTv = convertView.findViewById(R.id.tv_complian_title);
                goodName = convertView.findViewById(R.id.complaint_goodname_tv);
//                ll_item = convertView.findViewById(R.id.ll_item);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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
}
