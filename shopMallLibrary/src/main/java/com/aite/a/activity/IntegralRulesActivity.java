package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.IntegralRulesInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 积分规则
 *
 * @author Administrator
 */
public class IntegralRulesActivity extends BaseActivity implements OnClickListener {

    private ImageView iv_bcreturn;
    private TextView tv_bctitle;
    private ListView lv_rules;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private List<String> list = new ArrayList<String>();
    private IntegralRulesInfo integralRulesInfo;
    private MyAdapter myAdapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case integral_rules_id:
                    if (msg.obj != null) {
                        integralRulesInfo = (IntegralRulesInfo) msg.obj;
                        list.add(getString(R.string.evaluation_tips46) + integralRulesInfo.points_reg + getString(R.string.integrall));
                        list.add(getString(R.string.evaluation_tips47) + integralRulesInfo.points_login
                                + getString(R.string.integrall));
                        list.add(getString(R.string.evaluation_tips48) + integralRulesInfo.points_comments
                                + getString(R.string.integrall));
                        list.add(getString(R.string.evaluation_tips49)
                                + integralRulesInfo.points_orderrate + getString(R.string.integrall));
                        list.add(getString(R.string.evaluation_tips50) + integralRulesInfo.points_ordermax
                                + getString(R.string.integrall));
                        if (integralRulesInfo.open_com.equals("1")) {
                            list.add(getString(R.string.evaluation_tips51)
                                    + integralRulesInfo.buy_back_fromer_points
                                    + getString(R.string.integrall));
                            list.add(getString(R.string.evaluation_tips52)
                                    + integralRulesInfo.rec_points + getString(R.string.integrall));
                            list.add(getString(R.string.evaluation_tips53) + integralRulesInfo.sub_points
                                    + getString(R.string.integrall));
                        }
                        myAdapter = new MyAdapter();
                        lv_rules.setAdapter(myAdapter);
                    }
                    break;
                case integral_rules_err:
                    Toast.makeText(IntegralRulesActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integralrules);
        findViewById();
    }


    @Override
    protected void findViewById() {
        iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
        tv_bctitle = (TextView) findViewById(R.id._tv_name);
        lv_rules = (ListView) findViewById(R.id.lv_rules);
        tv_bctitle.setText(getString(R.string.item_mymoney8));

        initView();
    }

    @Override
    protected void initView() {
        iv_bcreturn.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun.IntegralRules();
    }

    @Override
    public void ReGetData() {

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

    /**
     * 明细
     *
     * @author Administrator
     */
    private class MyAdapter extends BaseAdapter {

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
                convertView = View.inflate(IntegralRulesActivity.this,
                        R.layout.item_rules, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_name.setText(list.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView tv_name;

            public ViewHolder(View convertView) {
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(this);
            }
        }
    }

}
