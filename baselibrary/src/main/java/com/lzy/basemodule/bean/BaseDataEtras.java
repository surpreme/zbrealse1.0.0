package com.lzy.basemodule.bean;

import java.io.Serializable;


/**
 * @author:TQX
 * @Date: 2019/4/15
 * @description:
 */
public class BaseDataEtras<T extends ErrorBean> implements Serializable {


    /**
     * error_code : 10003
     * message : 用户名密码错误
     * datas :
     */

    private Object code;
    private T datas;
    private boolean isSuccessed;

    public String getRegister_step() {
        return register_step;
    }

    public void setRegister_step(String register_step) {
        this.register_step = register_step;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String register_step;
    private String key;


    public boolean isSuccessed() {
        return getCode().toString().equals("200");
    }

    public void setSuccessed(boolean successed) {
        isSuccessed = successed;
    }


    private String errorMsg;

//    public String getErrorMsg() {
//        if (!isSuccessed()) {
//            ErrorBean code = BeanConvertor.convertBean(datas, ErrorBean.class);
//            return code.getError();
//        }
//        return null;
//    }

    public void setErrorMsg(String msg) {
        this.errorMsg = msg;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }


}
