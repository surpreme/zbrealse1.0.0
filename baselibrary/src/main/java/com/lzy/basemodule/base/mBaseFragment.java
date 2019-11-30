package com.lzy.basemodule.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lzy.basemodule.mvp.BasePresenterImpl;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.basemodule.view.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.lang.reflect.ParameterizedType;

public class mBaseFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends Fragment {
    public Context context;
    public T mPresenter;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    //初始化banner
    protected void initBanner(Banner banner) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(3000);
        banner.isAutoPlay(true);

    }

    //初始化banner
    protected void initBanner(Banner banner, int type) {
        banner.setBannerStyle(type);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(3000);
        banner.isAutoPlay(true);

    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(context, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz, String tag, String extra) {
        Intent intent = new Intent(context, clz);
        intent.putExtra(tag, extra);
        startActivity(intent);
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
