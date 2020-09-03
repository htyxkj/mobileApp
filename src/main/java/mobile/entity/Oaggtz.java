package mobile.entity;

import java.util.Date;

public class Oaggtz  extends BasePojo {
	  private Integer id;//编号
	  private Integer c_corp=0;//集团
	  private String sid;//编码
	  private String slb;//信息类别
	  private String smaker;//发布人
	  private String sorgto;//接收部门
	  private String susr;//接收人
	  private String title;//标题
	  private String content;//内容
	  private Date mkdate;//发布日期
	  private String fj_root;//附件路径
	  private String suri;//附件
	  private String sbuid;//单据类型
	  private Integer state;//状态
	  private String schk;//审核人
	  private Date ckdate;//审核日期
	  private String sorg;//起草部门
	  private String coll_cc;//收藏次数
	  private String scm;//归属公司
	  private String xxgs;//信息归属
	  private String read="";//是否已读
	  private String w_appid;/**微信应用id**/
	  private String d_appid;/**钉钉应用id**/
	  private String wapno;/**平台定义应用id**/
	  private String w_corpid;/**微信企业号id**/
	  private String d_corpid;/**钉钉企业号id**/
	  private String serverurl;/**来源服务器地址**/
	  private String source;//来源项 W:微信移动端,B:BIP客户端
	  private String dbid;/** 数据库编码**/
	public Integer getC_corp() {
		return c_corp;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setC_corp(Integer c_corp) {
		this.c_corp = c_corp;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSlb() {
		return slb;
	}
	public void setSlb(String slb) {
		this.slb = slb;
	}
	public String getSmaker() {
		return smaker;
	}
	public void setSmaker(String smaker) {
		this.smaker = smaker;
	}
	public String getSorgto() {
		return sorgto;
	}
	public void setSorgto(String sorgto) {
		this.sorgto = sorgto;
	}
	public String getSusr() {
		return susr;
	}
	public void setSusr(String susr) {
		this.susr = susr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getMkdate() {
		return mkdate;
	}
	public void setMkdate(Date mkdate) {
		this.mkdate = mkdate;
	}
	public String getFj_root() {
		return fj_root;
	}
	public void setFj_root(String fj_root) {
		this.fj_root = fj_root;
	}
	public String getSuri() {
		return suri;
	}
	public void setSuri(String suri) {
		this.suri = suri;
	}
	public String getSbuid() {
		return sbuid;
	}
	public void setSbuid(String sbuid) {
		this.sbuid = sbuid;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getSchk() {
		return schk;
	}
	public void setSchk(String schk) {
		this.schk = schk;
	}
	public Date getCkdate() {
		return ckdate;
	}
	public void setCkdate(Date ckdate) {
		this.ckdate = ckdate;
	}
	public String getSorg() {
		return sorg;
	}
	public void setSorg(String sorg) {
		this.sorg = sorg;
	}
	public String getColl_cc() {
		return coll_cc;
	}
	public void setColl_cc(String coll_cc) {
		this.coll_cc = coll_cc;
	}
	public String getScm() {
		return scm;
	}
	public void setScm(String scm) {
		this.scm = scm;
	}
	public String getXxgs() {
		return xxgs;
	}
	public void setXxgs(String xxgs) {
		this.xxgs = xxgs;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	} 
	public String getWapno() {
		return wapno;
	}
	public void setWapno(String wapno) {
		this.wapno = wapno;
	}
	public String getW_corpid() {
		return w_corpid;
	}
	public void setW_corpid(String w_corpid) {
		this.w_corpid = w_corpid;
	}
	public String getServerurl() {
		return serverurl;
	}
	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDbid() {
		return dbid;
	}
	public void setDbid(String dbid) {
		this.dbid = dbid;
	}
	public String getD_corpid() {
		return d_corpid;
	}
	public void setD_corpid(String d_corpid) {
		this.d_corpid = d_corpid;
	}
//	public String toString(){
//		return "\"id\":\""+id+"\",\"c_corp\":\""+c_corp+"\",\"sid\":\""+sid+"\",\"slb\":\""+slb+"\","
//				+ "\"smaker\":\""+smaker+"\",\"sorgto\":\""+sorgto+"\",\"susr\":\""+susr+"\",\"title\":\""+title+"\","
//				+ "\"content\":\""+content+"\",\"mkdate\":\""+mkdate+"\",\"fj_root\":\""+fj_root+"\",\"suri\":\""+suri+"\","
//				+ "\"sbuid\":\""+sbuid+"\",\"state\":\""+state+"\",\"schk\":\""+schk+"\",\"ckdate\":\""+ckdate+"\","
//				+ "\"sorg\":\""+sorg+"\",\"coll_cc\":\""+coll_cc+"\",\"scm\":\""+scm+"\",\"xxgs\":\""+xxgs+"\","
//				+ "\"read\":\""+read+"\",\"appid\":\""+w_appid+"\",\"wapno\":\""+wapno+"\",\"w_corpid\":\""+w_corpid+"\","
//				+ "\"serverurl\":\""+serverurl+"\","
//				+ "\"source\":\""+source+"\","+ "\"d_corpid\":\""+d_corpid+"\"";
//		
//	}
	public String getW_appid() {
		return w_appid;
	}
	public void setW_appid(String w_appid) {
		this.w_appid = w_appid;
	}
	public String getD_appid() {
		return d_appid;
	}
	public void setD_appid(String d_appid) {
		this.d_appid = d_appid;
	}
}
