package com.aite.a.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 仿京东快报适配
 * 
 * @author Administrator
 *
 */
public class JD_Personal1Adapter extends RecyclerView.Adapter<PersonalHolder1> {

	private Context mactivity;

	private String txtlist[] = new String[] { "待付款", "待收货", "待评价", "退换/售后",
			"我的订单", "艾豆", "优惠券", "白条", "艾特小金库", "我的钱包" };
	private String txtlist2[] = new String[] { "", "", "", "", "", "147", "9",
			"0.00", "0.00", null };
	private int[] navigationimage = new int[] { R.drawable.daizhifufcf,
			R.drawable.daifahuofcf, R.drawable.daishouhuofcf,
			R.drawable.dingdanfcf, R.drawable.jiaoyiwangcengfcf, 0, 0, 0, 0,
			R.drawable.jiaoyiwangcengfcf };

	public JD_Personal1Adapter(Context mactivity) {
		this.mactivity = mactivity;
	}

	@Override
	public int getItemCount() {
		return txtlist.length;
	}

	@Override
	public void onBindViewHolder(PersonalHolder1 arg0, int arg1) {
		arg0.tv_name.setText(txtlist[arg1]);
		if (txtlist2[arg1] != null && !txtlist2[arg1].equals("")) {
			arg0.tv_number.setVisibility(View.VISIBLE);
			arg0.iv_img.setVisibility(View.GONE);
			arg0.tv_number.setText(txtlist2[arg1]);
		} else {
			arg0.tv_number.setVisibility(View.GONE);
			arg0.iv_img.setVisibility(View.VISIBLE);
			if (navigationimage[arg1]!=0) {
				arg0.iv_img.setImageResource(navigationimage[arg1]);
			}
		}

	}

	@Override
	public PersonalHolder1 onCreateViewHolder(ViewGroup parent, int arg1) {
		PersonalHolder1 holder = new PersonalHolder1(LayoutInflater.from(
				mactivity).inflate(R.layout.item_jd_personal1, parent, false));
		return holder;
	}
}

class PersonalHolder1 extends RecyclerView.ViewHolder {
	TextView tv_name, tv_number;
	ImageView iv_img;

	public PersonalHolder1(View itemView) {
		super(itemView);
		tv_name = (TextView) itemView.findViewById(R.id.tv_name);
		tv_number = (TextView) itemView.findViewById(R.id.tv_number);
		iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
	}
}
