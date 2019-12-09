package chat.model;

import java.io.Serializable;

/**
 * 会话消息
 * Created by Administrator on 2017/11/21.
 */
public class MessageInfo implements Serializable {
    public String from;//发送者
    public String to;//接受者
    public String isSend;//是否发送 1发送 2接受
    public String msgtime;//发送时间
    public String msgtype;//消息类型 TXT文字 IMAGE图片 VOICE语音 FILE文件 VIDEO视屏 LOCATION位置
    public textmsg textmsg;//文字消息对象
    public imagemsg imagemsg;//图片消息对象
    public voicemsg voicemsg;//语音消息对象
    public filemsg filemsg;//文件消息对象
    public videomsg videomsg;//视屏消息对象
    public locationmsg locationmsg;//地址
    public static class textmsg implements Serializable {
        public String txtcontent;//消息内容

        public textmsg(String txtcontent) {
            this.txtcontent = txtcontent;
        }
        public textmsg() {
        }
    }

    public static class imagemsg implements Serializable {
        public String preview;//远程预览路径
        public String remoteurl;//远程路径
        public String localurl;//本地路径

        public imagemsg(String preview, String remoteurl, String localurl) {
            this.preview = preview;
            this.remoteurl = remoteurl;
            this.localurl = localurl;
        }
        public imagemsg() {
        }
    }

    public static class voicemsg implements Serializable {
        public String remoteurl;//远程路径
        public String localurl;//本地路径
        public int voicelength;//时长

        public voicemsg(String remoteurl, String localurl, int voicelength) {
            this.remoteurl = remoteurl;
            this.localurl = localurl;
            this.voicelength = voicelength;
        }
        public voicemsg() {
        }
    }

    public static class filemsg implements Serializable {
        public String filename;//文件名
        public String filesize;//文件大小
        public String filelocalurl;//本地路径
        public String fileremoteurl;//远程路径

        public filemsg(String filename, String filesize, String filelocalurl, String fileremoteurl) {
            this.filename = filename;
            this.filesize = filesize;
            this.filelocalurl = filelocalurl;
            this.fileremoteurl = fileremoteurl;
        }
        public filemsg() {
        }
    }

    public static class videomsg implements Serializable {
        public String videoname;//文件名
        public int videoduration;//持续时间
        public String filelocalurl;//本地路径
        public String thumbnail;//缩略图

        public videomsg(String videoname, int videoduration, String filelocalurl, String thumbnail) {
            this.videoname = videoname;
            this.videoduration = videoduration;
            this.filelocalurl = filelocalurl;
            this.thumbnail = thumbnail;
        }
        public videomsg() {
        }
    }

    public static class locationmsg implements Serializable {
        public String address;//地址
        public double latitude;//纬度
        public double longitude;//经度

        public locationmsg(String address, double latitude, double longitude) {
            this.address = address;
            this.latitude = latitude;
            this.longitude = longitude;
        }
        public locationmsg() {
        }
    }
    //全部
    public MessageInfo(String from, String to, String isSend, String msgtime, String msgtype,
                       MessageInfo.textmsg textmsg, MessageInfo.imagemsg imagemsg, MessageInfo.voicemsg voicemsg,
                       MessageInfo.filemsg filemsg, MessageInfo.videomsg videomsg, MessageInfo.locationmsg locationmsg) {
        this.from = from;
        this.to = to;
        this.isSend = isSend;
        this.msgtime = msgtime;
        this.msgtype = msgtype;
        this.textmsg = textmsg;
        this.imagemsg = imagemsg;
        this.voicemsg = voicemsg;
        this.filemsg = filemsg;
        this.videomsg = videomsg;
        this.locationmsg = locationmsg;
    }
    //文字
    public MessageInfo(String from, String to, String isSend, String msgtime, String msgtype,
                       MessageInfo.textmsg textmsg) {
        this.from = from;
        this.to = to;
        this.isSend = isSend;
        this.msgtime = msgtime;
        this.msgtype = msgtype;
        this.textmsg = textmsg;
    }
    //图片
    public MessageInfo(String from, String to, String isSend, String msgtime, String msgtype,
                       MessageInfo.imagemsg imagemsg) {
        this.from = from;
        this.to = to;
        this.isSend = isSend;
        this.msgtime = msgtime;
        this.msgtype = msgtype;
        this.imagemsg = imagemsg;
    }
    //语音
    public MessageInfo(String from, String to, String isSend, String msgtime, String msgtype,
                       MessageInfo.voicemsg voicemsg) {
        this.from = from;
        this.to = to;
        this.isSend = isSend;
        this.msgtime = msgtime;
        this.msgtype = msgtype;
        this.voicemsg = voicemsg;
    }
    //文件
    public MessageInfo(String from, String to, String isSend, String msgtime, String msgtype,
                       MessageInfo.filemsg filemsg) {
        this.from = from;
        this.to = to;
        this.isSend = isSend;
        this.msgtime = msgtime;
        this.msgtype = msgtype;
        this.filemsg = filemsg;
    }
    //视屏
    public MessageInfo(String from, String to, String isSend, String msgtime, String msgtype,
                       MessageInfo.videomsg videomsg) {
        this.from = from;
        this.to = to;
        this.isSend = isSend;
        this.msgtime = msgtime;
        this.msgtype = msgtype;
        this.videomsg = videomsg;
    }
    //地址
    public MessageInfo(String from, String to, String isSend, String msgtime, String msgtype,
                       MessageInfo.locationmsg locationmsg) {
        this.from = from;
        this.to = to;
        this.isSend = isSend;
        this.msgtime = msgtime;
        this.msgtype = msgtype;
        this.locationmsg = locationmsg;
    }
    public MessageInfo() {
    }
}
