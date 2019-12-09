package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;






import java.util.List;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.FreightModeInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

/**
 * 运费模板
 * Created by Administrator on 2017/5/10.
 */
public class FreightModeActivity extends BaseActivity implements View.OnClickListener {
    private TextView _tv_name;
    private ImageView _iv_back;
    private ListView lv_model;
    private NetRun netRun;
    private FreightModeInfo freightModeInfo;
    private Madapter madapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case freight_mode_id:
                    if (msg.obj != null) {
                        freightModeInfo = (FreightModeInfo) msg.obj;
                        if (freightModeInfo.list == null || freightModeInfo.list.size() == 0) {
                            Toast.makeText(FreightModeActivity.this, getString(R.string.release_goods137), Toast.LENGTH_SHORT).show();
                        } else {
                            madapter = new Madapter(freightModeInfo.list);
                            lv_model.setAdapter(madapter);
                        }
                    }
                    break;
                case freight_mode_err:
                    Toast.makeText(FreightModeActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freightmode);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        lv_model = (ListView) findViewById(R.id.lv_model);
        _iv_back.setOnClickListener(this);
        _tv_name.setText(getString(R.string.release_goods136));
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.FreightMode();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//        }
        if(v.getId()==R.id._iv_back){
            finish();
        }
    }

    private class Madapter extends BaseAdapter {
        List<FreightModeInfo.list> list;

        public Madapter(List<FreightModeInfo.list> list) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(FreightModeActivity.this, R.layout.item_freightmode, null);
                new ViewHodler(convertView);
            }
            ViewHodler holder = (ViewHodler) convertView.getTag();
            final FreightModeInfo.list list = this.list.get(position);
            holder.tv_name.setText(list.title);
            holder.tv_time.setText(TimeStamp2Date(list.update_time, "yyyy-MM-dd HH:mm"));
            holder.tv_use.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //确定
                    Intent intent = new Intent(FreightModeActivity.this, ReleaseGoodsActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("id",list.id);
                    bundle.putString("name",list.title);
                    intent.putExtras(bundle);
                    setResult(10010,intent);
                    finish();
                }
            });
            return convertView;
        }

        class ViewHodler {
            TextView tv_use, tv_name, tv_time;

            public ViewHodler(View convertView) {
                tv_use = (TextView) convertView.findViewById(R.id.tv_use);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
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
	public void ReGetData() {
		
	}
}
