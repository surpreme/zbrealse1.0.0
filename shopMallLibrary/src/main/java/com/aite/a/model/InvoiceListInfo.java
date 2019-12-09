package com.aite.a.model;

import java.util.List;

/**
 * 发票列表
 * Created by mayn on 2018/8/16.
 */

public class InvoiceListInfo {
    public String message;
    public data data;
    public static class data {
        public List<inv_list> inv_list;

        public static class inv_list {
            public String inv_id;
            public String member_id;
            public String inv_state;
            public String inv_title;
            public String inv_content;
            public String inv_company;
            public String inv_code;
            public String inv_reg_addr;
            public String inv_reg_phone;
            public String inv_reg_bname;
            public String inv_reg_baccount;
            public String inv_rec_name;
            public String inv_rec_mobphone;
            public String inv_rec_province;
            public String inv_goto_addr;
            public String inv_type;
            public String content;
            public boolean ispick=false;
        }
    }

}
