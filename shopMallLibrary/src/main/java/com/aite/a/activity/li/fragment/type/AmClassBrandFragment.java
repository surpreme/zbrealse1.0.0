package com.aite.a.activity.li.fragment.type;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.aite.a.activity.li.adapter.BanderRecyAdapter;
import com.aite.a.activity.li.bean.BrandBean;
import com.aite.a.activity.li.fragment.BaseFragment;
import com.aite.a.activity.li.p.Model;
import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.R2;
import com.gjiazhe.wavesidebar.WaveSideBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;

public class AmClassBrandFragment extends BaseFragment {
    @BindView(R2.id.band_recy)
    RecyclerView recyclerView;
    @BindView(R2.id.side_bar)
    WaveSideBar waveSideBar;
    private BanderRecyAdapter banderRecyAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<BrandBean.DatasBean> mdatas = new ArrayList<>();

    @Override
    protected void initModel() {
        Model model = new Model(getActivity());
        model.amClassBrandFragment(new Model.ModelInteface.AmClassBrandFragmentInterface() {
            @Override
            public void amClassBrandFragmentInterfaceModelSuccess(Context context, final List<?> datas) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mdatas.addAll((Collection<? extends BrandBean.DatasBean>) datas);
                        banderRecyAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void amClassBrandFragmentInterfaceModelFail(String error) {

            }
        });
    }

    @Override
    protected void initViews() {
        linearLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(linearLayoutManager);
        banderRecyAdapter = new BanderRecyAdapter(context, mdatas);
        recyclerView.setAdapter(banderRecyAdapter);
        waveSideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < mdatas.size(); i++) {
                    if (mdatas.get(i).getTitle().equals(index)){
                        linearLayoutManager.scrollToPosition(/*recyclerView,new RecyclerView.State(),*/i);
                        return;
                    }
//                    if ((String.valueOf(SpUtils.getPingYin(mdatas.get(i).getBrand_name()).toUpperCase()).equals(index))) {
////                                        recyclerView.scrollToPosition(i);
//                        //或者
//
//                    }


                }
            }
        });
        banderRecyAdapter.setOnclickInterface(new BanderRecyAdapter.OnclickInterface() {
            @Override
            public void getPostion(int postion) {
//                Bundle bundle = new Bundle();
//                bundle.putString("b_id", mdatas.get(postion).getList().get());
//                bundle.putString("gc_name", mdatas.get(postion).getBrand_name());
//                startActivity(GoodsListActivity.class, bundle);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_amclassbrand;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onClick(View v) {

    }
}
