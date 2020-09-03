package mobile.entity;

import java.util.Date;

public class AccessToken {
    
	private volatile String w_accessToken;//微信 获取到的凭证  
	private volatile long w_expiresTime;//微信凭证有效时间，单位：秒  
	private volatile String d_accessToken;//钉钉 获取到的凭证  
	private volatile long d_expiresTime;//钉钉凭证有效时间，单位：秒
    private String companyId;//公司标识
    private String w_applyId;//微信应用id
	private String w_corpIDid;//微信企业号标识
	private String w_secret;//微信管理组标识
	private String d_applyId;//钉钉应用id
	private String d_corpIDid;//钉钉企业号标识
	private String d_appkey;//钉钉应用key
	private String d_appsecret;//钉钉应用秘钥
	private String d_secret;//钉钉管理组标识
	private String domainName;//访问域名
	private String serverurl;/**来源服务器地址**/
	private String dbid;/** 数据库访问编码 **/
	private Date createTime;//密钥获取时间
	
	public String getW_accessToken() {
		return w_accessToken;
	}
	public void setW_accessToken(String w_accessToken) {
		this.w_accessToken = w_accessToken;
	}
	public long getW_expiresTime() {
		return w_expiresTime;
	}
	public void setW_expiresTime(long w_expiresTime) {
		this.w_expiresTime = w_expiresTime;
	}
	public String getD_accessToken() {
		return d_accessToken;
	}
	public void setD_accessToken(String d_accessToken) {
		this.d_accessToken = d_accessToken;
	}
	public long getD_expiresTime() {
		return d_expiresTime;
	}
	public void setD_expiresTime(long d_expiresTime) {
		this.d_expiresTime = d_expiresTime;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getW_applyId() {
		return w_applyId;
	}
	public void setW_applyId(String w_applyId) {
		this.w_applyId = w_applyId;
	}
	public String getW_corpIDid() {
		return w_corpIDid;
	}
	public void setW_corpIDid(String w_corpIDid) {
		this.w_corpIDid = w_corpIDid;
	}
	public String getW_secret() {
		return w_secret;
	}
	public void setW_secret(String w_secret) {
		this.w_secret = w_secret;
	}
	public String getD_applyId() {
		return d_applyId;
	}
	public void setD_applyId(String d_applyId) {
		this.d_applyId = d_applyId;
	}
	public String getD_corpIDid() {
		return d_corpIDid;
	}
	public void setD_corpIDid(String d_corpIDid) {
		this.d_corpIDid = d_corpIDid;
	}
	public String getD_secret() {
		return d_secret;
	}
	public void setD_secret(String d_secret) {
		this.d_secret = d_secret;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getServerurl() {
		return serverurl;
	}
	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}
	public String getDbid() {
		return dbid;
	}
	public void setDbid(String dbid) {
		this.dbid = dbid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	//是否失效
	public boolean ddIsinvalid() {
		long startTime = this.createTime.getTime();
		long endTime = new Date().getTime();
		long xc = ((endTime - startTime)/1000)+200;//相差秒数
		if(xc > d_expiresTime) {
			return true;
		}
		return false;
	}
	//是否失效
	public boolean wxIsinvalid() {
		long startTime = this.createTime.getTime();
		long endTime = new Date().getTime();
		long xc = ((endTime - startTime)/1000)+200;//相差秒数
		if(xc > w_expiresTime) {
			return true;
		}
		return false;
	}
}
