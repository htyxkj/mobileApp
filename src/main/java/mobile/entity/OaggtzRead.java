package mobile.entity;

public class OaggtzRead {
	private String sid;//编码
	private String usercode;//用户编码
	private Integer read;//已读？
	private String w_corpid;//企业号标识
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public Integer getRead() {
		return read;
	}
	public void setRead(Integer read) {
		this.read = read;
	}
	public String getW_corpid() {
		return w_corpid;
	}
	public void setW_corpid(String w_corpid) {
		this.w_corpid = w_corpid;
	}
	
}
