package mobile.entity;

public class Expa {
	private String sid_w;//微信单据编号
	private String sid_b;//平台单据编号
	private String cid;//序号
	private String idxno;//发票项目
	private String inv_no;//发票编号
	private String deduction;//可抵扣否
	private String inv_type;//发票类别
	private String sp_tax;//特殊票别
	private Double fcy;//金额
	private Double addtaxrt;//增值税率
	private Double addtax;//增值税金
	private Double rmbhs;//无税金额
	private String remark;//摘要
	private String corpid;//微信企业号标识
	public String getSid_w() {
		return sid_w;
	}
	public void setSid_w(String sid_w) {
		this.sid_w = sid_w;
	}
	public String getSid_b() {
		return sid_b;
	}
	public void setSid_b(String sid_b) {
		this.sid_b = sid_b;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getIdxno() {
		return idxno;
	}
	public void setIdxno(String idxno) {
		this.idxno = idxno;
	}
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String inv_no) {
		this.inv_no = inv_no;
	}
	public String getDeduction() {
		return deduction;
	}
	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}
	public String getInv_type() {
		return inv_type;
	}
	public void setInv_type(String inv_type) {
		this.inv_type = inv_type;
	}
	public String getSp_tax() {
		return sp_tax;
	}
	public void setSp_tax(String sp_tax) {
		this.sp_tax = sp_tax;
	}
	public Double getFcy() {
		return fcy;
	}
	public void setFcy(Double fcy) {
		this.fcy = fcy;
	}
	public Double getAddtaxrt() {
		return addtaxrt;
	}
	public void setAddtaxrt(Double addtaxrt) {
		this.addtaxrt = addtaxrt;
	}
	public Double getAddtax() {
		return addtax;
	}
	public void setAddtax(Double addtax) {
		this.addtax = addtax;
	}
	public Double getRmbhs() {
		return rmbhs;
	}
	public void setRmbhs(Double rmbhs) {
		this.rmbhs = rmbhs;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String toString(){
		return "{\"sid_w\":"+"\""+sid_w+"\",\"sid_b\":"+"\""+sid_b+"\",\"cid\":"+"\""+cid+"\""
			+ ",\"idxno\":"+"\""+idxno+"\",\"inv_no\":"+"\""+inv_no+"\",\"deduction\":"+"\""+deduction+"\""
			+ ",\"inv_type\":"+"\""+inv_type+"\",\"sp_tax\":"+"\""+sp_tax+"\",\"fcy\":"+"\""+fcy+"\""
			+ ",\"addtaxrt\":"+"\""+addtaxrt+"\",\"addtax\":"+"\""+addtax+"\",\"rmbhs\":"+"\""+rmbhs+"\""
			+ ",\"remark\":"+"\""+remark+"\",\"corpid\":"+"\""+corpid+"\"}";
	}
}