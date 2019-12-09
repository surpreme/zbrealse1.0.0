package courier.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.fargment.BaseFragment;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.zxing.CaptureActivity;
import com.aiteshangcheng.a.R;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.suke.widget.SwitchButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import courier.BatchOperationActivity;
import courier.CustomerListActivity;
import courier.SmsModerActivity;
import courier.View.ExpressPopu;
import courier.model.DeliveryCourierInfo;
import courier.model.DeliveryOrderInfo;

/**
 * 入库
 * <p/>
 * Created by Administrator on 2018/1/8.
 */
public class CourierWarehouseFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_name, tv_empty, tv_affirm, tv_express_no , tv_smsname,tv_dxcon,tv_right;
    private RelativeLayout rl_menu1, rl_menu2, rl_menu3, rl_menu4, rl_menu5, rl_menu6, rl_menu7, rl_menu8;
    private EditText tv_express_code,tv_consignee_mobile, tv_consignee;
    private ImageView iv_vrcode,iv_call,iv_card,iv_volume;
    private SwitchButton sb_tx, sb_px, sb_dx;
    private List<DeliveryCourierInfo> deliveryCourierInfo;
    private RelativeLayout i_title;
    private String phone, name, sms_code, express_id, express_name, express_code, express_no;//客户名称/手机/短信模板/快递公司编号/快递公司名称/快递单号/入库编号/短信模板名称
    private String id;//站点入库ID
    private final int REQUEST_CODE = 999, CLIENT_CODE = 998, SMS_CODE = 997;//二维码/客户/短信
    private DeliveryOrderInfo deliveryOrderInfo;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case delivery_courier_id://快递公司
                    if (msg.obj != null) {
                        deliveryCourierInfo = (List<DeliveryCourierInfo>) msg.obj;
                    }
                    break;
                case delivery_courier_err:
                    Toast.makeText(getActivity(), "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case express_no_id://入库编号
                    if (msg.obj != null) {
                        express_no = (String) msg.obj;
                        tv_express_no.setText("入库编号 " + express_no);
                    }
                    break;
                case express_no_err:
                    Toast.makeText(getActivity(), "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case express_storage_id://快递入库
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                        if (str.equals("入库成功")) {
                            emptyChoseData();
                        }
                    }
                    break;
                case express_storage_err:
                    Toast.makeText(getActivity(), "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case delivery_order_info_id://站点订单信息
                    if (msg.obj != null) {
                        deliveryOrderInfo = (DeliveryOrderInfo) msg.obj;
                        tv_express_code.setText(deliveryOrderInfo.shipping_code);
                        tv_consignee_mobile.setText(deliveryOrderInfo.reciver_mobphone == null ? deliveryOrderInfo.reciver_telphone : deliveryOrderInfo.reciver_mobphone);
                        tv_consignee.setText(deliveryOrderInfo.reciver_name);
                        tv_name.setText(deliveryOrderInfo.express_name);
                    }
                    break;
                case delivery_order_info_err:
                    Toast.makeText(getActivity(), "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case 109://弹出快递公司
                    showpopu();
                    break;
            }
        }
    };

    /**
     * 入库ID
     *
     * @param id
     */
    public void setid(String id) {
        this.id = id;
        if (id == null) return;
        if (netRun == null) {
            netRun = new NetRun(getActivity(), handler);
        }
        netRun.DeliveryOrderInfo(id);
        netRun.ExpressNo();
    }

    private void findviewbrid() {
        tv_name = (TextView) layout.findViewById(R.id.tv_name);
        tv_empty = (TextView) layout.findViewById(R.id.tv_empty);
        tv_affirm = (TextView) layout.findViewById(R.id.tv_affirm);
        tv_dxcon = (TextView) layout.findViewById(R.id.tv_dxcon);
        tv_right = (TextView) layout.findViewById(R.id.tv_right);
        tv_express_code = (EditText) layout.findViewById(R.id.tv_express_code);
        tv_express_no = (TextView) layout.findViewById(R.id.tv_express_no);
        tv_consignee_mobile = (EditText) layout.findViewById(R.id.tv_consignee_mobile);
        tv_consignee = (EditText) layout.findViewById(R.id.tv_consignee);
        tv_smsname = (TextView) layout.findViewById(R.id.tv_smsname);
        rl_menu1 = (RelativeLayout) layout.findViewById(R.id.rl_menu1);
        rl_menu2 = (RelativeLayout) layout.findViewById(R.id.rl_menu2);
        rl_menu3 = (RelativeLayout) layout.findViewById(R.id.rl_menu3);
        rl_menu4 = (RelativeLayout) layout.findViewById(R.id.rl_menu4);
        rl_menu5 = (RelativeLayout) layout.findViewById(R.id.rl_menu5);
        rl_menu6 = (RelativeLayout) layout.findViewById(R.id.rl_menu6);
        rl_menu7 = (RelativeLayout) layout.findViewById(R.id.rl_menu7);
        rl_menu8 = (RelativeLayout) layout.findViewById(R.id.rl_menu8);
        i_title = (RelativeLayout) layout.findViewById(R.id.i_title);
        sb_tx = (SwitchButton) layout.findViewById(R.id.sb_tx);
        sb_px = (SwitchButton) layout.findViewById(R.id.sb_px);
        sb_dx = (SwitchButton) layout.findViewById(R.id.sb_dx);
        iv_vrcode= (ImageView) layout.findViewById(R.id.iv_vrcode);
        iv_call= (ImageView) layout.findViewById(R.id.iv_call);
        iv_card= (ImageView) layout.findViewById(R.id.iv_card);
        iv_volume= (ImageView) layout.findViewById(R.id.iv_volume);
    }

    @Override
    protected void initView() {
        findviewbrid();
        tv_name.setText("请选择快递公司");
        tv_right.setText("批量入库");
        iv_vrcode.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        iv_call.setOnTouchListener(l);
        iv_card.setOnClickListener(this);
        rl_menu7.setOnClickListener(this);
        tv_empty.setOnClickListener(this);
        tv_affirm.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        if (netRun == null) {
            netRun = new NetRun(getActivity(), handler);
        }
        handler.sendEmptyMessageDelayed(109,500);
        netRun.ExpressNo();
    }

    @Override
    protected void initData() {
        netRun.DeliveryCourier();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            emptyChoseData();
            showpopu();
            netRun.ExpressNo();
        }
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_courierwarehouse;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.iv_vrcode) {//扫码
            if (id != null) return;
            Intent intent = new Intent(getActivity(), CaptureActivity.class);
            intent.putExtra("type", 1);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (vId == R.id.iv_card) {//客户列表
            if (id != null) return;
            Intent intent9 = new Intent(getActivity(), CustomerListActivity.class);
            intent9.putExtra("isrk", true);
            startActivityForResult(intent9, CLIENT_CODE);
        } else if (vId == R.id.rl_menu7) {//短信模板
            Intent dxintent = new Intent(getActivity(), SmsModerActivity.class);
            dxintent.putExtra("isrk", true);
            startActivityForResult(dxintent, SMS_CODE);
        } else if (vId == R.id.tv_empty) {//清空
            emptyChoseData();
        } else if (vId == R.id.tv_affirm) {//确认
            if (sms_code == null) {
                Toast.makeText(getActivity(), "请选择短信模板", Toast.LENGTH_SHORT).show();
                return;
            }
            if (id != null) {//站点入库
                if (deliveryOrderInfo != null) {
                    netRun.DeliveryOrderState(deliveryOrderInfo.order_id, deliveryOrderInfo.express_code, deliveryOrderInfo.express_name, deliveryOrderInfo.shipping_code, express_no,
                            deliveryOrderInfo.reciver_name, deliveryOrderInfo.reciver_mobphone == null ? deliveryOrderInfo.reciver_telphone : deliveryOrderInfo.reciver_mobphone,
                            sb_tx.isChecked() ? "1" : "0", sb_px.isChecked() ? "1" : "0", sb_dx.isChecked() ? "0" : "1", sms_code);
                }
            } else {//入库
                express_code = tv_express_code.getText().toString();
                phone = tv_consignee_mobile.getText().toString();
                name = tv_consignee.getText().toString();
                if (express_id == null) {
                    Toast.makeText(getActivity(), "请选择快递公司", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (express_code == null) {
                    Toast.makeText(getActivity(), "请输入快递编号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name == null) {
                    Toast.makeText(getActivity(), "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone == null) {
                    Toast.makeText(getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                netRun.ExpressStorage(express_id, express_name, express_code, express_no, name, phone, sb_tx.isChecked() ? "1" : "0", sb_px.isChecked() ? "1" : "0", sb_dx.isChecked() ? "0" : "1", sms_code);
            }
        } else if (vId == R.id.tv_name) {//快递方式
            showpopu();
        } else if (vId == R.id.tv_right) {//批量入库
            if (express_id == null) {
                Toast.makeText(getActivity(), "请选择快递公司", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent1 = new Intent(getActivity(), BatchOperationActivity.class);
                intent1.putExtra("name", tv_name.getText().toString());
                intent1.putExtra("express_id", express_id);
                startActivity(intent1);
            }
        }
    }

    /**
     * 清除
     */
    private void emptyChoseData() {
        phone = null;
        name = null;
        express_id = null;
        sms_code = null;
        express_no = null;
        tv_name.setText("请选择快递公司");
        tv_express_no.setText("快递编号");
        tv_express_code.setText("");
        tv_consignee_mobile.setText("");
        tv_consignee.setText("");
        sb_tx.setChecked(false);
        sb_px.setChecked(false);
        sb_dx.setChecked(true);
        tv_dxcon.setText("");
        tv_dxcon.setVisibility(View.GONE);
    }

    /**
     * 显示菜单
     */
    private void showpopu() {
        if (deliveryCourierInfo == null) return;
        ExpressPopu expressPopu = new ExpressPopu(getActivity(), deliveryCourierInfo);
//        expressPopu.setBackgroundDrawable(new ColorDrawable(Color
//                .parseColor("#00000000")));
        expressPopu.setmenu(new ExpressPopu.menu() {
            @Override
            public void onItemClick(String id) {
                express_id = id;
                for (int i = 0; i < deliveryCourierInfo.size(); i++) {
                    if (id.equals(deliveryCourierInfo.get(i).id)) {
                        express_name = deliveryCourierInfo.get(i).e_name;
                        tv_name.setText(deliveryCourierInfo.get(i).e_name);
                    }
                }
            }
        });
        expressPopu.showAsDropDown(i_title, 0, 0);
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
                    tv_consignee_mobile.append(sb.toString());
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                String result = data.getStringExtra("result");
                Log.i("-----------------------", "扫描结果 " + result);
                express_code = result;
                tv_express_code.setText(result);

            }
        } else if (requestCode == CLIENT_CODE) {
            //处理客户选择
            if (data != null) {
                String id = data.getStringExtra("id");
                phone = data.getStringExtra("phone");
                name = data.getStringExtra("name");
                tv_consignee_mobile.setText(phone);
                tv_consignee.setText(name);
            }
        } else if (requestCode == SMS_CODE) {
            //处理短信选择
            sms_code = data.getStringExtra("id");
            String name = data.getStringExtra("name");
            tv_dxcon.setVisibility(View.VISIBLE);
            tv_dxcon.setText(name);
        }
    }


}
