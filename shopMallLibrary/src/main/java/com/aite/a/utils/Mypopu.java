package com.aite.a.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

public class Mypopu extends PopupWindow implements OnClickListener {
	private Activity mActivity = null;
	private TextView tv_pswfinhui, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7,
			tv_8, tv_9, tv_0;

	private ImageView iv_dian1, iv_dian2, iv_dian3, iv_dian4, iv_dian5,
			iv_dian6, iv_deleteb;

	public Mypopu(Activity activity) {
		mActivity = activity;
		// 设置SelectPicPopupWindow弹出窗体的宽
		setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		setHeight(LayoutParams.WRAP_CONTENT);
		View view = View.inflate(mActivity, R.layout.pswpopu, null);
		tv_pswfinhui = (TextView) view.findViewById(R.id.tv_pswfinhui);
		tv_1 = (TextView) view.findViewById(R.id.tv_1);
		tv_2 = (TextView) view.findViewById(R.id.tv_2);
		tv_3 = (TextView) view.findViewById(R.id.tv_3);
		tv_4 = (TextView) view.findViewById(R.id.tv_4);
		tv_5 = (TextView) view.findViewById(R.id.tv_5);
		tv_6 = (TextView) view.findViewById(R.id.tv_6);
		tv_7 = (TextView) view.findViewById(R.id.tv_7);
		tv_8 = (TextView) view.findViewById(R.id.tv_8);
		tv_9 = (TextView) view.findViewById(R.id.tv_9);
		tv_0 = (TextView) view.findViewById(R.id.tv_0);
		iv_dian1 = (ImageView) view.findViewById(R.id.iv_dian1);
		iv_dian2 = (ImageView) view.findViewById(R.id.iv_dian2);
		iv_dian3 = (ImageView) view.findViewById(R.id.iv_dian3);
		iv_dian4 = (ImageView) view.findViewById(R.id.iv_dian4);
		iv_dian5 = (ImageView) view.findViewById(R.id.iv_dian5);
		iv_dian6 = (ImageView) view.findViewById(R.id.iv_dian6);
		iv_deleteb = (ImageView) view.findViewById(R.id.iv_deleteb);
		tv_1.setOnClickListener(this);
		tv_2.setOnClickListener(this);
		tv_3.setOnClickListener(this);
		tv_4.setOnClickListener(this);
		tv_5.setOnClickListener(this);
		tv_6.setOnClickListener(this);
		tv_7.setOnClickListener(this);
		tv_8.setOnClickListener(this);
		tv_9.setOnClickListener(this);
		tv_0.setOnClickListener(this);
		tv_pswfinhui.setOnClickListener(this);
		iv_deleteb.setOnClickListener(this);
		list.add(iv_dian1);
		list.add(iv_dian2);
		list.add(iv_dian3);
		list.add(iv_dian4);
		list.add(iv_dian5);
		list.add(iv_dian6);
		// 设置SelectPicPopupWindow的View
		setContentView(view);
		// 设置点击视图之外的地方是否取消当前的PopupWindow
		setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);

		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				android.view.WindowManager.LayoutParams lp = mActivity
						.getWindow().getAttributes();
				lp.alpha = 1f;
				mActivity.getWindow().setAttributes(lp);
			}
		});
		// 设置PopupWindow弹出窗体动画效果
		setAnimationStyle(R.style.AnimBottomPopup);

	}

	Handler h = new Handler() {
		// 显示玩popup后，改变背景透明度
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				android.view.WindowManager.LayoutParams lp = mActivity
						.getWindow().getAttributes();
				lp.alpha = 0.8f;
				mActivity.getWindow().setAttributes(lp);
				break;
			case 1:
				dismiss();
				break;
			}
		};
	};

	private void showEvent() {
		h.sendEmptyMessageDelayed(0, 500);
	}

	@Override
	public void showAsDropDown(View anchor) {
		super.showAsDropDown(anchor);
		showEvent();
	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff) {
		super.showAsDropDown(anchor, xoff, yoff);
		showEvent();
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		showEvent();
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.tv_1){
			dian(1);
		}else if(v.getId()==R.id.tv_2){
			dian(2);
		}else if(v.getId()==R.id.tv_3){
			dian(3);
		}else if(v.getId()==R.id.tv_4){
			dian(4);
		}else if(v.getId()==R.id.tv_5){
			dian(5);
		}else if(v.getId()== R.id.tv_6){
			dian(6);
		}else if(v.getId()==R.id.tv_7){
			dian(7);
		}else if(v.getId()==R.id.tv_8){
			dian(8);
		}else if(v.getId()==R.id.tv_9){
			dian(9);
		}else if(v.getId()==R.id.tv_0){
			dian(0);
		}else if(v.getId()==R.id.tv_pswfinhui){
			dismiss();
			psww.clear();
		}else if(v.getId()==R.id.iv_deleteb){
			if (psww.size()>0) {
				psww.remove((psww.size() - 1));
				list.get(psww.size()).setVisibility(View.GONE);
			}
		}
//		switch (v.getId()) {
//		case R.id.tv_1:
//			dian(1);
//			break;
//		case R.id.tv_2:
//			dian(2);
//			break;
//		case R.id.tv_3:
//			dian(3);
//			break;
//		case R.id.tv_4:
//			dian(4);
//			break;
//		case R.id.tv_5:
//			dian(5);
//			break;
//		case R.id.tv_6:
//			dian(6);
//			break;
//		case R.id.tv_7:
//			dian(7);
//			break;
//		case R.id.tv_8:
//			dian(8);
//			break;
//		case R.id.tv_9:
//			dian(9);
//			break;
//		case R.id.tv_0:
//			dian(0);
//			break;
//		case R.id.tv_pswfinhui:
//			dismiss();
//			psww.clear();
//			break;
//		case R.id.iv_deleteb:
//			if (psww.size()>0) {
//				psww.remove((psww.size() - 1));
//				list.get(psww.size()).setVisibility(View.GONE);
//			}
//			break;
//		}
	}
	List<ImageView> list = new ArrayList<ImageView>();
	List<Integer> psww = new ArrayList<Integer>();
	StringBuffer sb=new StringBuffer();
	private void dian(int psw) {
		if (psww.size() < 6) {
			list.get(psww.size()).setVisibility(View.VISIBLE);
			psww.add(psw);
		}
		if (iv_dian6.isShown()) {
			for (int p : psww) {
				sb.append(p+"");
			}
			lingshi.getInstance().setPay(sb.toString());
			if (mhuidiao!=null){
				mhuidiao.onItemClick(sb.toString());
			}
			psww.clear();
			h.sendEmptyMessageDelayed(1, 300);
		}
	}

	private huidiao mhuidiao = null;

	public void sethuidiao(huidiao p) {
		mhuidiao = p;
	}

	public interface huidiao {
		void onItemClick(String psw);
	}
}
