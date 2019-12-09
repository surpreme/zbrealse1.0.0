package courier.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import courier.model.BatchOperationInfo;

/**
 * 批量入库
 * Created by Administrator on 2018/2/1.
 */
public class BatchOperationAdapter extends BaseAdapter {
    private Context mcontext;
    private Handler handler;
    private List<BatchOperationInfo> batchOperationInfo;
    private int yy=0;

    public BatchOperationAdapter(Context mcontext, Handler handler, List<BatchOperationInfo> batchOperationInfo) {
        this.mcontext = mcontext;
        this.handler = handler;
        this.batchOperationInfo = batchOperationInfo;
    }

    /**
     * 添加
     * @param batchOperationInfo
     */
    public void addData(List<BatchOperationInfo> batchOperationInfo){
        if (batchOperationInfo==null)return;
        batchOperationInfo.addAll(batchOperationInfo);
        notifyDataSetChanged();
    }

    /**
     * 删除
     * @param id
     */
    private void delItem(int id){
        batchOperationInfo.remove(id);
        notifyDataSetChanged();
    }

    /**
     * 修改手机号
     * @param str
     */
    public void setPhone(String str){
        batchOperationInfo.get(yy).consignee_mobile=str;
        notifyDataSetChanged();
    }

    /**
     * 获取提交数据
     * @return
     */
    public String getjson(String express_id,String express_name){
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i=0;i<batchOperationInfo.size();i++){
                if (batchOperationInfo.get(i).consignee==null||batchOperationInfo.get(i).consignee.length()==0
                        ||batchOperationInfo.get(i).consignee_mobile==null||batchOperationInfo.get(i).consignee_mobile.length()==0){
                    return "1";
                }
                if (!isChinaPhoneLegal(batchOperationInfo.get(i).consignee_mobile)){
                    return "2";
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("express_code",batchOperationInfo.get(i).express_code);
                jsonObject.put("express_no",batchOperationInfo.get(i).express_no);
                jsonObject.put("consignee",batchOperationInfo.get(i).consignee);
                jsonObject.put("consignee_mobile",batchOperationInfo.get(i).consignee_mobile);
                jsonObject.put("express_id",express_id);
                jsonObject.put("express_name",express_name);
                jsonArray.put(jsonObject);
            }
        }catch (Exception e){
            return "3";
        }
        return jsonArray.toString();
    }

    @Override
    public int getCount() {
        return batchOperationInfo == null ? 0 : batchOperationInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return batchOperationInfo == null ? null : batchOperationInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_batch, null);
            new BatchHolder(convertView);
        }
        BatchHolder holder = (BatchHolder) convertView.getTag();
        final BatchOperationInfo info = this.batchOperationInfo.get(position);
        holder.tv_express_no.setText(info.express_no);
        holder.tv_express_code.setText(info.express_code);
        holder.tv_consignee_mobile.setText(info.consignee_mobile==null?"":info.consignee_mobile);
        holder.tv_consignee.setText(info.consignee==null?"":info.consignee);
        holder.iv_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                delItem(position);
            }
        });
        holder.iv_call.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        yy=position;
                        handler.sendEmptyMessage(109);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessage(110);
                        break;
                }
                return false;
            }
        });
        holder.tv_consignee_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()==null){
                    info.consignee_mobile="";
                }else{
                    info.consignee_mobile=s.toString();
                }
            }
        });
        holder.tv_consignee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()==null){
                    info.consignee="";
                }else{
                    info.consignee=s.toString();
                }
            }
        });
        return convertView;
    }

    class BatchHolder {
        TextView tv_express_no, tv_express_code;
        EditText tv_consignee_mobile, tv_consignee;
        ImageView iv_call, iv_cha;

        public BatchHolder(View convertView) {
            tv_express_no = (TextView) convertView.findViewById(R.id.tv_express_no);
            tv_express_code = (TextView) convertView.findViewById(R.id.tv_express_code);
            tv_consignee_mobile = (EditText) convertView.findViewById(R.id.tv_consignee_mobile);
            tv_consignee = (EditText) convertView.findViewById(R.id.tv_consignee);
            iv_call = (ImageView) convertView.findViewById(R.id.iv_call);
            iv_cha = (ImageView) convertView.findViewById(R.id.iv_cha);
            convertView.setTag(this);
        }
    }

    /**
     * 判断手机格式
     *
     */
    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
