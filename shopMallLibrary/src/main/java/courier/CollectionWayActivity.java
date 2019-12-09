package courier;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 收款方式
 * Created by Administrator on 2018/1/10.
 */
public class CollectionWayActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout ll_break;
    private TextView tv_name,tv_right;
    private EditText et_phone,et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectionway);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break= (LinearLayout) findViewById(R.id.ll_break);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_right= (TextView) findViewById(R.id.tv_right);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_name= (EditText) findViewById(R.id.et_name);
        initView();
    }

    @Override
    protected void initView() {
        ll_break.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_name.setText("收款方式");
        tv_right.setText("修改");

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_break) {
            finish();
        } else if (id == R.id.tv_right) {//修改
        }
    }

    @Override
    public void ReGetData() {

    }
}
