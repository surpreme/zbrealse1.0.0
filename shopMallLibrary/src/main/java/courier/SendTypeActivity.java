package courier;

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
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import courier.adapter.SendTypeAdapter;
import courier.model.SendTypeInfo;

/**
 * 快递发送状态
 * Created by Administrator on 2018/1/11.
 */
public class SendTypeActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_break,ll_null;
    private EditText et_input;
    private ImageView iv_voice,iv_volume;
    private TextView tv_search,tv_menu1,tv_menu2,tv_menu3;
    private View v_slide1,v_slide2,v_slide3;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_kd;
    private String pagesize="20",state="0",inphone="";
    private int curpage=1,refreshtype = 0;
    private MyListener myListenr;
    private SendTypeAdapter sendTypeAdapter;
    private SendTypeInfo sendTypeInfo;
    private NetRun netRun;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case sms_log_list_id://短信列表
                    if (msg.obj!=null){
                        sendTypeInfo= (SendTypeInfo) msg.obj;
                        if (sendTypeInfo==null||sendTypeInfo.datas.list==null||sendTypeInfo.datas.list.size()==0){
                            ll_null.setVisibility(View.VISIBLE);
                        }else{
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype==0){
                                sendTypeAdapter=new SendTypeAdapter(SendTypeActivity.this,sendTypeInfo.datas.list);
                                lv_kd.setAdapter(sendTypeAdapter);
                            }else if (refreshtype==1){
                                sendTypeAdapter.refreshData(sendTypeInfo.datas.list);
                                myListenr.refreshSucceed();
                            }else if (refreshtype==2){
                                sendTypeAdapter.addData(sendTypeInfo.datas.list);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case sms_log_list_err:
                    Toast.makeText(SendTypeActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendtype);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break= (LinearLayout) findViewById(R.id.ll_break);
        ll_null= (LinearLayout) findViewById(R.id.ll_null);
        et_input= (EditText) findViewById(R.id.et_input);
        iv_voice= (ImageView) findViewById(R.id.iv_voice);
        iv_volume= (ImageView) findViewById(R.id.iv_volume);
        tv_search= (TextView) findViewById(R.id.tv_search);
        tv_menu1= (TextView) findViewById(R.id.tv_menu1);
        tv_menu2= (TextView) findViewById(R.id.tv_menu2);
        tv_menu3= (TextView) findViewById(R.id.tv_menu3);
        v_slide1=findViewById(R.id.v_slide1);
        v_slide2=findViewById(R.id.v_slide2);
        v_slide3=findViewById(R.id.v_slide3);
        lv_kd= (PullableListView) findViewById(R.id.lv_kd);
        refresh_view= (PullToRefreshLayout) findViewById(R.id.refresh_view);
        initView();
    }

    @Override
    protected void initView() {
        ll_break.setOnClickListener(this);
        iv_voice.setOnTouchListener(l);
        tv_search.setOnClickListener(this);
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        tv_menu3.setOnClickListener(this);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        netRun=new NetRun(this,handler);
        xunfei();
        initData();
    }

    @Override
    protected void initData() {
        netRun.SmsLogList(pagesize,curpage+"",state,inphone);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_break) {
            finish();
        } else if (id == R.id.tv_search) {//搜索
            String s = et_input.getText().toString();
            if (TextUtils.isEmpty(s)) {
                inphone = "";
            }
            inphone = s;
            refreshtype = 1;
            curpage = 1;
            netRun.SmsLogList(pagesize, curpage + "", state, inphone);
        } else if (id == R.id.tv_menu1) {//全部
            state = "0";
            setMune(v_slide1);
        } else if (id == R.id.tv_menu2) {//发送成功
            state = "1";
            setMune(v_slide2);
        } else if (id == R.id.tv_menu3) {//发送失败
            state = "2";
            setMune(v_slide3);
        }
    }

    /**
     * 修改菜单
     * @param v
     */
    private void setMune(View v){
        v_slide1.setVisibility(View.INVISIBLE);
        v_slide2.setVisibility(View.INVISIBLE);
        v_slide3.setVisibility(View.INVISIBLE);
        v.setVisibility(View.VISIBLE);
        refreshtype = 1;
        curpage = 1;
        netRun.SmsLogList(pagesize,curpage+"",state,inphone);
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

    private SpeechRecognizer mIat;
    boolean isrepeat=false;
    private void xunfei() {
        // 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(SendTypeActivity.this, null);
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
            CommonTools.showShortToast(SendTypeActivity.this, plainDescription);
        }

        // 开始录音
        @Override
        public void onBeginOfSpeech() {
            iv_volume.setVisibility(View.VISIBLE);
        }

        // 音量值0~30
        @Override
        public void onVolumeChanged(int arg0, byte[] arg1) {
            if (arg0<6){
                iv_volume.setImageResource(R.drawable.volume1);
            }else if (arg0<11){
                iv_volume.setImageResource(R.drawable.volume2);
            }else if (arg0<16){
                iv_volume.setImageResource(R.drawable.volume3);
            }else if (arg0<21){
                iv_volume.setImageResource(R.drawable.volume4);
            }else if (arg0<26){
                iv_volume.setImageResource(R.drawable.volume5);
            }else{
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

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        private PullToRefreshLayout pullToRefreshLayout;

        private Handler refreshHandler = new Handler() {

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case pull_down_refresh_success:// 下拉刷新成功
                        if (pullToRefreshLayout != null) {
                            pullToRefreshLayout
                                    .refreshFinish(PullToRefreshLayout.SUCCEED);
                        }
                        break;
                    case pull_up_loaded_success:// 上拉加载成功
                        if (pullToRefreshLayout != null) {
                            pullToRefreshLayout
                                    .loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        }
                        break;
                }
            };
        };

        /**
         * 下拉式刷新成功
         * */
        public void refreshSucceed() {
            refreshHandler.sendEmptyMessageDelayed(pull_down_refresh_success,
                    400);
        }

        /**
         * 上拉加载成功
         * */
        public void loadMoreSucceed() {
            refreshHandler.sendEmptyMessageDelayed(pull_up_loaded_success, 400);
        }

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            refreshtype = 1;
            curpage = 1;
            netRun.SmsLogList(pagesize,curpage+"",state,inphone);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (sendTypeInfo.hasmore.equals("true")) {
                refreshtype = 2;
                curpage++;
                netRun.SmsLogList(pagesize,curpage+"",state,inphone);
            } else {
                loadMoreSucceed();
                Toast.makeText(SendTypeActivity.this,
                        getString(R.string.act_no_data_load),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void ReGetData() {

    }
}
