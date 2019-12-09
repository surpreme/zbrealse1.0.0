package chat.model;

/**
 * Im账号签名
 * Created by mayn on 2018/10/25.
 */
public class IMUserSigInfo {
    public String message;
    public datas datas;

    public static class datas {
        public String userSig;
        public String identifier;
        public String sdkAppID;
        public String identifierNick;
        public String headurl;
    }

}
