package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.adapter.InvoiceListAdapter;
import com.aite.a.adapter.SpinnerAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.InvoiceListInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 发票
 * Created by mayn on 2018/8/16.
 */

public class InvoiceActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_type1, tv_type2, tv_address, tv_save, tv_save2;
    private ImageView iv_goback;
    private CheckBox cb_ordinary, cb_vta;
    private LinearLayout ll_ordinary;
    private Spinner sp_type, sp_desc;
    private LinearLayout ll_companyname, ll_vta;
    private EditText et_company, et_company2, et_taxnumber, et_registeredaddress, et_registeredphone, et_bank, et_bankuser, et_name, et_phone, et_address2;
    private ListView lv_list;
    private ScrollView sv_new;

    private InvoiceListInfo invoiceListInfo;
    private InvoiceListAdapter invoiceListAdapter;
    private String type = "1", vat_hash, address = null;
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case invoice_list_id://发票列表
                    if (msg.obj != null) {
                        invoiceListInfo = (InvoiceListInfo) msg.obj;
                        if (invoiceListInfo.message.equals(getString(R.string.evaluation_tips65))) {
                            invoiceListAdapter = new InvoiceListAdapter(InvoiceActivity.this, invoiceListInfo.data.inv_list, handler);
                            lv_list.setAdapter(invoiceListAdapter);
                        } else {
                            Toast.makeText(appSingleton, invoiceListInfo.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case invoice_list_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case new_invoice_id://添加发票
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (isNumeric(str)) {
                            netRun.getInvoiceList(vat_hash, "");
                            Toast.makeText(appSingleton, getString(R.string.evaluation_tips66), Toast.LENGTH_SHORT).show();
                            type = "1";
                            tv_type1.setBackgroundResource(R.drawable.drawmoney_typea1);
                            tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb2);
                            tv_type1.setTextColor(Color.WHITE);
                            tv_type2.setTextColor(0xffED5457);
                            lv_list.setVisibility(View.VISIBLE);
                            sv_new.setVisibility(View.GONE);
                        }
                    }
                    break;
                case new_invoice_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case DELETE_INVOICE://删除发票
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        netRun.getInvoiceList(vat_hash, str);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        tv_type1 = (TextView) findViewById(R.id.tv_type1);
        tv_type2 = (TextView) findViewById(R.id.tv_type2);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save2 = (TextView) findViewById(R.id.tv_save2);
        cb_ordinary = (CheckBox) findViewById(R.id.cb_ordinary);
        cb_vta = (CheckBox) findViewById(R.id.cb_vta);
        ll_ordinary = (LinearLayout) findViewById(R.id.ll_ordinary);
        sp_type = (Spinner) findViewById(R.id.sp_type);
        sp_desc = (Spinner) findViewById(R.id.sp_desc);
        ll_companyname = (LinearLayout) findViewById(R.id.ll_companyname);
        ll_vta = (LinearLayout) findViewById(R.id.ll_vta);
        et_company = (EditText) findViewById(R.id.et_company);
        et_company2 = (EditText) findViewById(R.id.et_company2);
        et_taxnumber = (EditText) findViewById(R.id.et_taxnumber);
        et_registeredaddress = (EditText) findViewById(R.id.et_registeredaddress);
        et_registeredphone = (EditText) findViewById(R.id.et_registeredphone);
        et_bank = (EditText) findViewById(R.id.et_bank);
        et_bankuser = (EditText) findViewById(R.id.et_bankuser);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address2 = (EditText) findViewById(R.id.et_address2);
        lv_list = (ListView) findViewById(R.id.lv_list);
        sv_new = (ScrollView) findViewById(R.id.sv_new);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        iv_goback.setOnClickListener(this);
        tv_type1.setOnClickListener(this);
        tv_type2.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_save2.setOnClickListener(this);
        cb_ordinary.setOnClickListener(this);
        cb_vta.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        vat_hash = getIntent().getStringExtra("vat_hash");

        List<String> menu = new ArrayList<String>();
        menu.add(getString(R.string.invoice24));
        menu.add(getString(R.string.invoice25));
        SpinnerAdapter adapter = new SpinnerAdapter(this,
                android.R.layout.simple_spinner_item, menu);
        sp_type.setAdapter(adapter);
        sp_type.setOnItemSelectedListener(item);

        List<String> menu2 = new ArrayList<String>();
        menu2.add(getString(R.string.invoice16));
        menu2.add(getString(R.string.invoice26));
        menu2.add(getString(R.string.invoice27));
        menu2.add(getString(R.string.invoice28));
        menu2.add(getString(R.string.invoice29));
        menu2.add(getString(R.string.invoice30));
        menu2.add(getString(R.string.invoice31));
        menu2.add(getString(R.string.invoice32));
        menu2.add(getString(R.string.invoice33));
        menu2.add(getString(R.string.invoice34));
        menu2.add(getString(R.string.invoice35));
        menu2.add(getString(R.string.invoice36));
        menu2.add(getString(R.string.invoice37));
        SpinnerAdapter adapter2 = new SpinnerAdapter(this,
                android.R.layout.simple_spinner_item, menu2);
        sp_desc.setAdapter(adapter2);
        initData();
    }

    @Override
    protected void initData() {
        netRun.getInvoiceList(vat_hash, "");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_goback) {
            finish();
        } else if (v.getId() == R.id.tv_address) {
            //选地址
            Intent intent = new Intent();
            intent.setClass(this, AddresSregionListActivity.class);
            startActivityForResult(intent, Sregion_Result);
        } else if (v.getId() == R.id.tv_type1) {
            type = "1";
            tv_type1.setBackgroundResource(R.drawable.drawmoney_typea1);
            tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb2);
            tv_type1.setTextColor(Color.WHITE);
            tv_type2.setTextColor(0xffED5457);
            lv_list.setVisibility(View.VISIBLE);
            sv_new.setVisibility(View.GONE);
        } else if (v.getId() == R.id.tv_type2) {
            type = "2";
            tv_type1.setBackgroundResource(R.drawable.drawmoney_typea2);
            tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb1);
            tv_type2.setTextColor(Color.WHITE);
            tv_type1.setTextColor(0xffED5457);
            lv_list.setVisibility(View.GONE);
            sv_new.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.tv_save) {
            if (type.equals("1")) {//选中
                if (invoiceListAdapter == null) {
                    Toast.makeText(this, getString(R.string.invoice38), Toast.LENGTH_SHORT).show();
                    return;
                }
                InvoiceListInfo.data.inv_list inv_list = invoiceListAdapter.getpick();
                if (inv_list == null) {
                    Toast.makeText(this, getString(R.string.invoice39), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intentr = new Intent(InvoiceActivity.this, OrderSureActivity.class);
                    intentr.putExtra("id", inv_list.inv_id);
                    intentr.putExtra("desc", inv_list.content);
                    setResult(PICK_INVOICE, intentr);
                    finish();
                }
            } else {//保存
                save();
            }
        } else if (v.getId() == R.id.tv_save2) {
            Intent intentr = new Intent(InvoiceActivity.this, OrderSureActivity.class);
            intentr.putExtra("id", "");
            intentr.putExtra("desc", getString(R.string.invoice23));
            setResult(PICK_INVOICE, intentr);
            finish();
        } else if (v.getId() == R.id.cb_ordinary) {
            cb_vta.setChecked(false);
            cb_ordinary.setChecked(true);
            ll_ordinary.setVisibility(View.VISIBLE);
            ll_vta.setVisibility(View.GONE);
        } else if (v.getId() == R.id.cb_vta) {
            cb_ordinary.setChecked(false);
            cb_vta.setChecked(true);
            ll_ordinary.setVisibility(View.GONE);
            ll_vta.setVisibility(View.VISIBLE);
        }


//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_address:
//                //选地址
//                Intent intent = new Intent();
//                intent.setClass(this, AddresSregionListActivity.class);
//                startActivityForResult(intent, Sregion_Result);
//                break;
//            case R.id.tv_type1:
//                type = "1";
//                tv_type1.setBackgroundResource(R.drawable.drawmoney_typea1);
//                tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb2);
//                tv_type1.setTextColor(Color.WHITE);
//                tv_type2.setTextColor(0xffED5457);
//                lv_list.setVisibility(View.VISIBLE);
//                sv_new.setVisibility(View.GONE);
//                break;
//            case R.id.tv_type2:
//                type = "2";
//                tv_type1.setBackgroundResource(R.drawable.drawmoney_typea2);
//                tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb1);
//                tv_type2.setTextColor(Color.WHITE);
//                tv_type1.setTextColor(0xffED5457);
//                lv_list.setVisibility(View.GONE);
//                sv_new.setVisibility(View.VISIBLE);
//                break;
//            case R.id.tv_save://保存
//                if (type.equals("1")) {//选中
//                    if (invoiceListAdapter == null) {
//                        Toast.makeText(this, getString(R.string.invoice38), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    InvoiceListInfo.data.inv_list inv_list = invoiceListAdapter.getpick();
//                    if (inv_list == null) {
//                        Toast.makeText(this, getString(R.string.invoice39), Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        Intent intentr = new Intent(InvoiceActivity.this, OrderSureActivity.class);
//                        intentr.putExtra("id", inv_list.inv_id);
//                        intentr.putExtra("desc", inv_list.content);
//                        setResult(PICK_INVOICE, intentr);
//                        finish();
//                    }
//                } else {//保存
//                    save();
//                }
//                break;
//            case R.id.tv_save2://不需要发票
//                Intent intentr = new Intent(InvoiceActivity.this, OrderSureActivity.class);
//                intentr.putExtra("id", "");
//                intentr.putExtra("desc", getString(R.string.invoice23));
//                setResult(PICK_INVOICE, intentr);
//                finish();
//                break;
//            case R.id.cb_ordinary://普通发票
//                cb_vta.setChecked(false);
//                cb_ordinary.setChecked(true);
//                ll_ordinary.setVisibility(View.VISIBLE);
//                ll_vta.setVisibility(View.GONE);
//                break;
//            case R.id.cb_vta:
//                //增值税发票
//                cb_ordinary.setChecked(false);
//                cb_vta.setChecked(true);
//                ll_ordinary.setVisibility(View.GONE);
//                ll_vta.setVisibility(View.VISIBLE);
//                break;
//        }
    }

    private AdapterView.OnItemSelectedListener item = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (sp_type.getSelectedItem().toString().equals(getString(R.string.invoice25))) {
                ll_companyname.setVisibility(View.VISIBLE);
            } else {
                ll_companyname.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * 保存
     */
    private void save() {
        String invoice_type = "";
        String inv_title = "";
        String inv_content = "";

        String inv_company = "";
        String inv_code = "";
        String inv_reg_addr = "";
        String inv_reg_phone = "";
        String inv_reg_bname = "";
        String inv_reg_baccount = "";
        String inv_rec_name = "";
        String inv_rec_mobphone = "";
        String area_info = "";
        String inv_goto_addr = "";
        if (cb_ordinary.isChecked()) {
            invoice_type = "1";
        } else if (cb_vta.isChecked()) {
            invoice_type = "2";
        }
        if (invoice_type.equals("1")) {//普通发票
            if (sp_type.getSelectedItem().toString().equals(getString(R.string.invoice25))) {
                String s = et_company.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(appSingleton, getString(R.string.invoice40), Toast.LENGTH_SHORT).show();
                    return;
                }
                inv_title = s;
            } else {
                inv_title = getString(R.string.invoice24);
            }
            inv_content = sp_desc.getSelectedItem().toString();
        } else if (invoice_type.equals("2")) {//增值税发票
            String gsmc = et_company2.getText().toString();
            String nsr = et_taxnumber.getText().toString();
            String zcdz = et_registeredaddress.getText().toString();
            String zcdh = et_registeredphone.getText().toString();
            String khyh = et_bank.getText().toString();
            String khzh = et_bankuser.getText().toString();
            String sprxm = et_name.getText().toString();
            String sprsjh = et_phone.getText().toString();
            String spdz = et_address2.getText().toString();
            if (TextUtils.isEmpty(gsmc)) {
                Toast.makeText(appSingleton, getString(R.string.invoice40), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(nsr)) {
                Toast.makeText(appSingleton, getString(R.string.invoice41), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(zcdz)) {
                Toast.makeText(appSingleton, getString(R.string.invoice42), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(zcdh)) {
                Toast.makeText(appSingleton, getString(R.string.invoice43), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(khyh)) {
                Toast.makeText(appSingleton, getString(R.string.invoice44), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(khzh)) {
                Toast.makeText(appSingleton, getString(R.string.invoice45), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(sprxm)) {
                Toast.makeText(appSingleton, getString(R.string.invoice46), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(sprsjh)) {
                Toast.makeText(appSingleton, getString(R.string.invoice47), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(spdz)) {
                Toast.makeText(appSingleton, getString(R.string.invoice48), Toast.LENGTH_SHORT).show();
                return;
            }
            if (address == null) {
                Toast.makeText(appSingleton, getString(R.string.invoice49), Toast.LENGTH_SHORT).show();
                return;
            }
            inv_company = gsmc;
            inv_code = nsr;
            inv_reg_addr = zcdz;
            inv_reg_phone = zcdh;
            inv_reg_bname = khyh;
            inv_reg_baccount = khzh;
            inv_rec_name = sprxm;
            inv_rec_mobphone = sprsjh;
            inv_goto_addr = spdz;
            area_info = address;
        }
        netRun.NewInvoice(invoice_type, inv_title, inv_content, inv_company, inv_code, inv_reg_addr, inv_reg_phone, inv_reg_bname, inv_reg_baccount, inv_rec_name
                , inv_rec_mobphone, area_info, inv_goto_addr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            String district = bundle.getString("district_name");
            String city = bundle.getString("city_name");
            String province = bundle.getString("province_name");
            tv_address.setText(province + city + district);
            address = province + city + district;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void ReGetData() {

    }

    /**
     * 是否数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
