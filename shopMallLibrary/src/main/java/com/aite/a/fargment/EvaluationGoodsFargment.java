package com.aite.a.fargment;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.aite.a.base.BaseFargment;
import com.aite.a.model.GoodsDetailsInfo.GoodsEvaluateList;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;

public class EvaluationGoodsFargment extends BaseFargment {
	public View view;
	private Adapter1 adapter1;
	private ListView listView;
	private BitmapUtils bitmapUtils;
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Evaluation_list:
				if (msg.obj != null)
					try {
//						requestData((List<GoodsEvaluateList>) msg.obj);
						adapter1=new Adapter1((List<GoodsEvaluateList>) msg.obj);
						listView.setAdapter(adapter1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				break;

			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.goods_evaluation, container, false);
		listView = (ListView) view
				.findViewById(R.id.goods_evaluation_list);
		bitmapUtils=new BitmapUtils(getActivity());
//		requestData();
		return view;
	}

	protected void requestData(List<GoodsEvaluateList> evaluationModels)
			throws Exception {
		
//		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
//		for (GoodsEvaluateList evaluation : evaluationModels) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("geval_rname", evaluation.geval_frommembername);
//			map.put("geval_addtime", evaluation.geval_addtime);
//			map.put("geval_scores", evaluation.geval_scores);
//			map.put("geval_content", evaluation.geval_content);
//			data.add(map);
//		}
//		String[] from = new String[] { "geval_rname", "geval_addtime",
//				"geval_scores", "geval_content" };
//		int[] to = new int[] { R.id.name, R.id.data, R.id.small_ratingbar,
//				R.id.evaluation_tv_details };
//		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), data,
//				R.layout.evaluation_item, from, to);
//		simpleAdapter.setViewBinder(new ViewBinder() {
//			public boolean setViewValue(View view, Object data,
//					String textRepresentation) {
//				if (view instanceof ImageView && data instanceof String) {
//					ImageView iv = (ImageView) view;
//					setImageView(iv, data.toString());
//					return true;
//				}
//				return false;
//			}
//		});
//		listView.setAdapter(simpleAdapter);
	}

	protected void setImageView(ImageView iv, String data) {
		switch (Integer.valueOf(data)) {
		case 1:
			iv.setBackground(getResources().getDrawable(R.drawable.d_xing1));
			break;
		case 2:
			iv.setBackground(getResources().getDrawable(R.drawable.d_xing2));
			break;
		case 3:
			iv.setBackground(getResources().getDrawable(R.drawable.d_xing3));
			break;
		case 4:
			iv.setBackground(getResources().getDrawable(R.drawable.d_xing4));
			break;
		case 5:
			iv.setBackground(getResources().getDrawable(R.drawable.d_xing5));
			break;

		}
	}
	/**
	 * 一级
	 * @author Administrator
	 *
	 */
	private class Adapter1 extends BaseAdapter {
		List<GoodsEvaluateList> evaluationModels;
		public Adapter1(List<GoodsEvaluateList> evaluationModels) {
			this.evaluationModels=evaluationModels;
		}
		@Override
		public int getCount() {
			return evaluationModels.size();
		}

		@Override
		public Object getItem(int position) {
			return evaluationModels==null?null:evaluationModels.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.item_evaluation, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			GoodsEvaluateList goodsEvaluateList = evaluationModels.get(position);
			holder.name.setText(goodsEvaluateList.geval_frommembername);
			holder.data.setText(goodsEvaluateList.geval_addtime);
			holder.evaluation_tv_details.setText(goodsEvaluateList.geval_content);
			setImageView(holder.small_ratingbar, goodsEvaluateList.geval_scores.toString());
			if (goodsEvaluateList.geval_image!=null&&goodsEvaluateList.geval_image.size()!=0) {
				SdAdapter sdAdapter = new SdAdapter(goodsEvaluateList.geval_image);
				holder.mv_img.setAdapter(sdAdapter);
			}
			
			return convertView;
		}

		class ViewHolder {
			ImageView small_ratingbar;
			TextView name, data, evaluation_tv_details;
			MyGridView mv_img;

			public ViewHolder(View convertView) {
				small_ratingbar = (ImageView) convertView
						.findViewById(R.id.small_ratingbar);
				name = (TextView) convertView.findViewById(R.id.name);
				data = (TextView) convertView.findViewById(R.id.data);
				evaluation_tv_details = (TextView) convertView
						.findViewById(R.id.evaluation_tv_details);
				mv_img = (MyGridView) convertView.findViewById(R.id.mv_img);
				convertView.setTag(this);
			}
		}
	}
	
	/**
	 * 晒单适配
	 * 
	 * @author Administrator
	 *
	 */
	private class SdAdapter extends BaseAdapter {
		List<String> geval_image;

		public SdAdapter(List<String> geval_image) {
			this.geval_image = geval_image;
		}

		@Override
		public int getCount() {
			return geval_image.size();
		}

		@Override
		public Object getItem(int position) {
			return geval_image == null ? null : geval_image.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.sd_item,
						null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			bitmapUtils.display(holder.iv_img, geval_image.get(position));
			return convertView;
		}

		class ViewHolder {
			ImageView iv_img;

			public ViewHolder(View convertView) {
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				convertView.setTag(this);
			}
		}
	}

}
