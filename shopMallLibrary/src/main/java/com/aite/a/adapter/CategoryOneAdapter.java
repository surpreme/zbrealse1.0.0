package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.model.CategoryList;

public class CategoryOneAdapter extends BaseAdapter {

	private Context ctx;
	private List<CategoryList> list;
	private int position = 0;
	private int layout = R.layout.search_more_mainlist_item;

	public CategoryOneAdapter(Context ctx) {
		this.ctx = ctx;
		list = new ArrayList<CategoryList>();
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int arg0) {
		return arg0;
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public void setList(List<CategoryList> list) {
		this.list = list;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder hold;
		if (arg1 == null) {
			hold = new Holder();
			arg1 = View.inflate(ctx, layout, null);
			hold.txt = (TextView) arg1.findViewById(R.id.Search_more_mainitem_txt);
			hold.layout = (LinearLayout) arg1.findViewById(R.id.Search_more_mainitem_layout);
			LayoutParams layoutParams = hold.txt.getLayoutParams();
			layoutParams.height=(getScreenWidth(ctx)/4)/2;
			hold.txt.setLayoutParams(layoutParams);
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}
		if (list.get(arg0).getGc_show_in_menu().equals("1")) {
			hold.txt.setText(list.get(arg0).getGc_name());
		} else {
			list.remove(arg0);
		}
		hold.layout.setBackgroundResource(R.drawable.search_more_mainlistselect);
		if (arg0 == position) {
			hold.layout.setBackgroundResource(R.drawable.list_bkg_line_u);
		}
		return arg1;
	}

	public void setSelectItem(int i) {
		position = i;
	}

	public int getSelectItem() {
		return position;
	}

	private static class Holder {
		LinearLayout layout;
		// ImageView img;
		TextView txt;
	}
	  //获取屏幕的宽度  
	   public static int getScreenWidth(Context context) {  
	       WindowManager manager = (WindowManager) context  
	               .getSystemService(Context.WINDOW_SERVICE);  
	       Display display = manager.getDefaultDisplay();  
	       return display.getWidth();  
	   } 
}
