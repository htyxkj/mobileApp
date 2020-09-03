package mobile.entity;

import java.util.Date;

public class Num {
	private String weixinid;
	private String w_corpid;
	private String appid;
	private String scm;
	private Date time;
	private String content;
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
	public String getW_corpid() {
		return w_corpid;
	}
	public void setW_corpid(String w_corpid) {
		this.w_corpid = w_corpid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getScm() {
		return scm;
	}
	public void setScm(String scm) {
		this.scm = scm;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
