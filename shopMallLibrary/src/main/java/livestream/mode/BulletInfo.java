package livestream.mode;

/**
 * 弹幕
 * Created by Administrator on 2017/9/28.
 */
public class BulletInfo {
    public String level;
    public String name;
    public String txt;
    public String type;

    public BulletInfo(String level, String name, String txt, String type) {
        this.level = level;
        this.name = name;
        this.txt = txt;
        this.type = type;
    }
}
