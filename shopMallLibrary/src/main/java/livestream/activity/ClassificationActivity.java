package livestream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import java.util.List;

import livestream.adapter.Classification2Adapter;
import livestream.adapter.ClassificationAdapter;
import livestream.mode.ClassificationInfo;

/**
 * 直播分类
 * Created by mayn on 2018/12/26.
 */
public class ClassificationActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_return;
    private TextView tv_save;
    private ListView lv_class1, lv_class2;
    private ClassificationInfo classificationInfo;
    private ClassificationAdapter classificationAdapter;
    private Classification2Adapter classification2Adapter;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case room_type_id://直播分类
                    mdialog.dismiss();
                    if (msg.obj != null) {
                        classificationInfo = (ClassificationInfo) msg.obj;
                        if (classificationInfo.class_list != null && classificationInfo.class_list.size() != 0) {
                            classificationInfo.class_list.get(0).ispick = true;
                        }
                        classificationAdapter = new ClassificationAdapter(ClassificationActivity.this, classificationInfo.class_list, handler);
                        lv_class1.setAdapter(classificationAdapter);
                        if (classificationInfo.class_list != null && classificationInfo.class_list.size() != 0) {
                            classificationInfo.class_list.get(0).ispick = true;
                            classification2Adapter = new Classification2Adapter(ClassificationActivity.this,
                                    classificationInfo.class_list.get(0).children1, handler);
                            lv_class2.setAdapter(classification2Adapter);
                        }
                    }
                    break;
                case room_type_err:
                    mdialog.dismiss();
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case SETROOM_TYPE://选中分类
                    if (msg.obj != null) {
                        List<ClassificationInfo.class_list.children1> children1 = (List<ClassificationInfo.class_list.children1>) msg.obj;
                        for (int i = 0; i < children1.size(); i++) {
                            if (children1.get(i).children2 != null) {
                                for (int j = 0; j < children1.get(i).children2.size(); j++) {
                                    children1.get(i).children2.get(j).ispick = false;
                                }
                            }
                        }
                        if (classification2Adapter == null) {
                            classification2Adapter = new Classification2Adapter(ClassificationActivity.this,
                                    children1, handler);
                            lv_class2.setAdapter(classification2Adapter);
                        } else {
                            classification2Adapter.refreshData(children1);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_return = findViewById(R.id.iv_return);
        tv_save = findViewById(R.id.tv_save);
        lv_class1 = findViewById(R.id.lv_class1);
        lv_class2 = findViewById(R.id.lv_class2);
        initView();
    }

    @Override
    protected void initView() {
        iv_return.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        mdialog.show();
        netRun.RoomType();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.iv_return) {
            finish();
        } else if (viewId == R.id.tv_save) {//保存
            ClassificationInfo.class_list.children1.children2 id = classification2Adapter.getID();
            if (id != null) {
                Intent intent = getIntent();
                intent.putExtra("id", id.gc_id);
                intent.putExtra("name", id.gc_name);
                setResult(SETROOM_TYPE2, intent);
                finish();
            } else {
                Toast.makeText(appSingleton, getString(R.string.select_category), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void ReGetData() {

    }
}
