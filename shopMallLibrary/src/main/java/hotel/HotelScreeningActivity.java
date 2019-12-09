package hotel;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotelListInfo.datas;
import com.aite.a.model.HotelListInfo.datas.brand_list;
import com.aite.a.model.HotelListInfo.datas.sheshi_list;
import com.aite.a.model.HotelListInfo.datas.style_arr;
import com.aite.a.model.HotelListInfo1;
import com.aite.a.model.HotelListInfo2;
import com.aiteshangcheng.a.R;

/**
 * 酒店筛选
 *
 * @author Administrator
 */
public class HotelScreeningActivity extends BaseActivity implements
        OnClickListener {
    private ImageView iv_return;
    private ListView lv_level1, lv_level2;
    private TextView tv_determine;
    private MyAdapter myAdapter;
    private MyAdapter2 myAdapter2;
    private datas data;
    private List<HotelListInfo1> level1txt;
    private List<HotelListInfo2> hotelListInfo1, hotelListInfo2,
            hotelListInfo3, hotelListInfo4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotelscreening);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_return = (ImageView) findViewById(R.id.iv_return);
        lv_level1 = (ListView) findViewById(R.id.lv_level1);
        lv_level2 = (ListView) findViewById(R.id.lv_level2);
        tv_determine = (TextView) findViewById(R.id.tv_determine);
        initView();
    }

    @Override
    protected void initView() {
        iv_return.setOnClickListener(this);
        tv_determine.setOnClickListener(this);
        initData();
    }

    @Override
    protected void initData() {
        data = (datas) getIntent().getSerializableExtra("data");
        level1txt = new ArrayList<>();
        level1txt.add(new HotelListInfo1(getString(R.string.find_reminder206), true));
        level1txt.add(new HotelListInfo1(getString(R.string.find_reminder207), false));
        level1txt.add(new HotelListInfo1(getString(R.string.distancee), false));
        level1txt.add(new HotelListInfo1(getString(R.string.find_reminder136), false));

        myAdapter = new MyAdapter(level1txt);
        lv_level1.setAdapter(myAdapter);
        myAdapter2 = new MyAdapter2(getTS(), 0);
        lv_level2.setAdapter(myAdapter2);
    }

    @Override
    public void ReGetData() {

    }

    /**
     * 获取选中的特色
     *
     * @return
     */
    private String getTSid() {
        List<HotelListInfo2> ts = getTS();
        for (int i = 0; i < ts.size(); i++) {
            if (ts.get(i).ischoose) {
                if (ts.get(i).name.equals(getString(R.string.near_reminder13))) {
                    return getString(R.string.near_reminder13);
                } else if (ts.get(i).name != null
                        && ts.get(i).name.length() < 0) {
                    return ts.get(i).name.substring(0, 3);
                } else {
                    return getString(R.string.find_reminder176);
                }
            }
        }
        return "0";
    }

    /**
     * 获取品牌id
     *
     * @return
     */
    private String getPPid() {
        List<HotelListInfo2> pp = getPP();
        for (int i = 0; i < pp.size(); i++) {
            if (pp.get(i).ischoose) {
                return pp.get(i).id.equals("") ? "0" : pp.get(i).id;
            }
        }
        return "0";
    }

    /**
     * 获取选中距离
     *
     * @return
     */
    private String getJLid() {
        List<HotelListInfo2> jl = getJL();
        for (int i = 0; i < jl.size(); i++) {
            if (jl.get(i).ischoose) {
                return jl.get(i).id;
            }
        }
        return "0";
    }

    /**
     * 获取设施id
     *
     * @return
     */
    private String getSSid() {
        List<HotelListInfo2> ss = getSS();
        for (int i = 0; i < ss.size(); i++) {
            if (ss.get(i).ischoose) {
                return ss.get(i).id;
            }
        }
        return "0";
    }

    /**
     * 获取特色
     */
    private List<HotelListInfo2> getTS() {
        if (hotelListInfo1 != null) {
            return hotelListInfo1;
        }
        hotelListInfo1 = new ArrayList<HotelListInfo2>();
        hotelListInfo1.add(new HotelListInfo2(getString(R.string.near_reminder13), "", "", true));
        if (data == null || data.style_arr == null) {
            return hotelListInfo1;
        }
        for (int i = 0; i < data.style_arr.size(); i++) {
            style_arr style_arr = data.style_arr.get(i);
            hotelListInfo1
                    .add(new HotelListInfo2(style_arr.name, "", "", false));
        }
        return hotelListInfo1;
    }

    /**
     * 获取品牌
     */
    private List<HotelListInfo2> getPP() {
        if (hotelListInfo2 != null) {
            return hotelListInfo2;
        }
        hotelListInfo2 = new ArrayList<HotelListInfo2>();
        hotelListInfo2.add(new HotelListInfo2(getString(R.string.near_reminder13), "0", "", true));
        if (data == null || data.brand_list == null) {
            return hotelListInfo2;
        }
        for (int i = 0; i < data.brand_list.size(); i++) {
            brand_list brand_list = data.brand_list.get(i);
            hotelListInfo2.add(new HotelListInfo2(brand_list.name,
                    brand_list.id, "", false));
        }
        return hotelListInfo2;
    }

    /**
     * 获取距离
     */
    private List<HotelListInfo2> getJL() {
        if (hotelListInfo3 != null) {
            return hotelListInfo3;
        }
        hotelListInfo3 = new ArrayList<HotelListInfo2>();
        hotelListInfo3.add(new HotelListInfo2(getString(R.string.near_reminder13), "0", "", true));
        hotelListInfo3.add(new HotelListInfo2("1"+getString(R.string.find_reminder208), "1", "", false));
        hotelListInfo3.add(new HotelListInfo2("2"+getString(R.string.find_reminder208), "2", "", false));
        hotelListInfo3.add(new HotelListInfo2("4"+getString(R.string.find_reminder208), "4", "", false));
        hotelListInfo3.add(new HotelListInfo2("8"+getString(R.string.find_reminder208), "8", "", false));
        return hotelListInfo3;
    }

    /**
     * 获取设施
     */
    private List<HotelListInfo2> getSS() {
        if (hotelListInfo4 != null) {
            return hotelListInfo4;
        }
        hotelListInfo4 = new ArrayList<HotelListInfo2>();
        hotelListInfo4.add(new HotelListInfo2(getString(R.string.near_reminder13), "0", "", true));
        if (data == null || data.sheshi_list == null) {
            return hotelListInfo4;
        }
        for (int i = 0; i < data.sheshi_list.size(); i++) {
            sheshi_list sheshi_list = data.sheshi_list.get(i);
            hotelListInfo4.add(new HotelListInfo2(sheshi_list.name,
                    sheshi_list.id, "", false));
        }
        return hotelListInfo4;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_return) {
            finish();
        } else if (id == R.id.tv_determine) {// 确定
            Intent intent2 = new Intent(HotelScreeningActivity.this,
                    HotelListActivity.class);
            intent2.putExtra("htype", getTSid());
            intent2.putExtra("brand", getPPid());
            intent2.putExtra("distance", getJLid());
            intent2.putExtra("sheshi", getSSid());
            setResult(0, intent2);
            finish();
        }
    }


    /**
     * 一级
     *
     * @author Administrator
     */
    public class MyAdapter extends BaseAdapter {
        List<HotelListInfo1> level1txt;

        public MyAdapter(List<HotelListInfo1> level1txt) {
            this.level1txt = level1txt;
        }

        public void setChoose(int id) {
            for (int i = 0; i < level1txt.size(); i++) {
                level1txt.get(i).ischosse = false;
            }
            level1txt.get(id).ischosse = true;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return level1txt.size();
        }

        @Override
        public Object getItem(int position) {
            return level1txt == null ? null : level1txt.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(HotelScreeningActivity.this,
                        R.layout.item_screening1, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_name.setText(level1txt.get(position).name);
            if (level1txt.get(position).ischosse) {
                holder.tv_name.setTextColor(0xff0092DD);
                holder.tv_name.setBackgroundColor(0xffF6F6F6);
            } else {
                holder.tv_name.setTextColor(0xff808080);
                holder.tv_name.setBackgroundColor(Color.WHITE);
            }
            holder.tv_name.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            // 特色
                            myAdapter2.updata(getTS(), 0);
                            break;
                        case 1:
                            // 品牌
                            myAdapter2.updata(getPP(), 1);
                            break;
                        case 2:
                            // 距离
                            myAdapter2.updata(getJL(), 2);
                            break;
                        case 3:
                            // 设施
                            myAdapter2.updata(getSS(), 3);
                            break;
                    }
                    setChoose(position);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_name;

            public ViewHolder(View convertView) {
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 二级
     *
     * @author Administrator
     */
    public class MyAdapter2 extends BaseAdapter {
        List<HotelListInfo2> hotelList;
        int type = 0;

        public MyAdapter2(List<HotelListInfo2> hotelList, int type) {
            this.hotelList = hotelList;
            this.type = type;
        }

        /**
         * 更新
         *
         */
        public void updata(List<HotelListInfo2> hotelList, int type) {
            this.hotelList = hotelList;
            this.type = type;
            notifyDataSetChanged();
        }

        public void setChoose(int id) {
            for (int i = 0; i < hotelList.size(); i++) {
                hotelList.get(i).ischoose = false;
            }
            hotelList.get(id).ischoose = true;
            switch (type) {
                case 0:
                    hotelListInfo1 = hotelList;
                    break;
                case 1:
                    hotelListInfo2 = hotelList;
                    break;
                case 2:
                    hotelListInfo3 = hotelList;
                    break;
                case 3:
                    hotelListInfo4 = hotelList;
                    break;
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return hotelList.size();
        }

        @Override
        public Object getItem(int position) {
            return hotelList == null ? null : hotelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(HotelScreeningActivity.this,
                        R.layout.item_screening2, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            HotelListInfo2 hotelListInfo22 = hotelList.get(position);
            holder.tv_name.setText(hotelListInfo22.name);
            holder.cb_choose.setChecked(hotelListInfo22.ischoose);
            holder.cb_choose.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    setChoose(position);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_name;
            CheckBox cb_choose;

            public ViewHolder(View convertView) {
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                cb_choose = (CheckBox) convertView.findViewById(R.id.cb_choose);
                convertView.setTag(this);
            }
        }
    }
}
