package courier;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 收入明细
 * Created by Administrator on 2018/1/10.
 */
public class IncomeDatailsActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_break,ll_null;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomedatails);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break= (LinearLayout) findViewById(R.id.ll_break);
        ll_null= (LinearLayout) findViewById(R.id.ll_null);
        tv_name= (TextView) findViewById(R.id.tv_name);
        initView();
    }

    @Override
    protected void initView() {
        tv_name.setText("收入明细");
        ll_break.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_break) {
            finish();
        }
    }

    @Override
    public void ReGetData() {

    }
}
