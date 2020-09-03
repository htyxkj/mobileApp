package mobile.entity;

public class Users {
	private String userid;
	private String username;
	private String tel;
	private String scm;
	private String email;
	private String w_corpid;
	private String d_corpid;
	private String w_imgurl="img/ren.png";
	private String d_imgurl="img/ren.png";
	private String status;//关注企业号状态
	private String loginType;//登录类型
	private String loginAppid;//登录应用id   //钉钉登录不需要管
	
	private String d_userid;//钉钉ID
	
	
	public String getD_userid() {
		return d_userid;
	}
	public void setD_userid(String d_userid) {
		this.d_userid = d_userid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getScm() {
		return scm;
	}
	public void setScm(String scm) {
		this.scm = scm;
	}
	public String getW_corpid() {
		return w_corpid;
	}
	public void setW_corpid(String w_corpid) {
		this.w_corpid = w_corpid;
	}
	public String getW_imgurl() {
		return w_imgurl;
	}
	public void setW_imgurl(String w_imgurl) {
		this.w_imgurl = w_imgurl;
	}
	public String getD_imgurl() {
		return d_imgurl;
	}
	public void setD_imgurl(String d_imgurl) {
		this.d_imgurl = d_imgurl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getD_corpid() {
		return d_corpid;
	}
	public void setD_corpid(String d_corpid) {
		this.d_corpid = d_corpid;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getLoginAppid() {
		return loginAppid;
	}
	public void setLoginAppid(String loginAppid) {
		this.loginAppid = loginAppid;
	}
}