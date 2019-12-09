package courier.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.WebActivity;
import com.aite.a.fargment.BaseFragment;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyDialog;
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

import courier.NoticeActivity;
import courier.StorageLogActivity;
import courier.adapter.CourierHomeMenuAdapter;
import courier.model.CourierHomeInfo;

/**
 * 首页
 * Created by Administrator on 2018/1/8.
 */
public class CourierHomeFragment extends BaseFragment implements View.OnClickListener{
    private MyAdGallery adgallery;
    private LinearLayout ovalLayout;
    private ImageView iv_viceo,iv_msg,iv_volume;
    private EditText et_search,et_code;
    private TextView tv_send,tv_qx,tv_qs;
    private GridView gv_menu;
    private MyDialog mydialog;
    private View dialoglayout;
    private CourierHomeInfo courierHomeInfo;
    private CourierHomeMenuAdapter courierHomeMenuAdapter;
    private NetRun netRun;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case courier_home_id://驿站首页
                    if (msg.obj!=null){
                        courierHomeInfo= (CourierHomeInfo) msg.obj;
                        setAD(courierHomeInfo.list);
                    }
                    break;
                case courier_home_err:
                    Toast.makeText(getActivity(), "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case 103:
                    if (mydialog==null){
                        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                        mydialog=new MyDialog(getActivity(),wm.getDefaultDisplay().getWidth()-100,650,dialoglayout,R.style.loading_dialog);
                    }
                    et_code.setText("");
                    mydialog.show();
                    break;
                case sing_for_id://签收
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case sing_for_err:
                    Toast.makeText(getActivity(), "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected int layoutResID() {
        return R.layout.fragment_courierhome;
    }

    private void findviewbrid(){
        adgallery= (MyAdGallery) layout.findViewById(R.id.adgallery);
        ovalLayout= (LinearLayout) layout.findViewById(R.id.ovalLayout);
        iv_viceo= (ImageView) layout.findViewById(R.id.iv_viceo);
        iv_msg= (ImageView) layout.findViewById(R.id.iv_msg);
        iv_volume= (ImageView) layout.findViewById(R.id.iv_volume);
        et_search= (EditText) layout.findViewById(R.id.et_search);
        tv_send= (TextView) layout.findViewById(R.id.tv_send);
        gv_menu= (GridView) layout.findViewById(R.id.gv_menu);

        dialoglayout=View.inflate(getActivity(),R.layout.dialog_signfor,null);
        et_code= (EditText) dialoglayout.findViewById(R.id.et_code);
        tv_qs= (TextView) dialoglayout.findViewById(R.id.tv_qs);
        tv_qx= (TextView) dialoglayout.findViewById(R.id.tv_qx);
    }

    @Override
    protected void initView() {
        findviewbrid();
        iv_viceo.setOnTouchListener(l);
        tv_send.setOnClickListener(this);
        iv_msg.setOnClickListener(this);
        tv_qs.setOnClickListener(this);
        tv_qx.setOnClickListener(this);
        courierHomeMenuAdapter=new CourierHomeMenuAdapter(getActivity(),handler);
        gv_menu.setAdapter(courierHomeMenuAdapter);
        xunfei();
        netRun=new NetRun(getActivity(),handler);
    }

    @Override
    protected void initData() {
        netRun.CourierHome();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            netRun.CourierHome();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_send) {//搜索
            String s = et_search.getText().toString();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(getActivity(), "请输入提货码/运单号/手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getActivity(), StorageLogActivity.class);
            intent.putExtra("con", s);
            startActivity(intent);
        } else if (id == R.id.iv_msg) {//通知
            Intent intent2 = new Intent(getActivity(), NoticeActivity.class);
            startActivity(intent2);
        } else if (id == R.id.tv_qx) {
            if (mydialog != null) {
                mydialog.dismiss();
            }
        } else if (id == R.id.tv_qs) {//签收
            if (mydialog != null) {
                mydialog.dismiss();
            }
            String str = et_code.getText().toString();
            netRun.SingFor(str);
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
                    break;
            }
            return false;
        }
    };

    /**
     * 设置广告轮播
     *
     * @param obj
     */
    protected void setAD(final List<CourierHomeInfo.list> obj) {
        List<String> listAd = new ArrayList<String>();
        for (int i=0;i<obj.size();i++){
            listAd.add(obj.get(i).img_path);
        }
        String[] ADurl = listAd.toArray(new String[listAd.size()]);
        if (adgallery.mUris == null)
            adgallery.start(getActivity(), ADurl, null, 3000, ovalLayout,
                    R.drawable.dot_focused, R.drawable.dot_normal);
        adgallery.setMyOnItemClickListener(new MyAdGallery.MyOnItemClickListener() {

            @Override
            public void onItemClick(int curIndex) {
                Bundle bundle = new Bundle();
                bundle.putString("url", obj.get(curIndex).href);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private SpeechRecognizer mIat;
    boolean isrepeat=false;
    private void xunfei() {
        // 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(getActivity(), null);
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
                    et_search.append(sb.toString());
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
            CommonTools.showShortToast(getActivity(), plainDescription);
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

}
