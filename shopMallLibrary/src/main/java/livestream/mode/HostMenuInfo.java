package livestream.mode;

/**
 * 主播菜单
 * Created by Administrator on 2017/9/27.
 */
public class HostMenuInfo {
    public int img;
    public String txt;
    public boolean isunread;

    public HostMenuInfo(int img, String txt, boolean isunread) {
        this.img = img;
        this.txt = txt;
        this.isunread = isunread;
    }
}
