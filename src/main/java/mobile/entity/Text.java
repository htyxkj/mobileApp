package mobile.entity;

public class Text {
	private Integer id;
	private String title;
	private String type;
	private String content;
	private String users;
	private String w_corpid;
	private String appid;
	private String bipappid;
	private String scm;
	private java.util.Date time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
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
	public String getBipappid() {
		return bipappid;
	}
	public void setBipappid(String bipappid) {
		this.bipappid = bipappid;
	}
	public String getScm() {
		return scm;
	}
	public void setScm(String scm) {
		this.scm = scm;
	}
	public java.util.Date getTime() {
		return time;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	public String toString(){
		return "{\"title\":\""+title+"\",\"type\":\""+type+"\","
				+ "\"content\":\""+content+"\",\"users\":\""+users+"\","
				+ "\"w_corpid\":\""+w_corpid+"\",\"appid\":\""+appid+"\","
				+ "\"bipappid\":\""+bipappid+"\",\"scm\":\""+scm+"\"}";
	}
}