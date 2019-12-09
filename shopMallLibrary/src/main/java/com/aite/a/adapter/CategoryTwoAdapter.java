package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.model.CategoryList;
import com.lidroid.xutils.BitmapUtils;

public class CategoryTwoAdapter extends BaseAdapter {
	private Context ctx;
	private List<CategoryList> list;
	private int position = 0;
	private int layout = R.layout.search_more_morelist_item;
	private BitmapUtils bitmapUtils;

	public CategoryTwoAdapter(Context ctx) {
		this.ctx = ctx;
		list = new ArrayList<CategoryList>();
		bitmapUtils = new BitmapUtils(ctx);
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder hold;
		if (arg1 == null) {
			hold = new Holder();
			arg1 = View.inflate(ctx, layout, null);
			hold.img = (ImageView) arg1.findViewById(R.id.iv_category_img);
			hold.txt = (TextView) arg1.findViewById(R.id.Search_more_moreitem_txt);
			hold.layout = (LinearLayout) arg1.findViewById(R.id.More_list_lishi);
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}

		bitmapUtils.display(hold.img, list.get(arg0).getGc_image());
		LayoutParams layoutParams = hold.img.getLayoutParams();
		layoutParams.height=getScreenWidth(ctx)/4;
		hold.img.setLayoutParams(layoutParams);
		hold.txt.setText(list.get(arg0).getGc_name());
//		hold.layout.setBackgroundResource(R.drawable.my_list_txt_background);
//		hold.txt.setTextColor(Color.parseColor("#FF666666"));
		if (arg0 == position) {
//			hold.layout.setBackgroundResource(R.drawable.search_more_morelisttop_bkg);
//			hold.txt.setTextColor(Color.parseColor("#FFFF8C00"));
		}
		return arg1;
	}

	public void setSelectItem(int i) {
		position = i;
	}

	private static class Holder {
		public ImageView img;
		public LinearLayout layout;
		public TextView txt;
	}

	public void setList(List<CategoryList> list) {
		this.list = list;
	}
    //获取屏幕的宽度  
   public static int getScreenWidth(Context context) {  
       WindowManager manager = (WindowManager) context  
               .getSystemService(Context.WINDOW_SERVICE);  
       Display display = manager.getDefaultDisplay();  
       return display.getWidth();  
   } 
}
