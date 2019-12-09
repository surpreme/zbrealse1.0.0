package courier.model;

import java.util.List;

/**
 * 驿站首页
 * Created by Administrator on 2018/1/25.
 */
public class CourierHomeInfo {
    public List<list> list;

    public static class list {
        public String id;
        public String img_path;
        public String is_show;
        public String addtime;
        public String href;
    }
}
