package mobile.entity;

public class Inswaplist {
	private String uuid;
	private String orgcode;/**公司编码**/
	private String wapno;/**应用编码**/
	private String wapname;/**应用名称**/
	private String w_applyid;/**微信应用ID**/
	private String w_wapurl;/**微信URL**/
	private String w_corpid;/**微信企业号id**/
	private String w_appsecret;/**微信应用Secret**/
	private String d_applyid;/**钉钉应用ID**/
	private String d_wapurl;/**钉钉URL**/
	private String d_appkey;/**钉钉应用key**/
	private String d_appsecret;/**钉钉应用密钥**/
	private String dbid;/**数据库连接标识**/
	public String getWapno() {
		return wapno;
	}
	public String getDbid() {
		return dbid;
	}
	public void setDbid(String dbid) {
		this.dbid = dbid;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public void setWapno(String wapno) {
		this.wapno = wapno;
	}
	public String getWapname() {
		return wapname;
	}
	public void setWapname(String wapname) {
		this.wapname = wapname;
	}
	public String getW_applyid() {
		return w_applyid;
	}
	public void setW_applyid(String w_applyid) {
		this.w_applyid = w_applyid;
	}
	public String getW_wapurl() {
		return w_wapurl;
	}
	public void setW_wapurl(String w_wapurl) {
		this.w_wapurl = w_wapurl;
	}
	public String getW_corpid() {
		return w_corpid;
	}
	public void setW_corpid(String w_corpid) {
		this.w_corpid = w_corpid;
	}
	public String getW_appsecret() {
		return w_appsecret;
	}
	public void setW_appsecret(String w_appsecret) {
		this.w_appsecret = w_appsecret;
	}
	public String getD_applyid() {
		return d_applyid;
	}
	public void setD_applyid(String d_applyid) {
		this.d_applyid = d_applyid;
	}
	public String getD_wapurl() {
		return d_wapurl;
	}
	public void setD_wapurl(String d_wapurl) {
		this.d_wapurl = d_wapurl;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getD_appkey() {
		return d_appkey;
	}
	public void setD_appkey(String d_appkey) {
		this.d_appkey = d_appkey;
	}
	public String getD_appsecret() {
		return d_appsecret;
	}
	public void setD_appsecret(String d_appsecret) {
		this.d_appsecret = d_appsecret;
	}
}
