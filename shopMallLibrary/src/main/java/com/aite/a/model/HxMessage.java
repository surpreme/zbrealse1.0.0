package com.aite.a.model;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * 环信消息
 * 
 * @author Administrator
 *
 */
public class HxMessage implements Serializable {
	public String from;
	public String to;
	public String body;
	public String isSend;
	public String imagee;
	public String imagee_localurl;
	public String msgtype;
	public String voicelocalurl;
	public String voiceremoteurl;
	public int voicelength;
	public String file;
	public String filelocalUrl;
	public String fileremoteUrl;
	public String filesize;
	
	
	public HxMessage(String from, String to, String body, String isSend,
			String imagee, String imagee_localurl, String msgtype) {
		super();
		this.from = from;
		this.to = to;
		this.body = body;
		this.isSend = isSend;
		this.imagee = imagee;
		this.imagee_localurl = imagee_localurl;
		this.msgtype = msgtype;
	}

	public HxMessage(String from, String to, String isSend, String msgtype,
			String voicelocalurl, String voiceremoteurl, int voicelength) {
		super();
		this.from = from;
		this.to = to;
		this.isSend = isSend;
		this.msgtype = msgtype;
		this.voicelocalurl = voicelocalurl;
		this.voiceremoteurl = voiceremoteurl;
		this.voicelength = voicelength;
	}
	
	public HxMessage(String from, String to, String isSend, String msgtype,
			String file,String filesize, String filelocalUrl, String fileremoteUrl,String kogn) {
		super();
		this.from = from;
		this.to = to;
		this.isSend = isSend;
		this.msgtype = msgtype;
		this.file = file;
		this.filesize=filesize;
		this.filelocalUrl = filelocalUrl;
		this.fileremoteUrl = fileremoteUrl;
	}

	public HxMessage() {
	}
}
