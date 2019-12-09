package com.aite.a.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
/**
 * 论坛详情
 * @author Administrator
 *
 */
public class CommentsDetailsActivity extends BaseActivity implements OnClickListener{

	private TextView tv_bbsdata_title,tv_bbsdata_username,tv_bbsdata_time,tv_bbsdata_content,tv_bbsdata_all,tv_bbsdata_praisenum,tv_bbsdata_commentsnum;
	private ImageView iv_bbsdata_imgtitle;
	private MyListView mlv_bbsdata_img,mlv_bbsdata_comments;
	private EditText et_bbsdata_inputcen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_essaydetails);
		findViewById();
	}
	
	@Override
	public void ReGetData() {
		

	}

	@Override
	protected void findViewById() {
		tv_bbsdata_title=(TextView) findViewById(R.id.tv_bbsdata_title);
		tv_bbsdata_username=(TextView) findViewById(R.id.tv_bbsdata_username);
		tv_bbsdata_time=(TextView) findViewById(R.id.tv_bbsdata_time);
		tv_bbsdata_content=(TextView) findViewById(R.id.tv_bbsdata_content);
		tv_bbsdata_all=(TextView) findViewById(R.id.tv_bbsdata_all);
		tv_bbsdata_praisenum=(TextView) findViewById(R.id.tv_bbsdata_praisenum);
		tv_bbsdata_commentsnum=(TextView) findViewById(R.id.tv_bbsdata_commentsnum);
		iv_bbsdata_imgtitle=(ImageView) findViewById(R.id.iv_bbsdata_imgtitle);
		mlv_bbsdata_img=(MyListView) findViewById(R.id.mlv_bbsdata_img);
		mlv_bbsdata_comments=(MyListView) findViewById(R.id.mlv_bbsdata_comments);
		et_bbsdata_inputcen=(EditText) findViewById(R.id.et_bbsdata_inputcen);
		initView();
	}

	@Override
	protected void initView() {
		tv_bbsdata_all.setOnClickListener(this);
		tv_bbsdata_praisenum.setOnClickListener(this);
		tv_bbsdata_commentsnum.setOnClickListener(this);
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onClick(View v) {

	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_bbsdata_all:
//
//			break;
//		case R.id.tv_bbsdata_praisenum:
//
//			break;
//		case R.id.tv_bbsdata_commentsnum:
//
//			break;
//		}
//	}

}
