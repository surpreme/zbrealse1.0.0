package courier;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 配送件
 * Created by Administrator on 2018/1/9.
 */
public class CourierDistributionActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_name, tv_menu1, tv_menu2;
    private View v_slide;
    private LinearLayout ll_break;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courierdistribution);
        findViewById();
    }

    @Override
    protected void findViewById() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        v_slide = findViewById(R.id.v_slide);
        initView();
    }

    @Override
    protected void initView() {
        tv_name.setText("配送件");
        ll_break.setVisibility(View.VISIBLE);
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        ll_break.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_menu1) {//待配送
        } else if (id == R.id.tv_menu2) {//已配送
        } else if (id == R.id.ll_break) {
            finish();
        }
    }

    @Override
    public void ReGetData() {

    }
}
