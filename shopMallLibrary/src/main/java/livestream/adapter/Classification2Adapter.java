package livestream.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;

import java.util.List;

import livestream.mode.ClassificationInfo;

import static com.aite.a.base.Mark.SETROOM_TYPE2;

/**
 * 二级级分类
 * Created by mayn on 2018/12/26.
 */
public class Classification2Adapter extends BaseAdapter {
    private Context mcontext;
    private List<ClassificationInfo.class_list.children1> data;
    private Handler handler;

    public Classification2Adapter(Context mcontext, List<ClassificationInfo.class_list.children1> data, Handler handler) {
        this.mcontext = mcontext;
        this.data = data;
        this.handler = handler;
    }

    private void setpick(String id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).children2 != null) {
                for (int j = 0; j < data.get(i).children2.size(); j++) {
                    if (data.get(i).children2.get(j).gc_id.equals(id)) {
                        data.get(i).children2.get(j).ispick = true;
                    } else {
                        data.get(i).children2.get(j).ispick = false;
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public ClassificationInfo.class_list.children1.children2 getID(){
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).children2 != null) {
                for (int j = 0; j < data.get(i).children2.size(); j++) {
                    if (data.get(i).children2.get(j).ispick) {
                        return data.get(i).children2.get(j);
                    }
                }
            }
        }
        return null;
    }
    /**
     * 刷新
     *
     * @param data
     */
    public void refreshData(List<ClassificationInfo.class_list.children1> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data == null ? null : data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_classification2, null);
            new ClassificationHolder(view);
        }
        ClassificationHolder holder = (ClassificationHolder) view.getTag();
        final ClassificationInfo.class_list.children1 class_list = data.get(i);
//        if (class_list.ispick) {
//            holder.tv_title.setTextColor(Color.RED);
//        } else {
//            holder.tv_title.setTextColor(Color.BLACK);
//        }
        holder.tv_title.setText(class_list.gc_name);
        Classification3Adapter adapter = new Classification3Adapter(class_list.children2);
        holder.gv_class3.setAdapter(adapter);
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setpick(i);
            }
        });
        return view;
    }

    class ClassificationHolder {
        TextView tv_title;
        MyGridView gv_class3;

        public ClassificationHolder(View view) {
            tv_title = view.findViewById(R.id.tv_title);
            gv_class3 = view.findViewById(R.id.gv_class3);
            view.setTag(this);
        }
    }

    private class Classification3Adapter extends BaseAdapter {
        private List<ClassificationInfo.class_list.children1.children2> children2;

        public Classification3Adapter(List<ClassificationInfo.class_list.children1.children2> children2) {
            this.children2 = children2;
        }

        @Override
        public int getCount() {
            return children2 == null ? 0 : children2.size();
        }

        @Override
        public Object getItem(int i) {
            return children2 == null ? null : children2.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(mcontext, R.layout.item_classification3, null);
                new Classification3Holder(view);
            }
            Classification3Holder holder = (Classification3Holder) view.getTag();
            final ClassificationInfo.class_list.children1.children2 children2 = this.children2.get(i);
            holder.tv_name.setText(children2.gc_name);
            if (children2.ispick) {
                holder.tv_name.setTextColor(Color.RED);
            } else {
                holder.tv_name.setTextColor(Color.BLACK);
            }
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setpick(children2.gc_id);
                    handler.sendMessage(handler.obtainMessage(SETROOM_TYPE2, children2.gc_id));
                }
            });
            return view;
        }
    }

    class Classification3Holder {
        TextView tv_name;

        public Classification3Holder(View view) {
            tv_name = view.findViewById(R.id.tv_name);
            view.setTag(this);
        }
    }
}
