package com.aite.a.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 仿京东快报适配
 * 
 * @author Administrator
 *
 */
public class JD_Level1Adapter extends RecyclerView.Adapter<Level1Holder> {

	private Context mactivity;
	private String txtlist[] = new String[] { "推荐分类", "艾特超市", "全球购", "男装",
			"女装", "男鞋", "女鞋", "内衣配饰", "箱包手袋" };

	public JD_Level1Adapter(Context mactivity) {
		this.mactivity = mactivity;
	}

	@Override
	public int getItemCount() {
		return txtlist.length;
	}

	@Override
	public void onBindViewHolder(Level1Holder arg0, int arg1) {
		arg0.tv_name.setText(txtlist[arg1]);
	}

	@Override
	public Level1Holder onCreateViewHolder(ViewGroup parent, int arg1) {
		Level1Holder holder = new Level1Holder(LayoutInflater.from(mactivity)
				.inflate(R.layout.item_jdclassifylevel1, parent, false));
		return holder;
	}
}

class Level1Holder extends RecyclerView.ViewHolder {
	TextView tv_name;

	public Level1Holder(View itemView) {
		super(itemView);
		tv_name = (TextView) itemView.findViewById(R.id.tv_name);
	}
}
