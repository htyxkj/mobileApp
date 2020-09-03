package mobile.biz.impl;

import mobile.biz.IInsorgBiz;
import mobile.dao.IInsorgDao;
import mobile.dao.impl.InsorgDaoImpl;
import mobile.entity.AccessToken;
import mobile.entity.Insorg;
import mobile.entity.Inswaplist;

public class InsorgBizImpl implements IInsorgBiz {

	private IInsorgDao insorgDao = new InsorgDaoImpl(); 
	
	/**
	 * 跟新或修改企业信息
	 * @param insorg 企业信息
	 * @return 返回企业唯一UUID
	 * @throws Exception
	 */
	public String upOrInInsorg(Insorg insorg) throws Exception {
		return insorgDao.upOrInInsorg(insorg);
	}
	
	/**
	 * 跟新或修改企业应用信息
	 * @param insorg 企业信息
	 * @return 返回企业唯一UUID
	 * @throws Exception
	 */
	public int upOrInInsorgList(Inswaplist inswaplist) throws Exception {
		return insorgDao.upOrInInsorgList(inswaplist);
	}
	
	/**
	 * 删除企业应用信息
	 * @param insorg 企业信息 
	 * @throws Exception
	 */
	public int delInsorgList(String insorgUUID)  throws Exception{
		return insorgDao.delInsorgList(insorgUUID);
	}
	/**
	 * 获取钉钉应用logo
	 * @param appid			应用id
	 * @param corpid		企业唯一码 
	 * @return
	 * @throws Exception
	 */
	public String getDDLogo(String appid, String corpid) throws Exception {
		return insorgDao.getDDLogo(appid, corpid);
	}
	/**
	 * 修改钉钉应用logo
	 * @param appid			应用id
	 * @param corpid		企业唯一码 
	 * @return
	 * @throws Exception
	 */
	public void upDDLogo(String appid, String corpid,String logo) throws Exception {
		insorgDao.upDDLogo(appid, corpid,logo);
	}
	
	/**
	 * 获取应用 密钥信息 用来获取授权信息
	 * @param acckey 
	 * @return
	 * @throws Exception
	 */
	public AccessToken getWXInsAcc(String acckey) throws Exception {
		return insorgDao.getWXInsAcc(acckey);
	}
	
	/**
	 * 获取应用 密钥信息 用来获取授权信息
	 * @param acckey 
	 * @return
	 * @throws Exception
	 */
	public AccessToken getDDInsAcc(String acckey) throws Exception {
		return insorgDao.getDDInsAcc(acckey);
	}
	/** 
	 * 
	 * 查询微信或钉钉应用对应的平台应用编码
	 * @param ddscm
	 * @param appid
	 * @return
	 * @throws Exception
	 */
	public String selBipAppid(String ddscm, String appid) throws Exception {
		return insorgDao.selBipAppid(ddscm, appid);
	}

	/**
	 * 根据企业corpid 获取工作台key，用来同步用户
	 * @param corpid
	 * @return
	 * @throws Exception
	 */
	public String getWorkKey(String corpid,String bipAppId) throws Exception {
		return insorgDao.getWorkKey(corpid,bipAppId);
	}

	/**
	 * 根据企业corpid 获取同步用户的应用
	 * @param corpid
	 * @return
	 * @throws Exception
	 */
	public String getDDUserAppID(String corpid) throws Exception {
		return insorgDao.getDDUserAppID(corpid);
	}
	
	
	

}
