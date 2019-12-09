package courier.fragment;

import android.view.View;
import android.widget.TextView;

import com.aite.a.fargment.BaseFragment;
import com.aiteshangcheng.a.R;

/**
 * 寄件
 * Created by Administrator on 2018/1/8.
 */
public class CourierSendFragment extends BaseFragment implements View.OnClickListener{
    private TextView tv_name,tv_menu1,tv_menu2,tv_menu3,tv_menu4;

    private void findviewbrid(){
        tv_name= (TextView) layout.findViewById(R.id.tv_name);
        tv_menu1= (TextView) layout.findViewById(R.id.tv_menu1);
        tv_menu2= (TextView) layout.findViewById(R.id.tv_menu2);
        tv_menu3= (TextView) layout.findViewById(R.id.tv_menu3);
        tv_menu4= (TextView) layout.findViewById(R.id.tv_menu4);
    }

    @Override
    protected void initView() {
        findviewbrid();
        tv_name.setText("寄件");
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        tv_menu3.setOnClickListener(this);
        tv_menu4.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_couriersend;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_menu1) {
        } else if (id == R.id.tv_menu2) {
        } else if (id == R.id.tv_menu3) {
        } else if (id == R.id.tv_menu4) {
        }
    }
}
