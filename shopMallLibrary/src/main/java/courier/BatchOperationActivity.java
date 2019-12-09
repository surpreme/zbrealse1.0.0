package courier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.Mark;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.zxing.CaptureActivity;
import com.aiteshangcheng.a.R;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import courier.adapter.BatchOperationAdapter;
import courier.model.BatchOperationInfo;

/**
 * 批量入库
 * Created by Administrator on 2018/1/31.
 */
public class BatchOperationActivity extends Activity implements View.OnClickListener, Mark {
    private ListView lv_list;
    private LinearLayout ll_break;
    private TextView tv_name, tv_right, tv_affirm;
    private ImageView iv_volume;
    private BatchOperationAdapter batchOperationAdapter;
    private List<BatchOperationInfo> batchOperationInfo;
    private String name, express_id;
    private final int REQUEST_CODE = 999;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case delivery_bluk_storage_id://批量入库编号
                    if (msg.obj != null) {
                        batchOperationInfo = (List<BatchOperationInfo>) msg.obj;
                        if (batchOperationAdapter == null) {
                            batchOperationAdapter = new BatchOperationAdapter(BatchOperationActivity.this, handler, batchOperationInfo);
                            lv_list.setAdapter(batchOperationAdapter);
                        }
                    }
                    break;
                case delivery_bluk_storage_err:
                    Toast.makeText(BatchOperationActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case delivery_bluk_storage_success_id://批量入库
                   if (msg.obj!=null){
                       String str= (String) msg.obj;
                       Toast.makeText(BatchOperationActivity.this,str, Toast.LENGTH_SHORT).show();
                       if (str.equals("操作成功")){
                           finish();
                       }
                   }
                    break;
                case delivery_bluk_storage_success_err:
                    Toast.makeText(BatchOperationActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case 109://语音按下
                    isrepeat = false;
                    mIat.startListening(mRecoListener);
                    break;
                case 110://语音抬起
                    mIat.stopListening();
                    iv_volume.setImageResource(R.drawable.volume1);
                    iv_volume.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_batchoperation);
        findViewById();
    }

    protected void findViewById() {
        lv_list = (ListView) findViewById(R.id.lv_list);
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_affirm = (TextView) findViewById(R.id.tv_affirm);
        iv_volume = (ImageView) findViewById(R.id.iv_volume);
        initView();
    }

    protected void initView() {
        netRun = new NetRun(this, handler);
        ll_break.setVisibility(View.VISIBLE);
        tv_name.setText("批量入库");
        ll_break.setOnClickListener(this);
        tv_affirm.setOnClickListener(this);
        name = getIntent().getStringExtra("name");
        express_id = getIntent().getStringExtra("express_id");
        xunfei();
        Intent intent = new Intent(BatchOperationActivity.this, CaptureActivity.class);
        intent.putExtra("type", 2);
        intent.putExtra("isRepetition", true);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_break) {
            finish();
        } else if (id == R.id.tv_affirm) {//确认入库
            if (batchOperationAdapter != null) {
                String getjson = batchOperationAdapter.getjson(express_id, name);
                if (getjson.equals("1")) {
                    Toast.makeText(BatchOperationActivity.this, "请将资料填写完整", Toast.LENGTH_SHORT).show();
                    return;
                } else if (getjson.equals("2")) {
                    Toast.makeText(BatchOperationActivity.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
                    return;
                } else if (getjson.equals("3")) {
                    Toast.makeText(BatchOperationActivity.this, "出现未知错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("------------------", "josn  " + getjson);
                netRun.DeliveryBlukStorageSuccess(getjson);
            }
        }
    }

    private SpeechRecognizer mIat;
    boolean isrepeat = false;

    private void xunfei() {
        // 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(BatchOperationActivity.this, null);
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
            Log.i("-------------------", "语音  " + arg0.getResultString());
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
                    if (batchOperationAdapter != null) {
                        batchOperationAdapter.setPhone(sb.toString());
                    }
                    isrepeat = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // 会话发生错误回调接口
        @Override
        public void onError(SpeechError arg0) {
            String plainDescription = arg0.getPlainDescription(true); // 获取错误码描述}
            CommonTools.showShortToast(BatchOperationActivity.this, plainDescription);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra("result");
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < result.size(); i++) {
                    stringBuffer.append(result.get(i) + ",");
                }
                String str = stringBuffer.toString();
                if (str != null && str.length() != 0) {
                    str = str.substring(0, str.length() - 1);
                    Log.i("-----------------------", "结果  " + str);
                    netRun.DeliveryBlukStorage(str);
                }
            }
        }
    }

}
