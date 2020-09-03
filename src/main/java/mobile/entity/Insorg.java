package mobile.entity;

public class Insorg {
	private String uuid;
	private String c_corp;/**集团编码**/
	private String orgcode;/**公司编码**/
//	private String corpid;/**企业编号**/
	private String w_corpid;/**微信企业号ID**/
	private String w_secret;/**微信secret序列号**/
	private String w_trusturl;/**微信移动端地址串**/
	private String d_corpid;/**钉钉企业号ID**/
	private String d_secret;/**钉钉secret序列号**/
	private String d_trusturl;/**钉钉移动端地址串**/
	private String serverurl;/**信息来源地址**/
	public String getOrgcode() {
		return orgcode;
	}
	public String getC_corp() {
		return c_corp;
	}
	public void setC_corp(String c_corp) {
		this.c_corp = c_corp;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
//	public String getCorpid() {
//		return corpid;
//	}
//	public void setCorpid(String corpid) {
//		this.corpid = corpid;
//	}
	public String getW_corpid() {
		return w_corpid;
	}
	public void setW_corpid(String w_corpid) {
		this.w_corpid = w_corpid;
	}
	public String getW_secret() {
		return w_secret;
	}
	public void setW_secret(String w_secret) {
		this.w_secret = w_secret;
	}
	public String getW_trusturl() {
		return w_trusturl;
	}
	public void setW_trusturl(String w_trusturl) {
		this.w_trusturl = w_trusturl;
	}
	public String getServerurl() {
		return serverurl;
	}
	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}
	public String getD_corpid() {
		return d_corpid;
	}
	public void setD_corpid(String d_corpid) {
		this.d_corpid = d_corpid;
	}
	public String getD_secret() {
		return d_secret;
	}
	public void setD_secret(String d_secret) {
		this.d_secret = d_secret;
	}
	public String getD_trusturl() {
		return d_trusturl;
	}
	public void setD_trusturl(String d_trusturl) {
		this.d_trusturl = d_trusturl;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
