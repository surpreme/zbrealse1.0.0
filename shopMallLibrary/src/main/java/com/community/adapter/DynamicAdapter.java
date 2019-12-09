package com.community.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.activity.CircleoffriendsDatilsActivity;
import com.community.activity.ImagePreview;
import com.community.model.FindHomeInfo;
import com.community.utils.ClutterUtils;
import com.community.utils.Menu2Popu;

import java.util.ArrayList;
import java.util.List;

import static com.aite.a.base.Mark.DYNAMIC;
import static com.aite.a.base.Mark.PRAISECIRCLEOFFRIENDS;

/**
 * 动态
 * Created by mayn on 2018/8/31.
 */

public class DynamicAdapter extends BaseAdapter {
    private Context mcontext;
    private List<FindHomeInfo.theme_list> data;
    private Activity mActivity = null;
    private Handler handler;
    private int h = 0;//图片的高度

    public DynamicAdapter(Context mcontext, List<FindHomeInfo.theme_list> data, Handler handler, Activity mActivity) {
        this.mcontext = mcontext;
        this.data = data;
        this.handler = handler;
        this.mActivity = mActivity;
        h = ClutterUtils.getScreenWidth(mcontext) - ClutterUtils.dp2px(mcontext, 85);
    }

    private void showpopu(ImageView v, final String member_id) {
        Menu2Popu menu2Popu = new Menu2Popu(mActivity);
        menu2Popu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));
        menu2Popu.setmenu(new Menu2Popu.menu() {
            @Override
            public void onItemClick(int id) {
                if (id == 1) {//评论
                    handler.sendMessage(handler.obtainMessage(DYNAMIC, member_id));
                } else {//点赞
                }
            }
        });
        menu2Popu.showAsDropDown(v);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_dynamic, null);
            new DynamicHolder(convertView);
        }
        final DynamicHolder holder = (DynamicHolder) convertView.getTag();
        final FindHomeInfo.theme_list theme_list = data.get(position);
        Glide.with(mcontext).load(theme_list.member_avatar).into(holder.iv_iocn);
        holder.tv_nikename.setText(theme_list.member_name);
        holder.tv_desc.setText(theme_list.theme_name);
        holder.tv_titme.setText(theme_list.theme_addtime);
        if(theme_list.is_like.equals("0")){
            holder.iv_gz.setImageResource(R.drawable.heart1);
        }else{
            holder.iv_gz.setImageResource(R.drawable.heart2);
        }
        CommentAdaapter commentAdaapter = new CommentAdaapter(theme_list.reply_info);
        holder.lv_pl.setAdapter(commentAdaapter);
        if (theme_list.thumb_list != null && theme_list.thumb_list.size() != 0) {
            holder.gn_img.setVisibility(View.VISIBLE);
            if (theme_list.thumb_list.size() < 2) {
                holder.gn_img.setNumColumns(1);
            } else if (theme_list.thumb_list.size() < 3) {
                holder.gn_img.setNumColumns(2);
            } else {
                holder.gn_img.setNumColumns(3);
            }
            ImgAdapter imgAdapter = new ImgAdapter(theme_list.thumb_list);
            holder.gn_img.setAdapter(imgAdapter);
        } else {
            holder.gn_img.setVisibility(View.GONE);
        }
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, CircleoffriendsDatilsActivity.class);
                intent.putExtra("theme_id", theme_list.theme_id);
                mcontext.startActivity(intent);
            }
        });
        holder.iv_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(DYNAMIC, theme_list.theme_id));
            }
        });
        holder.iv_gz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(PRAISECIRCLEOFFRIENDS, theme_list.theme_id));
            }
        });
        return convertView;
    }

    class DynamicHolder {
        ImageView iv_iocn,iv_pl,iv_gz;
        TextView tv_nikename, tv_desc, tv_titme;
        MyGridView gn_img;
        MyListView lv_pl;
        RelativeLayout rl_item;

        public DynamicHolder(View convertView) {
            iv_iocn = (ImageView) convertView.findViewById(R.id.iv_iocn);
            iv_pl = (ImageView) convertView.findViewById(R.id.iv_pl);
            iv_gz = (ImageView) convertView.findViewById(R.id.iv_gz);
            tv_nikename = (TextView) convertView.findViewById(R.id.tv_nikename);
            tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            tv_titme = (TextView) convertView.findViewById(R.id.tv_titme);
            gn_img = (MyGridView) convertView.findViewById(R.id.gn_img);
            lv_pl = (MyListView) convertView.findViewById(R.id.lv_pl);
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }

    class ImgAdapter extends BaseAdapter {
        private List<String> img;

        public ImgAdapter(List<String> img) {
            this.img = img;
        }

        @Override
        public int getCount() {
            return img.size();
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
                new ImgHolder(convertView);
            }
            ImgHolder holder = (ImgHolder) convertView.getTag();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.iv_img.getLayoutParams();
            if (getCount() < 2) {
                layoutParams.height = h;
            } else if (getCount() < 3) {
                layoutParams.height = h / 2;
            } else {
                layoutParams.height = h / 3;
            }
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

    class CommentAdaapter extends BaseAdapter {
        private List<FindHomeInfo.theme_list.reply_info> reply_info;

        public CommentAdaapter(List<FindHomeInfo.theme_list.reply_info> reply_info) {
            this.reply_info = reply_info;
        }

        @Override
        public int getCount() {
            return reply_info == null ? 0 : reply_info.size();
        }

        @Override
        public Object getItem(int position) {
            return reply_info == null ? null : reply_info.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mcontext, R.layout.item_comment, null);
                new CommentHolder(convertView);
            }
            CommentHolder holder = (CommentHolder) convertView.getTag();
            FindHomeInfo.theme_list.reply_info reply_info = this.reply_info.get(position);
            holder.tv_reply.setText(reply_info.reply_content);
            holder.tv_replyname.setText(reply_info.member_name + ":");
            return convertView;
        }

        class CommentHolder {
            TextView tv_replyname, tv_reply;

            public CommentHolder(View convertView) {
                tv_replyname = (TextView) convertView.findViewById(R.id.tv_replyname);
                tv_reply = (TextView) convertView.findViewById(R.id.tv_reply);
                convertView.setTag(this);
            }
        }
    }
}
