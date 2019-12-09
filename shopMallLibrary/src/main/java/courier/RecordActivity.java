package courier;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyDialog;
import com.aite.a.view.MyKeyboard;
import com.aiteshangcheng.a.R;

import java.util.List;

import courier.adapter.CourierAdapter;
import courier.adapter.RecordAdapter;
import courier.model.DeliveryTallyInfo;
import courier.model.RecordInfo;

/**
 * 记账
 * Created by Administrator on 2018/1/9.
 */
public class RecordActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_break;
    private TextView tv_name, tv_time, tv_add, tv_cancel, tv_determine;
    private ImageView iv_search;
    private ListView lv_courier;
    private ListView lv_kd;
    private NetRun netRun;
    private List<RecordInfo> recordInfo;
    private CourierAdapter courierAdapter;
    private MyDialog myDialog;
    private String pagesize = "100",setid="",settype="1";
    private int curpage = 1;
    private View inflate;
    private DeliveryTallyInfo deliveryTallyInfo;
    private RecordAdapter recordAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case delivery_addlist_id://可添加快递公司
                    if (msg.obj != null) {
                        recordInfo = (List<RecordInfo>) msg.obj;
                        myDialog = new MyDialog(RecordActivity.this, 750, 150,
                                inflate, R.style.loading_dialog);
                        courierAdapter = new CourierAdapter(RecordActivity.this, recordInfo);
                        lv_courier.setAdapter(courierAdapter);
                    }
                    break;
                case delivery_addlist_err:
                    Toast.makeText(RecordActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case delivery_tally_id://记账列表
                    if (msg.obj != null) {
                        deliveryTallyInfo = (DeliveryTallyInfo) msg.obj;
                        recordAdapter = new RecordAdapter(RecordActivity.this, deliveryTallyInfo.datas.list,handler);
                        lv_kd.setAdapter(recordAdapter);
                    }
                    break;
                case delivery_tally_err:
                    Toast.makeText(RecordActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case delivery_addcourier_id://添加快递公司
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(RecordActivity.this, str, Toast.LENGTH_SHORT).show();
                        if (str.equals("操作成功")){
                            netRun.DeliveryTally(pagesize, curpage + "");
                            netRun.DeliveryAddlist();
                        }
                    }
                    break;
                case delivery_addcourier_err:
                    Toast.makeText(RecordActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    if (msg.obj!=null){
                        setid= (String) msg.obj;
                        settype="1";
                        showpopu();
                    }
                    break;
                case 102:
                    if (msg.obj!=null){
                        setid= (String) msg.obj;
                        settype="2";
                        showpopu();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_add = (TextView) findViewById(R.id.tv_add);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        lv_kd = (ListView) findViewById(R.id.lv_kd);
        inflate = View.inflate(this, R.layout.dialog_expressagelsit, null);
        lv_courier = (ListView) inflate.findViewById(R.id.lv_courier);
        tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        tv_determine = (TextView) inflate.findViewById(R.id.tv_determine);
        initView();
    }

    @Override
    protected void initView() {
        tv_name.setText("记账");
        ll_break.setVisibility(View.VISIBLE);
        iv_search.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_determine.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
//        netRun.DeliveryCourier();
        netRun.DeliveryTally(pagesize, curpage + "");
        netRun.DeliveryAddlist();
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.ll_break) {
            finish();
        } else if (vId == R.id.tv_add) {//添加快递公司
            if (recordInfo != null) {
                myDialog.show();
            }
        } else if (vId == R.id.tv_cancel) {//取消
            if (recordInfo != null) {
                myDialog.dismiss();
            }
        } else if (vId == R.id.tv_determine) {//确定
            String id = courierAdapter.getChoose();
            if (id != null) {
                netRun.DeliveryAddcourier(id);
            }
            if (recordInfo != null) {
                myDialog.dismiss();
            }
        }
    }

    /**
     * 显示数字键盘
     */
    private void showpopu() {
        MyKeyboard myKeyboard = new MyKeyboard(RecordActivity.this);
        myKeyboard.sethuidiao(new MyKeyboard.huidiao() {
            @Override
            public void onItemClick(String num) {
                netRun.DeliveryTallyUpdate(setid,num,settype);
                recordAdapter.setNum(setid,num,settype);
            }
        });
        myKeyboard.showAtLocation(tv_add, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void ReGetData() {

    }

}
