package mobile.entity;

import java.util.Date;

public class Message extends BasePojo {
	private Integer id;/** 主键  777**/
	private String title;/** 标题 **/
	private String name;/** 提交人 姓名（编号） **/
	private String spweixinid;/** 审批人 微信企业号 UserID **/
	private String spname;/** 审批人 姓名（编号） **/
	private String content; /** 审批内容 **/
	private String contenthuanhang; /** 审批内容 换行**/
	private String yjcontent;/** 审批意见 **/
	private Date tjtime;/** 提交时间 777**/
	private String tjtimeStr;
	private Date sptime;/** 审批时间 **/
	private String documentsid;/** 单据编号 **/
	private String documentstype;/** 单据类型 **/
	private String tablename; /** 单据表名 **/
	private Integer state; /** 状态 0未审批  1审批通过  2驳回申请        BIP  1提交  -1退回 0设置为未读状态**/      
	private String sbuId;/**业务号**/
	private String gs;/**公司**/
	private String tuUrl="img/ren.png";/**微信头像777**/
	private String dbid;/**数据库标识**/
	private String scm;/**公司**/
	private String department;/**部门**/
	private String state0;/**来源状态**/     
	private String state1;/**目标状态**/
	private String state1wenzi;/**目标状态 文字**/
	private String state2;/**下一状态**/
	private String spname2;/**下一审批人**/
	private String w_appid;/**应用id**/
	private String wapno;/**平台定义应用id**/
	private String w_corpid;/**微信企业号id**/
	private String d_appid;/**应用id**/
	private String d_corpid;/**钉钉企业号id**/
	private String serverurl;/**来源服务器地址**/
	private String smake;/**制单人**/
	private String submit;/**提交人**/
	private String lastState;/**documentsid 本单据的最后一个流程节点状态  **/
	
	public String getTuUrl() {
		return tuUrl;
	}
	public String getTjtimeStr() {
		return tjtimeStr;
	}
	public void setTjtimeStr(String tjtimeStr) {
		this.tjtimeStr = tjtimeStr;
	}
	public String getState2() {
		return state2;
	}
	public void setState2(String state2) {
		this.state2 = state2;
	}
	public String getSpname2() {
		return spname2;
	}
	public void setSpname2(String spname2) {
		this.spname2 = spname2;
	}
	public String getState0() {
		return state0;
	}
	public void setState0(String state0) {
		this.state0 = state0;
	}
	public String getScm() {
		return scm;
	}
	public void setScm(String scm) {
		this.scm = scm;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDbid() {
		return dbid;
	}
	public void setDbid(String dbid) {
		this.dbid = dbid;
	}
	public void setTuUrl(String tuUrl) {
		this.tuUrl = tuUrl;
	}
	public String getState1() {
		return state1;
	}
	public void setState1(String state1) {
		this.state1 = state1;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpweixinid() {
		return spweixinid;
	}
	public void setSpweixinid(String spweixinid) {
		this.spweixinid = spweixinid;
	}
	public String getSpname() {
		return spname;
	}
	public void setSpname(String spname) {
		this.spname = spname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getYjcontent() {
		return yjcontent;
	}
	public void setYjcontent(String yjcontent) {
		this.yjcontent = yjcontent;
	}
	public Date getTjtime() {
		return tjtime;
	}
	public void setTjtime(Date tjtime) {
		this.tjtime = tjtime;
	}
	public Date getSptime() {
		return sptime;
	}
	public void setSptime(Date sptime) {
		this.sptime = sptime;
	}
	public String getDocumentsid() {
		return documentsid;
	}
	public void setDocumentsid(String documentsid) {
		this.documentsid = documentsid;
	}
	public String getDocumentstype() {
		return documentstype;
	}
	public void setDocumentstype(String documentstype) {
		this.documentstype = documentstype;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getSbuId() {
		return sbuId;
	}
	public void setSbuId(String sbuId) {
		this.sbuId = sbuId;
	}
	public String getGs() {
		return gs;
	}
	public void setGs(String gs) {
		this.gs = gs;
	}
	public String getContenthuanhang() {
		return contenthuanhang;
	}
	public void setContenthuanhang(String contenthuanhang) {
		this.contenthuanhang = contenthuanhang;
	}
	public String getState1wenzi() {
		return state1wenzi;
	}
	public void setState1wenzi(String state1wenzi) {
		this.state1wenzi = state1wenzi;
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
	
	public String getLastState() {
		return lastState;
	}
	public void setLastState(String lastState) {
		this.lastState = lastState;
	}
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
	public Message copy(){
		Message mm = new Message();
		mm.id=this.id;/** 主键  777**/
		mm.title=this.title;/** 标题 **/
		mm.name=this.name;/** 提交人 姓名（编号） **/
		mm.spweixinid=this.spweixinid;/** 审批人 微信企业号 UserID **/
		mm.spname=this.spname;/** 审批人 姓名（编号） **/
		mm.content=this.content; /** 审批内容 **/
		mm.contenthuanhang=this.contenthuanhang; /** 审批内容 换行**/
		mm.yjcontent=this.yjcontent;/** 审批意见 **/
		mm.tjtime=this.tjtime;/** 提交时间 777**/
		mm.sptime=this.sptime;/** 审批时间 **/
		mm.documentsid=this.documentsid;/** 单据编号 **/
		mm.documentstype=this.documentstype;/** 单据类型 **/
		mm.tablename=this.tablename; /** 单据表名 **/
		mm.state=this.state; /** 状态 0未审批  1审批通过  2驳回申请        BIP  1提交  -1退回 0设置为未读状态**/      
		mm.sbuId=this.sbuId;/**业务号**/
		mm.gs=this.gs;/**公司**/
		mm.tuUrl=this.tuUrl;/**微信头像777**/
		mm.dbid=this.dbid;/**数据库标识**/
		mm.scm=this.scm;/**公司**/
		mm.department=this.department;/**部门**/
		mm.state0=this.state0;/**来源状态**/     
		mm.state1=this.state1;/**目标状态**/
		mm.state1wenzi=this.state1wenzi;/**目标状态 文字**/
		mm.state2=this.state2;/**下一状态**/
		mm.spname2=this.spname2;/**下一审批人**/
		mm.w_appid=this.w_appid;/**应用id**/
		mm.d_appid=this.d_appid;/**应用id**/
		mm.wapno=this.wapno;/**平台定义应用id**/
		mm.w_corpid=this.w_corpid;/**微信企业号id**/
		mm.d_corpid=this.d_corpid;/**微信企业号id**/
		mm.serverurl=this.serverurl;/**来源服务器地址**/
		mm.smake = this.smake;/**制单人**/
		mm.submit = this.submit;/**提交人**/
		mm.tjtimeStr = this.tjtimeStr;
		mm.lastState = this.lastState;
		return mm;
	}
	public String getSmake() {
		return smake;
	}
	public void setSmake(String smake) {
		this.smake = smake;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getD_corpid() {
		return d_corpid;
	}
	public void setD_corpid(String d_corpid) {
		this.d_corpid = d_corpid;
	}
	public String toString(){
		return "{\"id\":"+"\""+id+"\","+"\"title\":"+"\""+title+"\","+"\""
				+ "name\":"+"\""+name+"\","+"\"spweixinid\":"+"\""+spweixinid
				+"\","+"\"spname\":"+"\""+spname+"\","+"\"content\":"+"\""+content+"\","
				+"\"yjcontent\":"+"\""+yjcontent+"\","+"\"tjtime\":"+"\""+tjtime+"\","
				+"\"sptime\":"+"\""+sptime+"\","+"\"documentsid\":"+"\""+documentsid+"\","
				+"\"documentstype\":"+"\""+documentstype+"\","+"\"tablename\":"+"\""+tablename+"\","
				+"\"state\":"+"\""+state+"\","+"\"sbuId\":"+"\""+sbuId+"\","+"\"gs\":"+"\""+gs+"\","
				+"\"tuUrl\":"+"\""+tuUrl+"\","+"\"dbid\":"+"\""+dbid+"\","+"\"scm\":"+"\""+scm+"\","
				+"\"department\":"+"\""+department+"\","+"\"state0\":"+"\""+state0+"\","+"\"state1\":"+"\""
				+state1+"\","+"\"state2\":"+"\""+state2+"\","+"\"spname2\":"+"\""+spname2+"\","+"\"w_appid\":"+"\""+w_appid+"\","+"\"d_appid\":"+"\""+d_appid+"\","
				+"\"wapno\":"+"\""+wapno+"\","+"\"w_corpid\":"+"\""+w_corpid+"\","+"\"serverurl\":"+"\""+serverurl+"\","
				+"\"submit\":"+"\""+submit+"\","+"\"tjtimeStr\":"+"\""+tjtimeStr+"\","+"\"lastState\":"+"\""+lastState+"\","+"\"smake\":"+"\""+smake+"\","+"\"d_corpid\":"+"\""+d_corpid+"\"}";
	}
}