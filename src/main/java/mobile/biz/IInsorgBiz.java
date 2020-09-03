package mobile.biz;

import mobile.entity.AccessToken;
import mobile.entity.Insorg;
import mobile.entity.Inswaplist;

public interface IInsorgBiz {

	/**
	 * 跟新或修改企业信息
	 * @param insorg 企业信息
	 * @return 返回企业唯一UUID
	 * @throws Exception
	 */
	public String upOrInInsorg(Insorg insorg)  throws Exception;
	
	/**
	 *  添加修改企业应用信息
	 * @param insorg 企业信息
	 * @return 返回企业唯一UUID
	 * @throws Exception
	 */
	public int upOrInInsorgList(Inswaplist inswaplist)  throws Exception;
	/**
	 * 删除企业应用信息
	 * @param insorg 企业信息 
	 * @throws Exception
	 */
	public int delInsorgList(String insorgUUID)  throws Exception;

	/**
	 * 获取钉钉应用logo
	 * @param appid			应用id
	 * @param corpid		企业唯一码 
	 * @return
	 * @throws Exception
	 */
	public String getDDLogo(String appid,String corpid) throws Exception;
	
	/**
	 * 修改钉钉应用logo
	 * @param appid			应用id
	 * @param corpid		企业唯一码 
	 * @param logo 			logo
	 * @return
	 * @throws Exception
	 */
	public void upDDLogo(String appid,String corpid,String logo) throws Exception;
	
	/**
	 * 获取微信应用 密钥信息 用来获取授权信息
	 * @param acckey 
	 * @return
	 * @throws Exception
	 */
	public AccessToken getWXInsAcc(String acckey) throws Exception;
	
	/**
	 * 获取钉钉应用 密钥信息 用来获取授权信息
	 * @param acckey 
	 * @return
	 * @throws Exception
	 */
	public AccessToken getDDInsAcc(String acckey) throws Exception;
	
	/** 
	 * 
	 * 查询微信或钉钉应用对应的平台应用编码
	 * @param ddscm
	 * @param appid
	 * @return
	 * @throws Exception
	 */
	public String selBipAppid(String ddscm,String appid) throws Exception;
	
	/**
	 * 根据企业corpid 和应用id 获取key
	 * @param corpid
	 * @return
	 * @throws Exception
	 */
	public String getWorkKey(String corpid,String bipAppId) throws Exception;
	/**
	 * 根据企业corpid 获取同步用户的应用
	 * @param corpid
	 * @return
	 * @throws Exception
	 */
	public String getDDUserAppID(String corpid) throws Exception;
}


