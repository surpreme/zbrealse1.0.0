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
public class JD_Personal3Adapter extends RecyclerView.Adapter<PersonalHolder3> {

	private Context mactivity;

	private String txtlist[] = new String[] { "商品关注", "店铺关注", "浏览记录", "客户服务",
			"我的活动", "社区", "艾特超市", "排行榜" };
	private String txtlist2[] = new String[] { "2", "0", "100", "", "", "", "",
			"", "", null };
	private int[] navigationimage = new int[] { 0, 0, 0, R.drawable.ico1,
			R.drawable.ico2, R.drawable.ico3, R.drawable.ico4, R.drawable.ico5,
			R.drawable.ico6, R.drawable.ico7 };

	public JD_Personal3Adapter(Context mactivity) {
		this.mactivity = mactivity;
	}

	@Override
	public int getItemCount() {
		return txtlist.length;
	}

	@Override
	public void onBindViewHolder(PersonalHolder3 arg0, int arg1) {
		arg0.tv_name.setText(txtlist[arg1]);
		if (txtlist2[arg1] != null && !txtlist2[arg1].equals("")) {
			arg0.tv_number.setVisibility(View.VISIBLE);
			arg0.iv_img.setVisibility(View.GONE);
			arg0.tv_number.setText(txtlist2[arg1]);
			arg0.tv_number.setTextColor(0xffF92C5B);
		} else {
			arg0.tv_number.setVisibility(View.GONE);
			arg0.iv_img.setVisibility(View.VISIBLE);
			if (navigationimage[arg1] != 0) {
				arg0.iv_img.setImageResource(navigationimage[arg1]);
			}
		}

	}

	@Override
	public PersonalHolder3 onCreateViewHolder(ViewGroup parent, int arg1) {
		PersonalHolder3 holder = new PersonalHolder3(LayoutInflater.from(
				mactivity).inflate(R.layout.item_jd_personal3, parent, false));
		return holder;
	}
}

class PersonalHolder3 extends RecyclerView.ViewHolder {
	TextView tv_name, tv_number;
	ImageView iv_img;

	public PersonalHolder3(View itemView) {
		super(itemView);
		tv_name = (TextView) itemView.findViewById(R.id.tv_name);
		tv_number = (TextView) itemView.findViewById(R.id.tv_number);
		iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
	}
}
