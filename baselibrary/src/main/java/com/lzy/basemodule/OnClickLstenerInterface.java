package com.lzy.basemodule;

public class OnClickLstenerInterface {
    public interface OnRecyClickInterface {
        void getPostion(int postion);
    }

    public interface OnRecyClickInterfaceAndString {
        void getPostion(String type, int postion);
    }

    public interface OnItemRecyClickInterface {
        void getPostion(int postion);
    }

    public interface OnThingClickInterface {
        void getString(String msg);
    }
}
