package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aite.a.model.GoodsEvaluateInfo;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.activity.ImagePreview;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品评价
 * Created by mayn on 2018/11/14.
 */
public class GoodsEvaluate2Adapter extends BaseAdapter {
    private Context mcontext;
    private List<GoodsEvaluateInfo.goodsevallist> goodsevallist;
    private int h = 0;//图片的高度

    public GoodsEvaluate2Adapter(Context mcontext, List<GoodsEvaluateInfo.goodsevallist> goodsevallist, int h) {
        this.mcontext = mcontext;
        this.goodsevallist = goodsevallist;
        this.h = h;
    }

    /**
     * 刷新
     *
     * @param goodsevallist
     */
    public void refreshData(List<GoodsEvaluateInfo.goodsevallist> goodsevallist) {
        if (goodsevallist == null) return;
        this.goodsevallist = goodsevallist;
        notifyDataSetChanged();
    }

    /**
     * 刷新
     *
     * @param goodsevallist
     */
    public void addData(List<GoodsEvaluateInfo.goodsevallist> goodsevallist) {
        if (goodsevallist == null) return;
        this.goodsevallist.addAll(goodsevallist);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return goodsevallist == null ? 0 : goodsevallist.size();
    }

    @Override
    public Object getItem(int i) {
        return goodsevallist == null ? null : goodsevallist.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_goodsevaluate, null);
            new GoodsEvaluateHolder(view);
        }
        GoodsEvaluateHolder holder = (GoodsEvaluateHolder) view.getTag();
        GoodsEvaluateInfo.goodsevallist goodsEvaluateList = this.goodsevallist.get(i);
        Glide.with(mcontext).load(goodsEvaluateList.geval_frommemberavatar).into(holder.iv_img);
        holder.tv_name.setText(goodsEvaluateList.geval_frommembername);
        holder.tv_centent.setText(goodsEvaluateList.geval_content);
        holder.Ratingbar.setRating(Float.parseFloat(goodsEvaluateList.geval_scores));
        ImgAdapter adapter = new ImgAdapter(goodsEvaluateList.geval_image);
        holder.gv_img.setAdapter(adapter);
        return view;
    }

    class GoodsEvaluateHolder {
        RatingBar Ratingbar;
        CircleImageView iv_img;
        TextView tv_name, tv_centent;
        MyGridView gv_img;

        public GoodsEvaluateHolder(View view) {
            iv_img = view.findViewById(R.id.iv_img);
            tv_name = view.findViewById(R.id.tv_name);
            tv_centent = view.findViewById(R.id.tv_centent);
            gv_img = view.findViewById(R.id.gv_img);
            Ratingbar = view.findViewById(R.id.rb_score);
            view.setTag(this);
        }
    }

    class ImgAdapter extends BaseAdapter {
        private List<String> img;

        public ImgAdapter(List<String> img) {
            this.img = img;
        }

        @Override
        public int getCount() {
            return img == null ? 0 : img.size();
        }

        @Override
        public Object getItem(int position) {
            return img == null ? null : img.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mcontext, R.layout.item_dynamicimg, null);
                new ImgAdapter.ImgHolder(convertView);
            }
            ImgAdapter.ImgHolder holder = (ImgAdapter.ImgHolder) convertView.getTag();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.iv_img.getLayoutParams();
            layoutParams.height = h;
            holder.iv_img.setLayoutParams(layoutParams);
            Glide.with(mcontext).load(img.get(position)).into(holder.iv_img);
            holder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ArrayList<String> list = new ArrayList<String>();
//                    for (int i = 0; i < findImagesModels.size(); i++) {
//                        list.add(findImagesModels.get(i).getApic_cover());
//                    }
                    Intent intent = new Intent(mcontext, ImagePreview.class);
                    intent.putStringArrayListExtra("photourl", (ArrayList<String>) img);
                    intent.putExtra("id", position);
                    mcontext.startActivity(intent);
                }
            });
            return convertView;
        }

        class ImgHolder {
            ImageView iv_img;

            public ImgHolder(View convertView) {
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                convertView.setTag(this);
            }
        }
    }


}
