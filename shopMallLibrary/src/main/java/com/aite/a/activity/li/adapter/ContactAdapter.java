package com.aite.a.activity.li.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ContactAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
//    private ArrayList<Contact> arrayList;
//    private Context context;
//    private String pre = "A";
//
//    public ContactAdapter(ArrayList<Contact> arrayList, Context context) {
//        this.arrayList = arrayList;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return arrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return arrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
//            viewHolder.tv_firstWord = (TextView) convertView.findViewById(R.id.tv);
//            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.tv_firstWord.setText(String.valueOf(arrayList.get(position).getFirstWord()));
//        viewHolder.name.setText(arrayList.get(position).getName());
//        /**
//         * 分组:根据汉字的首字母
//         */
//        viewHolder.tv_firstWord.setVisibility(!arrayList.get(position).getFirstWord().equals(pre) ? View.VISIBLE : View.GONE);
//        pre = arrayList.get(position).getFirstWord();
//        return convertView;
//    }
//
//    class ViewHolder {
//        TextView tv_firstWord;
//        TextView name;
//    }
}