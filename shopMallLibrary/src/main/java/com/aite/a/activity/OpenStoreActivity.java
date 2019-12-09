package com.aite.a.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.adapter.BusinessCategoryAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.ManageCategoryList;
import com.aite.a.model.OpenStoreList;
import com.aite.a.model.OpenStoreList.GcList;
import com.aite.a.model.OpenStoreList.GradeList;
import com.aite.a.model.OpenStoreList.StoreClass;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.EditTextWithDel;
import com.aite.a.view.MyListView;

/**
 * 个人入住
 * 
 * @author CAOYOU
 * 
 */
public class OpenStoreActivity extends BaseActivity implements OnItemSelectedListener, OnClickListener {
	private EditTextWithDel et_store_name, et_detailed_address, et_contact_name, et_contact_phone, et_email, et_name, et_id_number, et_pay_name, et_pay_account, et_merchant_account, et_company_name;
	private Spinner sp_province, sp_city, sp_district, sp_store_level, sp_store_cg, sp_one_category, sp_three_category, sp_two_category;
	private Button bt_add, bt_submit;
	private ImageView iv_id_card_scanning;
	private CheckBox cb_look;
	private TextView tv_look;
	private MyListView cusiness_category;
	private List<ManageCategoryList> manageCategoryLists;
	private NetRun netRun;
	private String province, city, district, sc_id, sg_id, sg_name, sc_name, id_card_scanning, company_points;
	public String one_category, one_category_id, two_category, two_category_id, three_category, three_category_id;
	private List<String[]> provinceList, cityList, districtList = new ArrayList<String[]>();
	private OpenStoreList storeLists;
	private List<GcList> gcLists, gcLists2, gcLists3 = new ArrayList<GcList>();
	private BusinessCategoryAdapter businessCategoryAdapter;
	private int get_region_p = 0;
	private int gc_list_p = 0;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case get_region_id:
				if (msg.obj != null) {
					if (get_region_p == 0) {
						provinceList = (List<String[]>) msg.obj;
						sp_province.setAdapter(regionList(provinceList));
						sp_city.setVisibility(View.VISIBLE);
					}
					if (get_region_p == 1) {
						cityList = (List<String[]>) msg.obj;
						sp_city.setAdapter(regionList(cityList));
						sp_district.setVisibility(View.VISIBLE);
					}
					if (get_region_p == 2) {
						districtList = (List<String[]>) msg.obj;
						sp_district.setAdapter(regionList(districtList));
					}
				} else {
					CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.act_no_data_load));
				}
				break;
			case get_region_err:
				CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.act_net_error));
				break;
			case store_category_id:
				if (msg.obj != null) {
					storeLists = (OpenStoreList) msg.obj;
					gcLists = storeLists.gc_list;
					sp_store_cg.setAdapter(storeCg(storeLists.store_class));
					sp_store_level.setAdapter(storeLevel(storeLists.grade_lists));
					sp_one_category.setAdapter(businessAdapter(gcLists));
				} else {
					CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.act_no_data_load));
				}
				break;
			case getgclist_id:
				if (msg.obj != null) {
					if (gc_list_p == 1) {
						gcLists2 = (List<GcList>) msg.obj;
						sp_two_category.setAdapter(businessAdapter(gcLists2));
					}
					if (gc_list_p == 2) {
						gcLists3 = (List<GcList>) msg.obj;
						sp_three_category.setAdapter(businessAdapter(gcLists3));
					}
				} else {
					CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.act_no_data_load));
				}
				break;
			case open_store_id:
				if (msg.obj != null) {
					if (msg.obj.equals("1")) {
						CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.commit_success));
					} else {
						CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.upload_fail));
					}
				} else {
					CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.commit_fail));
				}
				mdialog.dismiss();
				break;
			case open_store_err:
				CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.act_net_error));
				mdialog.dismiss();
				break;
			case open_store_start:
//				mdialog.setMessage(getI18n(R.string.uploading));
				mdialog.dismiss();
				break;
			}
		};
	};

	private ArrayAdapter<String> storeLevel(List<GradeList> grade_lists) {
		List<String> strings = new ArrayList<String>();
		for (GradeList string : grade_lists) {
			strings.add(string.sg_name);
		}
		return ArrayAdapter(strings);
	}

	private ArrayAdapter<String> storeCg(List<StoreClass> store_class) {
		List<String> strings = new ArrayList<String>();
		for (StoreClass string : store_class) {
			strings.add(string.sc_name);
		}
		return ArrayAdapter(strings);
	}

	private ArrayAdapter<String> regionList(List<String[]> list) {
		List<String> strings = new ArrayList<String>();
		for (String[] string : list) {
			strings.add(string[1]);
		}
		return ArrayAdapter(strings);
	}

	private ArrayAdapter<String> ArrayAdapter(List<String> strings) {
		ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(OpenStoreActivity.this, android.R.layout.simple_spinner_item, strings);
		_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return _Adapter;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.open_store);
		netRun = new NetRun(this, handler);
		findViewById();
		initView();
		initData();
	}

	@Override
	public void ReGetData() {
	}

	@Override
	protected void findViewById() {
		et_store_name = (EditTextWithDel) findViewById(R.id.et_store_name);
		sp_province = (Spinner) findViewById(R.id.sp_province);
		sp_city = (Spinner) findViewById(R.id.sp_city);
		sp_district = (Spinner) findViewById(R.id.sp_district);
		et_detailed_address = (EditTextWithDel) findViewById(R.id.et_detailed_address);
		et_contact_name = (EditTextWithDel) findViewById(R.id.et_contact_name);
		et_contact_phone = (EditTextWithDel) findViewById(R.id.et_contact_phone);
		et_email = (EditTextWithDel) findViewById(R.id.et_email);
		et_name = (EditTextWithDel) findViewById(R.id.et_name);
		et_id_number = (EditTextWithDel) findViewById(R.id.et_id_number);
		iv_id_card_scanning = (ImageView) findViewById(R.id.iv_id_card_scanning);
		et_pay_name = (EditTextWithDel) findViewById(R.id.et_pay_name);
		et_pay_account = (EditTextWithDel) findViewById(R.id.et_pay_account);
		et_merchant_account = (EditTextWithDel) findViewById(R.id.et_merchant_account);
		et_company_name = (EditTextWithDel) findViewById(R.id.et_company_name);
		sp_store_level = (Spinner) findViewById(R.id.sp_store_level);
		sp_store_cg = (Spinner) findViewById(R.id.sp_store_category);
		sp_one_category = (Spinner) findViewById(R.id.sp_one_category);
		sp_two_category = (Spinner) findViewById(R.id.sp_two_category);
		sp_three_category = (Spinner) findViewById(R.id.sp_three_category);
		cusiness_category = (MyListView) findViewById(R.id.cusiness_category);
		iv_back = (ImageView) findViewById(R.id._iv_back);
		tv_title_name = (TextView) findViewById(R.id._tv_name);
		bt_add = (Button) findViewById(R.id.bt_add);
		cb_look = (CheckBox) findViewById(R.id.cb_look);
		tv_look = (TextView) findViewById(R.id.tv_look);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		tv_title_name.setText(getI18n(R.string.individual_settled));

		et_store_name.setText(getI18n(R.string.new_small));
		et_detailed_address.setText(getI18n(R.string.wh));
		et_contact_name.setText(getI18n(R.string.new_small));
		et_contact_phone.setText("13207215521");
		et_email.setText("625954232@qq.com");
		et_name.setText(getI18n(R.string.new_small));
		et_id_number.setText("420505199008310010");
		et_pay_name.setText(getI18n(R.string.new_small));
		et_pay_account.setText("13207215521");
		et_merchant_account.setText("13207215521");
		et_company_name.setText("13207215521");
	}

	@Override
	protected void initView() {
		manageCategoryLists = new ArrayList<ManageCategoryList>();
		sp_province.setOnItemSelectedListener(this);
		sp_city.setOnItemSelectedListener(this);
		sp_district.setOnItemSelectedListener(this);
		sp_store_level.setOnItemSelectedListener(this);
		sp_store_cg.setOnItemSelectedListener(this);
		sp_one_category.setOnItemSelectedListener(this);
		sp_two_category.setOnItemSelectedListener(this);
		sp_three_category.setOnItemSelectedListener(this);
		iv_id_card_scanning.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		bt_add.setOnClickListener(this);
		tv_look.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		sp_city.setVisibility(View.GONE);
		sp_district.setVisibility(View.GONE);
		bt_add.setVisibility(View.GONE);
		businessCategoryAdapter = new BusinessCategoryAdapter(this);
		cusiness_category.setAdapter(businessCategoryAdapter);
	}

	@SuppressWarnings("unchecked")
	private void submitData() {
		String store_name = et_store_name.getText().toString();
		String detailed_address = et_detailed_address.getText().toString();
		String contact_name = et_contact_name.getText().toString();
		String contact_phone = et_contact_phone.getText().toString();
		String email = et_email.getText().toString();
		String name = et_name.getText().toString();
		String id_number = et_id_number.getText().toString();
		String pay_name = et_pay_name.getText().toString();
		String pay_account = et_pay_account.getText().toString();
		String merchant_account = et_merchant_account.getText().toString();
		String company_name = et_company_name.getText().toString();
		if (store_name.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.shop_name_empty));
			return;
		}
		if (province == null) {
			CommonTools.showShortToast(this, getI18n(R.string.province_empty));
			return;
		}
		if (city == null) {
			CommonTools.showShortToast(this, getI18n(R.string.area_empty));
			return;
		}
		if (district == null) {
			CommonTools.showShortToast(this, getI18n(R.string.downtown_empty));
			return;
		}
		if (detailed_address.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.detail_is_empty));
			return;
		}
		if (contact_name.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.contact_is_empty));
			return;
		}
		if (contact_phone.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.phone_is_empty));
			return;
		}
		if (email.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.email_is_empty));
			return;
		}
		if (name.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.name_is_empty));
			return;
		}
		if (id_number.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.id_is_empty));
			return;
		}
		if (id_card_scanning == null) {
			CommonTools.showShortToast(this, getI18n(R.string.select_id_scan));
			return;
		}
		if (pay_name.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.alipay_name_is_empty));
			return;
		}
		if (pay_account.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.account_number_is_empty));
			return;
		}
		if (company_name.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.merchant_name_is_empty));
			return;
		}
		if (merchant_account.equals("")) {
			CommonTools.showShortToast(this, getI18n(R.string.merchant_account_is_empty));
			return;
		}
		if (sg_id == null) {
			CommonTools.showShortToast(this, getI18n(R.string.shop_level_is_empty));
			return;
		}
		if (sc_id == null) {
			CommonTools.showShortToast(this, getI18n(R.string.shop_category_is_empty));
			return;
		}
		if (one_category_id == null) {
			CommonTools.showShortToast(this, getI18n(R.string.select_shop_level_1));
			return;
		}
		if (two_category_id == null) {
			CommonTools.showShortToast(this, getI18n(R.string.select_shop_level_2));
			return;
		}
		if (three_category_id == null) {
			CommonTools.showShortToast(this, getI18n(R.string.select_shop_level_3));
			return;
		}
		String store_class_ids = storeClass().get("class_ds");
		String store_class_names = storeClass().get("class_names");
		netRun.personaLopenShop(company_name, merchant_account, store_name, sg_id, sg_name, sc_id, sc_name, store_class_ids.toString(), store_class_names.toString(), province, city, district, detailed_address, company_points, contact_phone, contact_name, email, name, id_card_scanning, id_number, pay_name, pay_account);
	}

	/**
	 * 整合分类ID和分类名称
	 * 
	 * @return
	 */
	private Map<String, String> storeClass() {
		String class_ids = "";
		String class_names = "";
		for (int i = 0; i < manageCategoryLists.size(); i++) {
			ManageCategoryList list = manageCategoryLists.get(i);
			if (i == 0) {
				class_ids = class_ids + list.one_category_id + "," + list.two_category_id + "," + list.three_category_id;
			} else {
				class_ids = class_ids + "|" + list.one_category_id + "," + list.two_category_id + "," + list.three_category_id;
			}
			if (i == 0) {
				class_names = class_names + list.one_category + "," + list.two_category + "," + list.three_category;
			} else {
				class_names = class_names + "|" + list.one_category + "," + list.two_category + "," + list.three_category;
			}

		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("class_ds", class_ids);
		map.put("class_names", class_names);
		return map;
	}

	@Override
	protected void initData() {
		company_points = appSingleton.mlocation.getLatitude() + "," + appSingleton.mlocation.getLongitude();
		netRun.getSregionList(null);
		netRun.getStoreCategory();
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.bt_add){
			if (one_category == null) {
				CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.select_shop_level_1));
				return;
			}
			if (two_category == null) {
				CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.select_shop_level_2));
				return;
			}
			if (three_category == null) {
				CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.select_shop_level_3));
				return;
			}
			ManageCategoryList list = new ManageCategoryList(one_category, one_category_id, two_category, two_category_id, three_category, three_category_id);
			manageCategoryLists.add(list);
			businessCategoryAdapter.setCategoryLists(manageCategoryLists);
			businessCategoryAdapter.notifyDataSetChanged();
			bt_add.setVisibility(View.GONE);
		}else if(v.getId()==R.id.bt_submit){
			submitData();
		}else if(v.getId()==R.id._iv_back){
			finish();
			overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		}else if(v.getId()==R.id.iv_id_card_scanning){
			editAvatar();
		}

//		switch (v.getId()) {
//		case R.id.bt_add:
//			if (one_category == null) {
//				CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.select_shop_level_1));
//				return;
//			}
//			if (two_category == null) {
//				CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.select_shop_level_2));
//				return;
//			}
//			if (three_category == null) {
//				CommonTools.showShortToast(OpenStoreActivity.this, getI18n(R.string.select_shop_level_3));
//				return;
//			}
//			ManageCategoryList list = new ManageCategoryList(one_category, one_category_id, two_category, two_category_id, three_category, three_category_id);
//			manageCategoryLists.add(list);
//			businessCategoryAdapter.setCategoryLists(manageCategoryLists);
//			businessCategoryAdapter.notifyDataSetChanged();
//			bt_add.setVisibility(View.GONE);
//			break;
//		case R.id.tv_look:
//
//			break;
//		case R.id.bt_submit:
//			submitData();
//			break;
//		case R.id._iv_back:
//			finish();
//			overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
//			break;
//		case R.id.iv_id_card_scanning:
//			editAvatar();
//			break;
//		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if(parent.getId()==R.id.sp_province){
			get_region_p = 1;
			province = provinceList.get(position)[0];
			netRun.getSregionList(province);
		}else if(parent.getId()==R.id.sp_city){
			get_region_p = 2;
			city = cityList.get(position)[0];
			netRun.getSregionList(city);
		}else if(parent.getId()== R.id.sp_district){
			get_region_p = 0;
			district = districtList.get(position)[0];
		}else if(parent.getId()== R.id.sp_store_level){
			sg_id = storeLists.grade_lists.get(position).sg_id;
			sg_name = storeLists.grade_lists.get(position).sg_name;
		}else if(parent.getId()==R.id.sp_store_category){
			sc_id = storeLists.store_class.get(position).sc_id;
			sc_name = storeLists.store_class.get(position).sc_name;
		}else if(parent.getId()==R.id.sp_one_category){
			gc_list_p = 1;
			one_category_id = gcLists.get(position).gc_id;
			one_category = gcLists.get(position).gc_name;
			netRun.getGcList(one_category_id);
		}else if(parent.getId()==R.id.sp_two_category){
			gc_list_p = 2;
			two_category_id = gcLists2.get(position).gc_id;
			two_category = gcLists2.get(position).gc_name;
			netRun.getGcList(two_category_id);
		}else if(parent.getId()==R.id.sp_three_category){
			gc_list_p = 0;
			three_category_id = gcLists3.get(position).gc_id;
			three_category = gcLists3.get(position).gc_name;
			bt_add.setVisibility(View.VISIBLE);
		}

//		switch (parent.getId()) {
//		case R.id.sp_province:
//			get_region_p = 1;
//			province = provinceList.get(position)[0];
//			netRun.getSregionList(province);
//			break;
//		case R.id.sp_city:
//			get_region_p = 2;
//			city = cityList.get(position)[0];
//			netRun.getSregionList(city);
//			break;
//		case R.id.sp_district:
//			get_region_p = 0;
//			district = districtList.get(position)[0];
//			break;
//		case R.id.sp_store_level:
//			sg_id = storeLists.grade_lists.get(position).sg_id;
//			sg_name = storeLists.grade_lists.get(position).sg_name;
//			break;
//		case R.id.sp_store_category:
//			sc_id = storeLists.store_class.get(position).sc_id;
//			sc_name = storeLists.store_class.get(position).sc_name;
//			break;
//		case R.id.sp_one_category:
//			gc_list_p = 1;
//			one_category_id = gcLists.get(position).gc_id;
//			one_category = gcLists.get(position).gc_name;
//			netRun.getGcList(one_category_id);
//			break;
//		case R.id.sp_two_category:
//			gc_list_p = 2;
//			two_category_id = gcLists2.get(position).gc_id;
//			two_category = gcLists2.get(position).gc_name;
//			netRun.getGcList(two_category_id);
//			break;
//		case R.id.sp_three_category:
//			gc_list_p = 0;
//			three_category_id = gcLists3.get(position).gc_id;
//			three_category = gcLists3.get(position).gc_name;
//			bt_add.setVisibility(View.VISIBLE);
//			break;
//		}
	}

	private ArrayAdapter<String> businessAdapter(List<GcList> gc_list) {
		List<String> strings = new ArrayList<String>();
		for (GcList string : gc_list) {
			strings.add(string.gc_name);
		}
		ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(OpenStoreActivity.this, android.R.layout.simple_spinner_item, strings);
		_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return _Adapter;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		sp_province.setSelection(0);
		sp_city.setSelection(0);
		sp_district.setSelection(0);
	}

	/**
	 * 选择图片数据源
	 */
	private void editAvatar() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getI18n(R.string.select_picture_source));
		String[] items = new String[] { getI18n(R.string.media_lib), getI18n(R.string.take_picture) };
		dialog.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = null;
				switch (which) {
				case 0:
					intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(intent, 0);
					break;
				case 1:
					intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, 1);
					break;
				}
			}
		});
		dialog.setNegativeButton(getI18n(R.string.cancel), null);
		dialog.create().show();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				Uri originalUri = data.getData();
				ContentResolver resolver = getContentResolver();
				try {
					Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					iv_id_card_scanning.setImageBitmap(bm);
					String[] proj = { MediaStore.Images.Media.DATA };
					Cursor cursor = managedQuery(originalUri, proj, null, null, null);
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					id_card_scanning = cursor.getString(column_index);

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (requestCode == 1) {
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");
				saveMyBitmap(bitmap);
				iv_id_card_scanning.setImageBitmap(bitmap);
			}
		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * 裁剪图片
	 * 
	 * @param mBitmap
	 */
	public void saveMyBitmap(Bitmap mBitmap) {
		id_card_scanning = Environment.getExternalStorageDirectory().getAbsolutePath();
		if (id_card_scanning == null) {
			id_card_scanning = Environment.getDataDirectory().getAbsolutePath();
		}
		File f = new File(id_card_scanning + "/DCIM/aite.png");
		FileOutputStream fOut = null;
		try {
			if (f.exists())
				f.delete();
			f.createNewFile();
			fOut = new FileOutputStream(f);
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
