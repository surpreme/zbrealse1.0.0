package livestream.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aite.a.base.Mark;
import com.aite.a.view.MyDialog;
import com.aiteshangcheng.a.R;

/**
 * Fragment基类
 * Created by Administrator on 2017/8/22.
 */
public abstract class BaseFragment extends Fragment implements Mark{
    protected View layout;
    protected MyDialog myDialog;
    private View inflate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(getlayoutResId(), null);
        inflate=View.inflate(getActivity(), R.layout.dialog_baseprogress,null);
        myDialog = new MyDialog(getActivity(), 150, 150,
                inflate, R.style.loading_dialog);
        myDialog.setCanceledOnTouchOutside(false);
        initView();
        initData();
        return layout;
    }

    /**
     * 通知首页切换
     */
    protected void ToHome(int id){
        Bundle bundle = new Bundle();
        bundle.putInt("index",id);
        Intent intent;
        // 消息广播频道
        intent = new Intent("homemenu");
        intent.putExtras(bundle);
        // 有序
        getActivity().sendOrderedBroadcast(intent, null);
    }

    /**
     * 布局
     *
     * @return 布局的ID
     */
    protected abstract int getlayoutResId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 请求数据
     */
    protected abstract void initData();


}
