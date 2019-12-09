package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.EditValInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 编辑参数
 * Created by Administrator on 2017/6/7.
 */
public class EditValActivity extends BaseActivity implements OnClickListener {
    private ImageView _iv_back;
    private TextView _tv_name;
    private RecyclerView rv_lb;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private Madapter madapter;
    private String commonid;
    private List<EditValInfo> editValInfo;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case edit_val_id:
                    editValInfo= (List<EditValInfo>) msg.obj;
                    
                    madapter = new Madapter(editValInfo);
                    rv_lb.setAdapter(madapter);
                    break;
                case edit_val_err:
                    Toast.makeText(EditValActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editval);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        rv_lb = (RecyclerView) findViewById(R.id.rv_lb);
        _tv_name.setText(getString(R.string.recommended6));
        _iv_back.setOnClickListener(this);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
    	rv_lb.setLayoutManager(new LinearLayoutManager(EditValActivity.this));
        commonid = getIntent().getStringExtra("commonid");
        netRun.EditVal(commonid);

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//        }
        if(v.getId()==R.id._iv_back){
            finish();
        }
    }

    /**
     * 商品列表
     */
    class Madapter extends RecyclerView.Adapter<ViewHodler> {
        List<EditValInfo> editValInfo;

        public Madapter(List<EditValInfo> editValInfo) {
            this.editValInfo = editValInfo;
        }

        /**
         * 获得商品ID
         * @return
         */
        public String getGoods_id(int id){
            return  editValInfo.get(id).goods_id;
        }

        /**
         * 过去商品名字
         * @param id
         * @return
         */
        public String getGoods_name(int id){
            return  editValInfo.get(id).goods_name+" SKU："+editValInfo.get(id).goods_id;
        }



        @Override
        public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHodler holder = new ViewHodler(LayoutInflater.from(
                    EditValActivity.this).inflate(R.layout.item_editval, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHodler holder, final int position) {
            EditValInfo editValInfo = this.editValInfo.get(position);
            bitmapUtils.display(holder.iv_img,editValInfo.goods_image);
            holder.tv_name.setText(editValInfo.goods_name);
            holder.tv_price.setText(getString(R.string.release_goods14)+"￥"+editValInfo.goods_price);
            holder.tv_number.setText(getString(R.string.gifts7)+editValInfo.goods_storage);
            holder.tv_xz.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//设置参数
                    String goods_id = madapter.getGoods_id(position);
                    String goods_name = madapter.getGoods_name(position);
                    Intent intent = new Intent(EditValActivity.this, ParameterActivity.class);
                    intent.putExtra("goodsid",goods_id);
                    intent.putExtra("goods_name",goods_name);
                    startActivity(intent);
				}
			});
        }

        @Override
        public int getItemCount() {
            return editValInfo.size();
        }
    }

    class ViewHodler extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_name,tv_price,tv_number,tv_val,tv_xz;
        public ViewHodler(View itemView) {
            super(itemView);
            iv_img= (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_price= (TextView) itemView.findViewById(R.id.tv_price);
            tv_number= (TextView) itemView.findViewById(R.id.tv_number);
            tv_val= (TextView) itemView.findViewById(R.id.tv_val);
            tv_xz= (TextView) itemView.findViewById(R.id.tv_xz);
        }

    }

	@Override
	public void ReGetData() {
		
	}


}
