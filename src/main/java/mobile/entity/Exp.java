package mobile.entity;

import java.util.Date;

public class Exp {
	private String sid_w;//微信端单据编号
	private String sid_b;//平台单据编号
	private Date mkdate;//制单日期
	private String state;//状态
	private String sopr;//报销人
	private String smake;//制单人
	private String p_account;//收款人账号
	private String p_bank;//收款人开户行
	private String remark;//报销说明
	private Double fcy;//金额
	private Date hpdate;//日期
	private String bxlb;//报销类别
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
	public String getSopr() {
		return sopr;
	}
	public void setSopr(String sopr) {
		this.sopr = sopr;
	}
	public String getSmake() {
		return smake;
	}
	public void setSmake(String smake) {
		this.smake = smake;
	}
	public String getP_account() {
		return p_account;
	}
	public void setP_account(String p_account) {
		this.p_account = p_account;
	}
	public String getP_bank() {
		return p_bank;
	}
	public void setP_bank(String p_bank) {
		this.p_bank = p_bank;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getFcy() {
		return fcy;
	}
	public void setFcy(Double fcy) {
		this.fcy = fcy;
	}
	public Date getMkdate() {
		return mkdate;
	}
	public void setMkdate(Date mkdate) {
		this.mkdate = mkdate;
	}
	public Date getHpdate() {
		return hpdate;
	}
	public void setHpdate(Date hpdate) {
		this.hpdate = hpdate;
	}
	public String getBxlb() {
		return bxlb;
	}
	public void setBxlb(String bxlb) {
		this.bxlb = bxlb;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String toString(){
		return "{\"sid_w\":"+"\""+sid_w+"\","+"\"sid_b\":"+"\""+sid_b+"\","+"\"mkdate\":"+"\""+mkdate+"\""
				+ ","+"\"state\":"+"\""+state+"\","+"\"sopr\":"+"\""+sopr+"\","+"\"smake\":"+"\""+smake+"\""
				+ ","+"\"p_account\":"+"\""+p_account+"\","+"\"p_bank\":"+"\""+p_bank+"\","+"\"remark\":"+"\""+remark+"\""
				+ ","+"\"fcy\":"+"\""+fcy+"\","+"\"hpdate\":"+"\""+hpdate+"\","+"\"bxlb\":"+"\""+bxlb+"\""
				+ ","+"\"corpid\":"+"\""+corpid+"\"}";
	}
	
}