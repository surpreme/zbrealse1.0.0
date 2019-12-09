package livestream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.donkingliang.labels.LabelsView;

import java.util.List;

import livestream.mode.RoomTagInfo;

/**
 * 选择标签
 * Created by Administrator on 2017/9/18.
 */
public class ChooseLabelActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_return;
    private TextView tv_save, tv_choosenumber;
    private List<RoomTagInfo> roomTagInfo;
    private LabelsView labels;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case room_tag_id://标签列表
                    if (msg.obj != null) {
                        roomTagInfo = (List<RoomTagInfo>) msg.obj;
                        labels.setLabels(roomTagInfo, new LabelsView.LabelTextProvider<RoomTagInfo>() {
                            @Override
                            public CharSequence getLabelText(TextView label, int position, RoomTagInfo data) {
                                //根据data和position返回label需要显示的数据。
                                return data.mtag_name;
                            }
                        });
                    }
                    break;
                case room_tag_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlabel);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_return = (ImageView) findViewById(R.id.iv_return);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_choosenumber = (TextView) findViewById(R.id.tv_choosenumber);
        labels = (LabelsView) findViewById(R.id.labels);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        iv_return.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun.RoomTag();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_return) {
            finish();
        } else if (id == R.id.tv_save) {//保存
            List<RoomTagInfo> selectLabelDatas = labels.getSelectLabelDatas();
            if (selectLabelDatas.size() == 0) {
                Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder62), Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < selectLabelDatas.size(); i++) {
                stringBuffer.append(selectLabelDatas.get(i).mtag_name + ",");
            }
            String label = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
            Intent intent = getIntent();
            intent.putExtra("label", label);
            setResult(SETROOM_LABLE, intent);
            finish();
        }
    }

    @Override
    public void ReGetData() {

    }

}
