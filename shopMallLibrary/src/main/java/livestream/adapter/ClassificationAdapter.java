package livestream.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import livestream.mode.ClassificationInfo;

import static com.aite.a.base.Mark.SETROOM_TYPE;

/**
 * 一级分类
 * Created by mayn on 2018/12/26.
 */
public class ClassificationAdapter extends BaseAdapter {
    private Context mcontext;
    private List<ClassificationInfo.class_list> data;
    private Handler handler;

    public ClassificationAdapter(Context mcontext, List<ClassificationInfo.class_list> data, Handler handler) {
        this.mcontext = mcontext;
        this.data = data;
        this.handler = handler;
    }

    private void setpick(int id) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).ispick = false;
        }
        data.get(id).ispick = true;
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
            view = View.inflate(mcontext, R.layout.item_classification, null);
            new ClassificationHolder(view);
        }
        ClassificationHolder holder = (ClassificationHolder) view.getTag();
        final ClassificationInfo.class_list class_list = data.get(i);
        if (class_list.ispick) {
            holder.tv_name.setTextColor(Color.RED);
        } else {
            holder.tv_name.setTextColor(Color.BLACK);
        }
        holder.tv_name.setText(class_list.gc_name);

        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpick(i);
                handler.sendMessage(handler.obtainMessage(SETROOM_TYPE, class_list.children1));
            }
        });
        return view;
    }

    class ClassificationHolder {
        TextView tv_name;

        public ClassificationHolder(View view) {
            tv_name = view.findViewById(R.id.tv_name);
            view.setTag(this);
        }
    }
}
