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

import courier.adapter.CourierStorageAdapter;
import courier.model.CourierstorageInfo;

/**
 * 未入库
 * Created by Administrator on 2018/1/10.
 */
public class CourierstorageAcivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout ll_break,ll_null;
    private EditText et_input;
    private ImageView iv_voice,iv_volume;
    private TextView tv_search;
    private PullableListView lv_kd;
    private PullToRefreshLayout refresh_view;
    private CourierStorageAdapter courierStorageAdapter;
    private NetRun netRun;
    private String pagesize="20";
    private int curpage=1,refreshtype = 0;
    private MyListener myListenr;
    private CourierstorageInfo courierstorageInfo;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case delivery_order_list_id:
                    if (msg.obj!=null){
                        courierstorageInfo= (CourierstorageInfo) msg.obj;
                        if (courierstorageInfo==null||courierstorageInfo.datas.list.size()==0){
                            ll_null.setVisibility(View.VISIBLE);
                        }else{
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0) {
                                courierStorageAdapter=new CourierStorageAdapter(CourierstorageAcivity.this,courierstorageInfo,handler);
                                lv_kd.setAdapter(courierStorageAdapter);
                            }else if (refreshtype == 1){//刷新
                                courierStorageAdapter.refreshData(courierstorageInfo);
                                myListenr.refreshSucceed();
                            }else  if (refreshtype == 2){//加载
                                courierStorageAdapter.addData(courierstorageInfo);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case delivery_order_list_err:
                    Toast.makeText(CourierstorageAcivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case 107://入库
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Bundle bundle = new Bundle();
                        bundle.putString("id",str);
                        Intent intent;
                        // 消息广播频道
                        intent = new Intent("homemenu");
                        intent.putExtras(bundle);
                        // 有序
                        sendOrderedBroadcast(intent, null);
                        finish();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courierstorage);
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
        lv_kd= (PullableListView) findViewById(R.id.lv_kd);
        refresh_view= (PullToRefreshLayout) findViewById(R.id.refresh_view);
        initView();
    }

    @Override
    protected void initView() {
        ll_break.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        iv_voice.setOnTouchListener(l);
        tv_search.setOnClickListener(this);

        netRun=new NetRun(this,handler);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        initData();
    }

    @Override
    protected void initData() {
        xunfei();
        netRun.DeliveryOrderList(pagesize,curpage+"");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_break) {
            finish();
        } else if (id == R.id.tv_search) {//搜索
            refreshtype = 1;
            String search = et_input.getText().toString();
            if (TextUtils.isEmpty(search)) return;
            netRun.DeliveryOrderPhone(search);
        }
    }

    @Override
    public void ReGetData() {

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
        mIat = SpeechRecognizer.createRecognizer(CourierstorageAcivity.this, null);
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
            CommonTools.showShortToast(CourierstorageAcivity.this, plainDescription);
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
            netRun.DeliveryOrderList(pagesize,curpage+"");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
                if (courierstorageInfo.hasmore.equals("true")) {
                    refreshtype = 2;
                    curpage++;
                    netRun.DeliveryOrderList(pagesize,curpage+"");
                } else {
                    loadMoreSucceed();
                    Toast.makeText(CourierstorageAcivity.this,
                            getString(R.string.act_no_data_load),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

}
