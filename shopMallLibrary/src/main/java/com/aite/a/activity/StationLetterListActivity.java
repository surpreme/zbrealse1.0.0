package com.aite.a.activity;

import com.aite.a.base.Mark;
import com.aite.a.model.StationLetterInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CustomToolBar;
import com.aite.a.view.CustomToolBar.onLeftBtnClickListener;
import com.aite.a.view.CustomToolBar.onRightBtnClickListener;
import com.aiteshangcheng.a.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

/**
 * 站内信
 *
 * @author Administrator
 */
public class StationLetterListActivity extends Activity implements Mark {

    private Context mContext;
    private CustomToolBar mCustomToolBar;
    private PopupWindow mPopupWindow;
    private View view;
    private NetRun mNetRun;
    private ListView lv_station_letter;
    private StationLetterInfo letterInfo;
    private TextView tv_nodata;
    public View previous_view = null;// 上一个点击的选项
    public boolean isChicked = false;// 当前点击的item
    private String type = "";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case station_letter_id:
                    // 系统消息列表
                    if (msg.obj != null) {
                        letterInfo = (StationLetterInfo) msg.obj;
                        if (letterInfo.message_array == null
                                || letterInfo.message_array.size() == 0) {
                            tv_nodata.setVisibility(View.VISIBLE);
                        } else {
                            tv_nodata.setVisibility(View.GONE);
                        }
                        lv_station_letter.setAdapter(new listAdapter());
                    } else {
                        tv_nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext,
                                getResources().getString(R.string.systembusy),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case station_letter_err:
                    Toast.makeText(mContext,
                            getResources().getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case ordinarymessage_list_id:
                    // 普通消息列表
                    if (msg.obj != null) {
                        letterInfo = (StationLetterInfo) msg.obj;
                        if (letterInfo.message_array == null
                                || letterInfo.message_array.size() == 0) {
                            tv_nodata.setVisibility(View.VISIBLE);
                        } else {
                            tv_nodata.setVisibility(View.GONE);
                        }
                        lv_station_letter.setAdapter(new listAdapter());
                    } else {
                        tv_nodata.setVisibility(View.VISIBLE);
                    }
                    break;
                case ordinarymessage_list_err:
                    Toast.makeText(mContext,
                            getResources().getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case direct_messages_id:
                    // 站内私信列表
                    if (msg.obj != null) {
                        letterInfo = (StationLetterInfo) msg.obj;
                        if (letterInfo.message_array == null
                                || letterInfo.message_array.size() == 0) {
                            tv_nodata.setVisibility(View.VISIBLE);
                        } else {
                            tv_nodata.setVisibility(View.GONE);
                        }
                        lv_station_letter.setAdapter(new listAdapter());
                    } else {
                        tv_nodata.setVisibility(View.VISIBLE);
                    }
                    break;
                case direct_messages_err:
                    Toast.makeText(mContext,
                            getResources().getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;

            }
        }

        ;
    };

    @Override
    protected void onResume() {
        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
            if (type != null && type.equals("designer")) {
                /**
                 * 设置为横屏
                 */
                if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            super.onResume();
            String listMessageType = getIntent().getStringExtra("msgType");
            if (listMessageType == null) {
                mNetRun.stationLetterList(null);
                return;
            }

            if (listMessageType.equals("app_mag"))//站内消息
                mNetRun.DirectMessages(null);
            if (listMessageType.equals("setting_msg"))//系统消息
                mNetRun.stationLetterList(null);
            if (listMessageType.equals("base_msg"))//普通消息
                mNetRun.OrdinaryMessageList(null);

        }
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_station_letter_list);
        init();
        setListener();
        // lv_station_letter.setAdapter(adapter);
    }

    private void init() {
        mContext = StationLetterListActivity.this;
        mCustomToolBar = (CustomToolBar) findViewById(R.id.custom_toolbar);
        lv_station_letter = (ListView) findViewById(R.id.lv_station_letter);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        mNetRun = new NetRun(mContext, handler);
        if (getIntent().getStringExtra("msgType") == null || TextUtils.isEmpty(getIntent().getStringExtra("msgType")))
            mNetRun.stationLetterList(null);// 传null返回所有站内信
    }

    private void setListener() {
        lv_station_letter
                .setOnItemLongClickListener(new OnItemLongClickListener() {// 长按事件

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {
                        return false;

                    }
                });
        mCustomToolBar.setOnLeftBtnClickListener(new onLeftBtnClickListener() {

            @Override
            public void onLeftBtnClick() {
                // 返回
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                finish();
            }
        });
        mCustomToolBar
                .setOnRightBtnClickListener(new onRightBtnClickListener() {

                    @Override
                    public void onRightBtnClick() {
                        showPopWindow();
                    }
                });
    }

    protected void showPopWindow() {
        if (mPopupWindow == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dialog_pop_up, null);
            mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }
        Button send = (Button) view.findViewById(R.id.btn_to_send);
        Button received = (Button) view.findViewById(R.id.btn_received_letter);
        Button btn_send = (Button) view.findViewById(R.id.btn_send_letter);
        Button btn_direct_messages = (Button) view
                .findViewById(R.id.btn_direct_messages);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Activity);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAsDropDown(mCustomToolBar);
        send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 我要发送
                mPopupWindow.dismiss();
                Intent intent = new Intent(mContext, SendLetterActivity.class);
                if (type != null && !type.equals("")) {
                    intent.putExtra("type", type);
                }
                startActivity(intent);
            }
        });
        received.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 收到
                mPopupWindow.dismiss();
                mNetRun.OrdinaryMessageList(null);
            }
        });
        btn_send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 系统消息
                mPopupWindow.dismiss();
                mNetRun.stationLetterList(null);
            }
        });
        btn_direct_messages.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 私信
                mPopupWindow.dismiss();
                mNetRun.DirectMessages(null);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public class listAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return letterInfo.message_array == null ? 0
                    : letterInfo.message_array.size();
        }

        @Override
        public Object getItem(int position) {
            return letterInfo.message_array.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final Holder hodler;
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext)
                        .getLayoutInflater();
                convertView = inflater.inflate(R.layout.station_letter_item,
                        null);
                hodler = new Holder();

                hodler.tv_title = (TextView) convertView
                        .findViewById(R.id.tv_title);
                hodler.tv_date = (TextView) convertView
                        .findViewById(R.id.tv_date);
                hodler.iv_arrow = (ImageView) convertView
                        .findViewById(R.id.iv_arrow);
                hodler.ll_hide_content = (LinearLayout) convertView
                        .findViewById(R.id.ll_hide_content);
                hodler.tv_content = (TextView) convertView
                        .findViewById(R.id.tv_content);

                if (letterInfo.message_array.get(position).message_title != null) {
                    hodler.tv_title.setText(letterInfo.message_array
                            .get(position).message_title);
                } else {
                    hodler.tv_title.setText(letterInfo.message_array
                            .get(position).from_member_name);
                }
                hodler.tv_date.setText(TimeStamp2Date(
                        letterInfo.message_array.get(position).message_time,
                        "yyyy-MM-dd"));
                hodler.tv_content.setText(letterInfo.message_array
                        .get(position).message_body);

                final View view = convertView;
                hodler.iv_arrow.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (previous_view != null) {
                            if (previous_view != view) {// 当前点击项与上一次不同
                                ImageView arrow = (ImageView) previous_view
                                        .findViewById(R.id.iv_arrow);
                                LinearLayout hide_content = (LinearLayout) previous_view
                                        .findViewById(R.id.ll_hide_content);
                                arrow.setImageResource(R.drawable.icon_down_arrow);
                                hide_content.setVisibility(View.GONE);
                                isChicked = true;
                            } else {// 当前点击项与上一次相同
                                isChicked = !isChicked;
                            }
                        } else {
                            isChicked = true;
                        }
                        if (isChicked) {
                            hodler.iv_arrow
                                    .setImageResource(R.drawable.icon_up_arrow);
                            hodler.ll_hide_content.setVisibility(View.VISIBLE);
                        } else {
                            hodler.iv_arrow
                                    .setImageResource(R.drawable.icon_down_arrow);
                            hodler.ll_hide_content.setVisibility(View.GONE);
                        }
                        previous_view = view;
                    }
                });
                convertView.setTag(hodler);

            } else {
                hodler = (Holder) convertView.getTag();
            }

            return convertView;
        }
    }

    public static class Holder {
        public TextView tv_title;
        public TextView tv_date;
        public ImageView iv_arrow;
        public LinearLayout ll_hide_content;
        public TextView tv_content;
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
