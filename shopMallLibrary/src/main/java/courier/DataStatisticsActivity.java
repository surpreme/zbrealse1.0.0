package courier;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import java.util.Calendar;

import courier.View.MonthChoosePopu;
import courier.model.DataStatisticsInfo;

/**
 * 数据统计
 * Created by Administrator on 2018/1/9.
 */
public class DataStatisticsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_time, tv_enter, tv_out, tv_break, tv_send, tv_date, tv_num1, tv_num2, tv_num3, tv_num4;
    private LinearLayout ll_break, ll_date;
    private NetRun netRun;
    private DataStatisticsInfo dataStatisticsInfo;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case express_storage_num_id://数据统计
                    if (msg.obj != null) {
                        dataStatisticsInfo = (DataStatisticsInfo) msg.obj;
                        if (dataStatisticsInfo != null && dataStatisticsInfo.jinri != null) {
                            tv_enter.setText(dataStatisticsInfo.jinri.end_num == null ? "0" : dataStatisticsInfo.jinri.end_num);
                            tv_out.setText(dataStatisticsInfo.jinri.out_num == null ? "0" : dataStatisticsInfo.jinri.out_num);
                            tv_break.setText(dataStatisticsInfo.jinri.tui_num == null ? "0" : dataStatisticsInfo.jinri.tui_num);
                        } else {
                            tv_enter.setText("0");
                            tv_out.setText("0");
                            tv_break.setText("0");
                        }
                        if (dataStatisticsInfo != null && dataStatisticsInfo.yue != null) {
                            tv_num1.setText(dataStatisticsInfo.yue.end_num == null ? "0单" : dataStatisticsInfo.yue.end_num+"单");
                            tv_num2.setText(dataStatisticsInfo.yue.out_num == null ? "0单" : dataStatisticsInfo.yue.out_num+"单");
                            tv_num3.setText(dataStatisticsInfo.yue.tui_num == null ? "0单" : dataStatisticsInfo.yue.tui_num+"单");
                        } else {
                            tv_num1.setText("0单");
                            tv_num2.setText("0单");
                            tv_num3.setText("0单");
                        }
                    }
                    break;
                case express_storage_num_err:
                    Toast.makeText(DataStatisticsActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datastatistics);
        findViewById();
    }

    @Override
    protected void findViewById() {
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_enter = (TextView) findViewById(R.id.tv_enter);
        tv_out = (TextView) findViewById(R.id.tv_out);
        tv_break = (TextView) findViewById(R.id.tv_break);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);
        tv_num2 = (TextView) findViewById(R.id.tv_num2);
        tv_num3 = (TextView) findViewById(R.id.tv_num3);
        tv_num4 = (TextView) findViewById(R.id.tv_num4);
        ll_date = (LinearLayout) findViewById(R.id.ll_date);
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        initView();
    }

    @Override
    protected void initView() {
        ll_break.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        ll_date.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        tv_time.setText(year + "-" + month + "-" + day + "（今天）");
        netRun.ExpressStorageNum(year + "", month + "");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_break) {
            finish();
        } else if (id == R.id.ll_date) {
            showpopu();
        }
    }


    /**
     * 弹出时间
     */
    private void showpopu() {
        MonthChoosePopu monthChoosePopu = new MonthChoosePopu(DataStatisticsActivity.this);
        monthChoosePopu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));
        monthChoosePopu.setmenu(new MonthChoosePopu.menu() {
            @Override
            public void onItemClick(String year, String month) {
                netRun.ExpressStorageNum(year, month);
                tv_date.setText(year + "-" + month);
            }
        });
        monthChoosePopu.showAsDropDown(tv_date, 0, 0);
    }

    @Override
    public void ReGetData() {

    }
}
