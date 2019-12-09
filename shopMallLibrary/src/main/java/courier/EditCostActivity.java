package courier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyDialog;
import com.aiteshangcheng.a.R;

import courier.adapter.EditCostAdapter;
import courier.model.EditCostInfo;

/**
 * 设置投递费用
 * Created by Administrator on 2018/1/30.
 */
public class EditCostActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_break;
    private TextView tv_name, tv_add,tv_qx,tv_qs;
    private ListView lv_list;
    private EditText et_code;
    private String id;

    private EditCostAdapter editCostAdapter;
    private EditCostInfo editCostInfo;
    private MyDialog mydialog;
    private View dialoglayout;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case delivery_cost_id://投递费用列表
                    if (msg.obj!=null){
                        editCostInfo= (EditCostInfo) msg.obj;
                        editCostAdapter=new EditCostAdapter(EditCostActivity.this,editCostInfo.datas.list,handler);
                        lv_list.setAdapter(editCostAdapter);
                    }
                    break;
                case delivery_cost_err:
                    Toast.makeText(EditCostActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case 105:
                    if (msg.obj!=null){//修改
                        id= (String) msg.obj;
                        if (mydialog==null){
                            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                            mydialog=new MyDialog(EditCostActivity.this,wm.getDefaultDisplay().getWidth()-100,650,dialoglayout,R.style.loading_dialog);
                        }
                        et_code.setText("");
                        mydialog.show();
                    }
                    break;
                case delivery_edit_id:
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(EditCostActivity.this,str, Toast.LENGTH_SHORT).show();
                        if (str.equals("操作成功")){
                            netRun.DeliveryCost();
                        }
                    }
                    break;
                case delivery_edit_err:
                    Toast.makeText(EditCostActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case delivery_add_cost_id:
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(EditCostActivity.this,str, Toast.LENGTH_SHORT).show();
                        if (str.equals("操作成功")){
                            netRun.DeliveryCost();
                        }
                    }
                    break;
                case delivery_add_cost_err:
                    Toast.makeText(EditCostActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcost);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_add = (TextView) findViewById(R.id.tv_add);
        lv_list = (ListView) findViewById(R.id.lv_list);
        dialoglayout=View.inflate(this,R.layout.dialog_editcost,null);
        tv_qx= (TextView) dialoglayout.findViewById(R.id.tv_qx);
        tv_qs= (TextView) dialoglayout.findViewById(R.id.tv_qs);
        et_code= (EditText) dialoglayout.findViewById(R.id.et_code);
        initView();
    }

    @Override
    protected void initView() {
        tv_name.setText("设置投递费用");
        ll_break.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_qx.setOnClickListener(this);
        tv_qs.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.DeliveryCost();
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.ll_break) {
            finish();
        } else if (vId == R.id.tv_add) {//添加
            Intent intent = new Intent(EditCostActivity.this, CompanyActivity.class);
            intent.putExtra("isedit", true);
            startActivityForResult(intent, 104);
        } else if (vId == R.id.tv_qx) {//取消
            if (mydialog != null) {
                mydialog.dismiss();
            }
        } else if (vId == R.id.tv_qs) {//确定
            if (mydialog != null) {
                mydialog.dismiss();
            }
            String s = et_code.getText().toString();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(EditCostActivity.this, "请输入费用", Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.DeliveryEdit(id, s);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 104:
                if (data != null) {
                    String id = data.getStringExtra("id");
                    netRun.DeliveryAddCost(id,"0");
                }
                break;
        }
    }

    @Override
    public void ReGetData() {

    }
}
