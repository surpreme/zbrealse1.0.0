package courier.model;

import java.util.List;

/**
 * 月份选择
 * Created by Administrator on 2018/1/25.
 */
public class MothChooseInfo {
    public String year;
    public List<String>month;

    public MothChooseInfo(String year, List<String> month) {
        this.year = year;
        this.month = month;
    }
}
