package com.aite.a.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 仿京东个人中心菜单二
 * 
 * @author Administrator
 *
 */
public class JD_Personal2Adapter extends RecyclerView.Adapter<Personal2Holder> {

	private Context mactivity;
	private int imglist[] = new int[] { R.drawable.ico1, R.drawable.ico2,
			R.drawable.ico3, R.drawable.ico4 };
	private String txtlist[] = new String[] { "618主会场", "品牌狂欢城", "必买清单",
			"我的618" };

	public JD_Personal2Adapter(Context mactivity) {
		this.mactivity = mactivity;
	}

	@Override
	public int getItemCount() {
		return imglist.length;
	}

	@Override
	public void onBindViewHolder(Personal2Holder arg0, final int arg1) {
		arg0.iv_img.setImageResource(imglist[arg1]);
		arg0.tv_name.setText(txtlist[arg1]);
	}

	@Override
	public Personal2Holder onCreateViewHolder(ViewGroup parent, int arg1) {
		Personal2Holder holder = new Personal2Holder(LayoutInflater.from(
				mactivity).inflate(R.layout.item_jdpersonal2, parent, false));
		return holder;
	}

}

class Personal2Holder extends RecyclerView.ViewHolder {
	ImageView iv_img;
	TextView tv_name;
	LinearLayout ll_item;

	public Personal2Holder(View itemView) {
		super(itemView);
		iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
		tv_name = (TextView) itemView.findViewById(R.id.tv_name);
		ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
	}

}
