package com.lzy.basemodule.util;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

public class HttpUtil {

    public static <T> PostRequest<T> post(String url) {

        return new PostRequest<>(url);
    }
}
