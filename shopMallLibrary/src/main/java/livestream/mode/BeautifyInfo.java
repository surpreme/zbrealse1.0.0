package livestream.mode;

/**
 * 美颜/滤镜 参数
 * Created by Administrator on 2017/9/28.
 */
public class BeautifyInfo {
    public int style = 0;//磨皮风格 0：光滑  1：自然  2：朦胧
    public int beautyLevel = 0;//磨皮等级 取值为0-9.
    public int whiteningLevel = 0;//美白等级 取值为0-9.
    public int ruddyLevel = 0;//红润等级 取值为0-9.
    public float specialratio=0.5f; //滤镜的程度 取值为0-1.
    public int choosestyle=0;// 滤镜类型 取值为0-8.

    public BeautifyInfo(int style, int beautyLevel, int whiteningLevel, int ruddyLevel, float specialratio, int choosestyle) {
        this.style = style;
        this.beautyLevel = beautyLevel;
        this.whiteningLevel = whiteningLevel;
        this.ruddyLevel = ruddyLevel;
        this.specialratio = specialratio;
        this.choosestyle = choosestyle;
    }

    public BeautifyInfo() {
    }
}
