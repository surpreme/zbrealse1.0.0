package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.NewsinfoActivity;
import com.aite.a.model.NewslistifyInfo.news_list;
import com.aiteshangcheng.a.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 仿京东快报适配
 * 
 * @author Administrator
 *
 */
public class JD_LettersAdapter extends RecyclerView.Adapter<ViewHolder1> {

	private Context mactivity;
	private List<news_list> news_list;

	// private String txtlist[] = new String[] { "新", "HOT", "荐","新"};
	// private String txtlist2[] = new String[] { "瑞士维氏军刀 新品满200-50",
	// "家居家装焕新季，讲199减100！", "带上相机去春游，尼康低至477","瑞士维氏军刀 新品满200-50"};

	public JD_LettersAdapter(Context mactivity, List<news_list> news_list) {
		this.mactivity = mactivity;
		this.news_list = news_list;
	}

	@Override
	public int getItemCount() {
		return news_list==null?0:news_list.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder1 arg0, final int arg1) {
		arg0.tv_txt2.setText(news_list.get(arg1).article_title);
		arg0.ll_item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 详情
				Intent intent = new Intent(mactivity, NewsinfoActivity.class);
				intent.putExtra("url", news_list.get(arg1).article_id);
				mactivity.startActivity(intent);
			}
		});
	}

	@Override
	public ViewHolder1 onCreateViewHolder(ViewGroup parent, int arg1) {
		ViewHolder1 holder = new ViewHolder1(LayoutInflater.from(mactivity)
				.inflate(R.layout.item_jd_letters, parent, false));
		return holder;
	}
}

class ViewHolder1 extends RecyclerView.ViewHolder {
	TextView tv_txt1, tv_txt2;
	LinearLayout ll_item;

	public ViewHolder1(View itemView) {
		super(itemView);
		tv_txt1 = (TextView) itemView.findViewById(R.id.tv_txt1);
		tv_txt2 = (TextView) itemView.findViewById(R.id.tv_txt2);
		ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
	}
}
