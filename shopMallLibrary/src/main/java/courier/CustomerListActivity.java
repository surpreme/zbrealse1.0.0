package courier;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aiteshangcheng.a.R;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import courier.View.ZmTest;
import courier.adapter.CustomerListAdapter;
import courier.model.CustomerListInfo;

/**
 * 客户列表
 * Created by Administrator on 2018/1/9.
 */
public class CustomerListActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout ll_break;
    private EditText et_input;
    private ImageView iv_voice,iv_volume;
    private TextView tv_search,tv_menu1,tv_menu2,tv_menu3,tv_zm;
    private ListView lv_list;
    private LinearLayout ll_null;
    private View v_slide1,v_slide2,v_slide3;
    private ZmTest zt;
    private String type="";
    private int type2=0;
    private CustomerListAdapter customerListAdapter;
    private List<CustomerListInfo>customerListInfo;
    private String search="";
    private NetRun netRun;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case delivery_customer_list_id://客户列表
                    if (msg.obj!=null){
                        customerListInfo= (List<CustomerListInfo>) msg.obj;
                        if (customerListInfo==null||customerListInfo.size()==0){
                            ll_null.setVisibility(View.VISIBLE);
                        }else {
                            ll_null.setVisibility(View.GONE);
                            if (customerListAdapter==null){
                                customerListAdapter=new CustomerListAdapter(CustomerListActivity.this,customerListInfo,type2,handler);
                                lv_list.setAdapter(customerListAdapter);
                            }else{
                                customerListAdapter.refreshData(customerListInfo,type2);
                            }
                        }
                    }
                    break;
                case delivery_customer_list_err:
                    Toast.makeText(CustomerListActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case 101://加入VIP
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        netRun.DeliveryCustomerStatevip(str);
                    }
                    break;
                case 102://选择
                    if (msg.obj!=null){
                        boolean isrk = getIntent().getBooleanExtra("isrk", false);
                        if (isrk){
                            CustomerListInfo.info info = (CustomerListInfo.info) msg.obj;
                            Intent intent = new Intent(CustomerListActivity.this, CourierHomeTabActivity.class);
                            intent.putExtra("id",info.id);
                            intent.putExtra("phone",info.customer_mobile);
                            intent.putExtra("name",info.customer_name);
                            setResult(998,intent);
                            finish();
                        }
                    }
                    break;
                case delivery_customer_statevip_id://加入VIP
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(CustomerListActivity.this, str, Toast.LENGTH_SHORT).show();
                        if (str.equals("操作成功")){
                            netRun.DeliveryCustomerList(type,search);
                        }
                    }
                    break;
                case delivery_customer_statevip_err:
                    Toast.makeText(CustomerListActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break= (LinearLayout) findViewById(R.id.ll_break);
        et_input= (EditText) findViewById(R.id.et_input);
        iv_voice= (ImageView) findViewById(R.id.iv_voice);
        iv_volume= (ImageView) findViewById(R.id.iv_volume);
        tv_search= (TextView) findViewById(R.id.tv_search);
        tv_menu1= (TextView) findViewById(R.id.tv_menu1);
        tv_menu2= (TextView) findViewById(R.id.tv_menu2);
        tv_menu3= (TextView) findViewById(R.id.tv_menu3);
        ll_null= (LinearLayout) findViewById(R.id.ll_null);
        v_slide1=findViewById(R.id.v_slide1);
        v_slide2=findViewById(R.id.v_slide2);
        v_slide3=findViewById(R.id.v_slide3);
        tv_zm= (TextView) findViewById(R.id.tv_zm);
        lv_list= (ListView) findViewById(R.id.lv_list);
        zt= (ZmTest) findViewById(R.id.zt);
        initView();
    }

    @Override
    protected void initView() {
        netRun=new NetRun(this,handler);
        ll_break.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        tv_menu3.setOnClickListener(this);
        iv_voice.setOnTouchListener(l);
        xunfei();
        zt.setmenu(new ZmTest.menu() {
            @Override
            public void onItemClick(String id) {
                choosezm(id);
            }
        });
        initData();
    }

    @Override
    protected void initData() {
        netRun.DeliveryCustomerList(type,search);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_search) {// 搜索
            search = et_input.getText().toString();
            if (TextUtils.isEmpty(search)) {
                Toast.makeText(CustomerListActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.DeliveryCustomerList(type, search);
        } else if (id == R.id.ll_break) {
            finish();
        } else if (id == R.id.tv_menu1) {//全部
            type2 = 0;
            type = "";
            setchoose(v_slide1);
        } else if (id == R.id.tv_menu2) {//VIP
            type2 = 1;
            type = "is_vip";
            setchoose(v_slide2);
        } else if (id == R.id.tv_menu3) {//会员
            type2 = 0;
            type = "is_member";
            setchoose(v_slide3);
        }
    }

    private View.OnTouchListener l = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isrepeat=false;
                    mIat.startListening(mRecoListener);
                    break;
                case MotionEvent.ACTION_UP:
                    mIat.stopListening();
                    iv_volume.setImageResource(R.drawable.volume1);
                    iv_volume.setVisibility(View.GONE);
                    break;
            }
            return false;
        }
    };

    /**
     * 选中字母
     */
    private void choosezm(String id){
        if (id.length() == 0) {
            tv_zm.setVisibility(View.GONE);
        } else {
            tv_zm.setVisibility(View.VISIBLE);
            tv_zm.setText(id);
            if (customerListInfo!=null||customerListInfo.size()!=0){
                for (int i=0;i<customerListInfo.size();i++){
                    if (customerListInfo.get(i).type_shouzimu.equals(id)){
                        lv_list.setSelection(i);
                    }
                }
            }
        }
    }

    /**
     * 修改选中
     * @param v
     */
    private void setchoose(View v){
        v_slide1.setVisibility(View.INVISIBLE);
        v_slide2.setVisibility(View.INVISIBLE);
        v_slide3.setVisibility(View.INVISIBLE);
        v.setVisibility(View.VISIBLE);
        netRun.DeliveryCustomerList(type,search);
    }

    private SpeechRecognizer mIat;
    boolean isrepeat=false;
    private void xunfei() {
        // 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(CustomerListActivity.this, null);
        // 2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        // 3.开始听写 mIat.startListening(mRecoListener);

    }


    // 听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        // 听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
        // 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        // 关于解析Json的代码可参见MscDemo中JsonParser类；
        // isLast等于true时会话结束。
        @Override
        public void onResult(RecognizerResult arg0, boolean arg1) {
            Log.i("-------------------","语音  "+arg0.getResultString());
            // System.out.println("--------------   " + arg0.getResultString());
            try {
                JSONObject jsonObject = new JSONObject(arg0.getResultString());
                JSONArray jsonArray = jsonObject.getJSONArray("ws");
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    JSONArray jsonArray2 = jsonObject2.getJSONArray("cw");
                    for (int j = 0; j < jsonArray2.length(); j++) {
                        JSONObject jsonObject3 = jsonArray2.getJSONObject(j);
                        String text = jsonObject3.getString("w");

                        sb.append(text);
                    }
                }
                if (!isrepeat) {
                    et_input.append(sb.toString());
                    isrepeat=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // 会话发生错误回调接口
        @Override
        public void onError(SpeechError arg0) {
            String plainDescription = arg0.getPlainDescription(true); // 获取错误码描述}
            CommonTools.showShortToast(CustomerListActivity.this, plainDescription);
        }

        // 开始录音
        @Override
        public void onBeginOfSpeech() {
            iv_volume.setVisibility(View.VISIBLE);
        }

        // 音量值0~30
        @Override
        public void onVolumeChanged(int arg0, byte[] arg1) {
            if (arg0 < 6) {
                iv_volume.setImageResource(R.drawable.volume1);
            } else if (arg0 < 11) {
                iv_volume.setImageResource(R.drawable.volume2);
            } else if (arg0 < 16) {
                iv_volume.setImageResource(R.drawable.volume3);
            } else if (arg0 < 21) {
                iv_volume.setImageResource(R.drawable.volume4);
            } else if (arg0 < 26) {
                iv_volume.setImageResource(R.drawable.volume5);
            } else {
                iv_volume.setImageResource(R.drawable.volume6);
            }
        }

        // 结束录音
        @Override
        public void onEndOfSpeech() {
            iv_volume.setImageResource(R.drawable.volume1);
            iv_volume.setVisibility(View.GONE);
        }

        // 扩展用接口
        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

        }

    };

    @Override
    public void ReGetData() {

    }
}
