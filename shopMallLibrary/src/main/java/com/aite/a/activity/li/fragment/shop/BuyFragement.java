package com.aite.a.activity.li.fragment.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.aite.a.activity.li.activity.ShopHomeActivity;
import com.aite.a.activity.li.adapter.ShopRecyAdapter;
import com.aite.a.activity.li.bean.ShopBean;
import com.aite.a.activity.li.fragment.BaseFragment;
import com.aite.a.activity.li.fragment.type.AmClassShopFragment;
import com.aite.a.activity.li.p.Model;
import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.R2;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
/**
 * 弃用
 */
public class BuyFragement extends BaseFragment implements AmClassShopFragment.ChocieInterface {
    @BindView(R2.id.shop_type_recy)
    RecyclerView shop_type_recy;
    @BindView(R2.id.smartlayout)
    SmartRefreshLayout smartRefreshLayout;

    private ShopRecyAdapter shopRecyAdapter;
    private List<ShopBean.DatasBean.StoreListBean> mDatasList = new ArrayList<>();
    private int pages = 1;
    private boolean isCanHasMore = true;

    private Page page;
    private static final int type = 2;
    private int toplow = 1;
    private AmClassShopFragment amClassShopFragment = new AmClassShopFragment();
    private static final int MESSAGE_WHAT = 17845;

    @Override
    protected void initModel() {
        final Model model = new Model(context);
        model.amClassShopFragment(String.valueOf(type), toplow, pages, new Model.ModelInteface.AmClassShopFragmentInterface() {
            @Override
            public void amClassShopFragmentInterfaceModelSuccess(Context context, final List<?> DatasList, boolean ishasmore) {
                if (!ishasmore) {
                    isCanHasMore = false;
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (page.isTypeChanger()) {
                            mDatasList.clear();
                            page.setTypeChange(false);
                        }
                        mDatasList.addAll((Collection<? extends ShopBean.DatasBean.StoreListBean>) DatasList);
                        shopRecyAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void amClassShopFragmentInterfaceeModelFail(String error) {

            }
        });


    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_WHAT:
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {

                    if (shopRecyAdapter == null)
                        shopRecyAdapter = new ShopRecyAdapter(getContext(), mDatasList);
                    shopRecyAdapter.notifyDataSetChanged();
//                        }
//                    });
                    break;
            }
        }
    };


    private void initDatas() {
        final Model model = new Model(context);
        model.amClassShopFragment(String.valueOf(type), toplow, pages, new Model.ModelInteface.AmClassShopFragmentInterface() {
            @Override
            public void amClassShopFragmentInterfaceModelSuccess(Context context, final List<?> DatasList, boolean ishasmore) {
                if (!ishasmore) {
                    isCanHasMore = false;
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                mDatasList.addAll((Collection<? extends ShopBean.DatasBean.StoreListBean>) DatasList);
                Message message = handler.obtainMessage(MESSAGE_WHAT);
                message.obj = "new";
                handler.sendMessage(message);
            }

            @Override
            public void amClassShopFragmentInterfaceeModelFail(String error) {

            }
        });


    }

    @Override
    protected void initViews() {
        initAction();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_amclassshop;
    }

    private void initAction() {
        page = new Page();
        //下拉刷新
        smartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        smartRefreshLayout.setRefreshHeader(new WaterDropHeader(getActivity()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
                pages = 1;
                initModel();

            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (isCanHasMore) pages++;
                else smartRefreshLayout.finishLoadMoreWithNoMoreData();
                initModel();
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });

        shop_type_recy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        shopRecyAdapter = new ShopRecyAdapter(getActivity(), mDatasList);
        shop_type_recy.setAdapter(shopRecyAdapter);
        shopRecyAdapter.setOnClickInterface(new ShopRecyAdapter.OnClickInterface() {
            @Override
            public void getPostion(int position) {
                startActivity(ShopHomeActivity.class);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void buy(int i) {
        switch (i) {
            case 1:
                toplow = 1;
                initDatas();
                break;
            case 2:
                toplow = 2;
                initDatas();
                break;
            default:
                break;
        }
    }

    @Override
    public void away(int i) {

    }


    class Page {
        private boolean isTypeChange = false;

        boolean isTypeChanger() {
            return isTypeChange;
        }

        void setTypeChange(boolean typeChange) {
            isTypeChange = typeChange;
        }

    }
}