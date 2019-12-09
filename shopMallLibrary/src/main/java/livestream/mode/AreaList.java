package livestream.mode;

import java.util.List;

/**
 * 地区
 * Created by Administrator on 2016/11/14.
 */
public class AreaList {
    public String area_id;
    public String area_name;
    public boolean ispick=false;
    public List<city_list> city_list;

    public static class city_list {
        public String area_id;
        public String area_name;
    }
}
