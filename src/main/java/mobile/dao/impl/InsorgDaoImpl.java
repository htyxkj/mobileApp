package mobile.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mobile.dao.BaseDao;
import mobile.dao.IInsorgDao;
import mobile.entity.AccessToken;
import mobile.entity.Insorg;
import mobile.entity.Inswaplist;

public class InsorgDaoImpl extends BaseDao<Insorg> implements IInsorgDao {
	/**
	 * 跟新或修改企业信息
	 * @param insorg 企业信息
	 * @return 返回企业唯一UUID
	 * @throws Exception
	 */
	public String upOrInInsorg(Insorg insorg) throws Exception {
		String uuid = selectScm(insorg);
		if(uuid == null) {
			insertScm(insorg);
			uuid = insorg.getUuid();
		}else {
			updateScm(insorg);
		}
		return uuid;
	}
	
	//查询公司微信/钉钉 配置信息是否存在  存在修改   不存在添加
	private String selectScm(Insorg insorg) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null; 
		String sql="select uuid from insorg where orgcode=? and c_corp=? and (w_corpid=? or d_corpid = ?)";
		String uuid = null;
		try{
			statement=connection.prepareStatement(sql);
			statement.setString(1,insorg.getOrgcode());
			statement.setString(2,insorg.getC_corp());
			statement.setString(3,insorg.getW_corpid());
			statement.setString(4,insorg.getD_corpid());
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				uuid = resultSet.getString("uuid"); 
			} 
			return uuid;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			closeAll(connection, statement, resultSet);
		}
	}

	//添加微信/钉钉配置信息
	private int insertScm(Insorg insorg) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		int rows=0;
		String sql="INSERT INTO insorg (c_corp,orgcode,w_corpid,w_secret,w_trusturl,d_corpid,d_secret,d_trusturl,serverurl,uuid) VALUES (?,?,?,?,?,?,?,?,?,?);";
		try{
			statement=connection.prepareStatement(sql);
			statement.setString(1,insorg.getC_corp());
			statement.setString(2,insorg.getOrgcode());
			statement.setString(3,insorg.getW_corpid());
			statement.setString(4,insorg.getW_secret());
			statement.setString(5,insorg.getW_trusturl());
			statement.setString(6,insorg.getD_corpid());
			statement.setString(7,insorg.getD_secret());
			statement.setString(8,insorg.getD_trusturl());
			statement.setString(9,insorg.getServerurl());
			statement.setString(10,insorg.getUuid());
			rows=statement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			rows=-1;
		}finally{
			closeAll(connection, statement, null);
		}
		return rows;
	}

	//修改微信配置信息
	private int updateScm(Insorg insorg) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		String sql="update  insorg set w_corpid=?,w_secret=?,w_trusturl=?,d_corpid=?,d_secret=?,d_trusturl=? ,serverurl=?  where orgcode=? and c_corp=? and (w_corpid=? or d_corpid=? )";
		int row=0;
		try{
			statement=connection.prepareStatement(sql);
			statement.setString(1,insorg.getW_corpid());
			statement.setString(2,insorg.getW_secret());
			statement.setString(3,insorg.getW_trusturl());
			statement.setString(4,insorg.getD_corpid());
			statement.setString(5,insorg.getD_secret());
			statement.setString(6,insorg.getD_trusturl());
			statement.setString(7,insorg.getServerurl());
			statement.setString(8,insorg.getOrgcode());
			statement.setString(9,insorg.getC_corp());
			statement.setString(10,insorg.getW_corpid());
			statement.setString(11,insorg.getD_corpid());
			row=statement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			row=-1;
		}finally{
			closeAll(connection, statement, null);
		}
		return row;
	}

	
	/**
	 * 跟新或修改企业应用信息
	 * @param insorg 企业信息
	 * @return 返回企业唯一UUID
	 * @throws Exception
	 */
	public int upOrInInsorgList(Inswaplist inswaplist) throws Exception {
//		int num = deleAPP(inswaplist.getUuid());
		int row = insertApp(inswaplist);
		return row;
	}
	/**
	 * 删除企业应用信息
	 * @param insorg 企业信息 
	 * @throws Exception
	 */
	public int delInsorgList(String insorgUUID)  throws Exception{
		int num = deleAPP(insorgUUID);
		return num;
	}
	//删除微信/钉钉APP配置信息  
	private int deleAPP(String uuid){
		Connection connection=getConnection();
		PreparedStatement statement=null;
		String sql="delete from inswaplist where uuid=?";
		int row=0;
		try{
			statement=connection.prepareStatement(sql);
			statement.setString(1, uuid); 
			row=statement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			row=-1;
		}finally{
			closeAll(connection, statement, null);
		}
		return row;
	}
	//查找微信/钉钉APP配置信息     存在进行修改  不存在进行添加
	public int selectApp(Inswaplist list){
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		int num = 0 ;
		String sql="select count(uuid) num from inswaplist where wapno=? and orgcode=? and (w_corpid=? or d_applyid =?)";
		try{
			statement=connection.prepareStatement(sql);
			statement.setString(1, list.getWapno());
			statement.setString(2, list.getOrgcode());
			statement.setString(3, list.getW_corpid());
			statement.setString(4, list.getD_applyid());
			resultSet=statement.executeQuery();
			if(resultSet.next()){ 
				num=resultSet.getInt("num");
			}
			return num;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}finally{
			closeAll(connection, statement, resultSet);
		}
	}
		//修改微信APP配置信息
	public int updateApp(Inswaplist list){
		Connection connection=getConnection();
		PreparedStatement statement=null;
		String sql="update  inswaplist set w_corpid=?,w_applyid=?,w_wapurl=?,w_appsecret=?,dbid=?,d_applyid=?,d_appkey=?,d_appsecret=? where wapno=? and orgcode=? and (w_corpid=? or d_applyid=?);";
		int row=0;
		try{
			statement=connection.prepareStatement(sql);
			statement.setString(1, list.getW_corpid());
			statement.setString(2, list.getW_applyid());
			statement.setString(3, list.getW_wapurl());
			statement.setString(4, list.getW_appsecret());
			statement.setString(5, list.getDbid());
			statement.setString(6, list.getD_applyid());
			statement.setString(7, list.getD_appkey());
			statement.setString(8, list.getD_appsecret());
			statement.setString(9, list.getWapno());
			statement.setString(10, list.getOrgcode());
			statement.setString(11, list.getW_corpid() );
			statement.setString(12, list.getD_applyid());
			row=statement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			row=-1;
		}finally{
			closeAll(connection, statement, null);
		}
		return row;
	} 
	//添加微信APP配置信息
	public int insertApp(Inswaplist list){
		Connection connection=getConnection();
		PreparedStatement statement=null;
		String sql="insert into  inswaplist  (orgcode,w_applyid,w_wapurl,wapname,wapno,w_corpid,w_appsecret,dbid,d_applyid,d_appkey,d_appsecret,uuid) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		int row=0;
		try{
			statement=connection.prepareStatement(sql);
			statement.setString(1, list.getOrgcode());
			statement.setString(2, list.getW_applyid());
			statement.setString(3, list.getW_wapurl());
			statement.setString(4, list.getWapname());
			statement.setString(5, list.getWapno());
			statement.setString(6, list.getW_corpid());
			statement.setString(7, list.getW_appsecret());
			statement.setString(8, list.getDbid());
			statement.setString(9, list.getD_applyid());
			statement.setString(10, list.getD_appkey());
			statement.setString(11, list.getD_appsecret());
			statement.setString(12, list.getUuid());
			row=statement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			row=-1;
		}finally{
			closeAll(connection, statement, null);
		}
		return row;
	}

	
	/**
	 * 获取钉钉应用logo
	 * @param appid			应用id
	 * @param corpid		企业唯一码 
	 * @return
	 * @throws Exception
	 */
	public String getDDLogo(String appid, String corpid) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String d_logo = null;
		try { 
			String sql = "select d_logo from Inswaplist l inner join insorg i on i.uuid= l.uuid where i.d_corpid = ? and l.d_applyid = ?";
			statement=connection.prepareStatement(sql);
			statement.setString(1, corpid);
			statement.setString(2, appid);
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				d_logo=resultSet.getString("d_logo");
			}
			return d_logo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			closeAll(connection, statement, resultSet);
		} 
	}

	/**
	 * 修改钉钉应用logo
	 * @param appid			应用id
	 * @param corpid		企业唯一码 
	 * @return
	 * @throws Exception
	 */
	public void upDDLogo(String appid, String corpid,String logo) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null; 
		String sql="update Inswaplist set d_logo = ? where uuid = (select uuid from insorg where d_corpid =?) and d_applyid=?";
		try{ 
			statement=connection.prepareStatement(sql);
			statement.setString(1, logo);
			statement.setString(2, corpid);
			statement.setString(3, appid);
			statement.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
	}
	/**
	 * 获取应用 密钥信息 用来获取授权信息
	 * @param acckey 
	 * @return
	 * @throws Exception
	 */
	public AccessToken getWXInsAcc(String acckey) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		AccessToken acc=null;
		String sql="select uuid,orgcode,w_corpid,w_applyid,w_appsecret,d_applyid,dbid from inswaplist where w_corpid = '"+acckey+"'";
		try{
			statement=connection.prepareStatement(sql);  
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				acc=new AccessToken();
				acc.setCompanyId(resultSet.getString("orgcode"));//公司标识
				acc.setW_applyId(resultSet.getString("w_applyid"));//微信应用id
				acc.setW_corpIDid(resultSet.getString("w_corpid"));//微信企业号标识
				acc.setW_secret(resultSet.getString("w_appsecret"));//微信应用秘钥标识
				String[] arr=this.geturl(resultSet.getString("uuid"));
				acc.setDomainName(arr[0]);
				acc.setServerurl(arr[1]);
				acc.setDbid(resultSet.getString("dbid")); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return acc;
	}  
	/**
	 * 获取应用 密钥信息 用来获取授权信息
	 * @param acckey 
	 * @return
	 * @throws Exception
	 */
	public AccessToken getDDInsAcc(String acckey) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null; 
		AccessToken acc=null;
		String sql="select l.uuid,l.orgcode,l.d_applyid,l.d_appkey,l.d_appsecret,l.dbid,i.d_corpid,i.d_secret,i.d_trusturl,i.serverurl from inswaplist l inner join insorg i on i.uuid = l.uuid where l.d_appkey is not null and  l.d_appkey= '"+acckey+"'";
//		String sql="select uuid,orgcode,d_corpid,d_secret,d_trusturl,serverurl from insorg where d_corpid = '"+acckey+"'";
		try{
			statement=connection.prepareStatement(sql);  
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				acc=new AccessToken();
				acc.setCompanyId(resultSet.getString("orgcode"));//公司标识
				acc.setD_corpIDid(resultSet.getString("d_corpid"));//钉钉企业号标识
				acc.setD_secret(resultSet.getString("d_secret"));//钉钉应用秘钥标识
				String arr=this.getdbid(resultSet.getString("uuid"));
				acc.setDomainName(resultSet.getString("d_trusturl"));
				acc.setServerurl(resultSet.getString("serverurl"));
				acc.setD_appkey(resultSet.getString("d_appkey"));
				acc.setD_appsecret(resultSet.getString("d_appsecret"));
				acc.setDbid(arr);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return acc;
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
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null; 
		String sql="select wapno from Inswaplist l inner join insorg i on l.uuid=i.uuid where i.d_corpid = ? and l.d_applyid=?";
		String bipApp="";
		try{ 
			statement=connection.prepareStatement(sql);
			statement.setString(1, ddscm);
			statement.setString(2, appid);
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				bipApp=resultSet.getString("wapno");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return bipApp;
	}  
	
//	根据uuid获取dbid
	public String getdbid(String uuid){
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String  arr =null;
		String sql="select dbid from inswaplist where uuid=?";
		try{
			statement=connection.prepareStatement(sql);  
			statement.setString(1,uuid);
			resultSet=statement.executeQuery();
			if(resultSet.next()){ 
				arr = resultSet.getString("dbid");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return arr;
	}
	//根据uuid获取信息访问地址，信息来源地址
	public String[] geturl(String uuid){
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String[] arr =null;
		String sql="select w_trusturl,serverurl,d_corpid,d_secret from insorg where uuid=?";
		try{
			statement=connection.prepareStatement(sql);  
			statement.setString(1,uuid);
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				 arr=new String[4];
				 arr[0]=resultSet.getString("w_trusturl");
				 arr[1]=resultSet.getString("serverurl");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return arr;
	}

	/**
	 * 根据企业corpid 应用id 获取key，
	 * @param corpid
	 * @return
	 * @throws Exception
	 */
	public String getWorkKey(String corpid,String bipAppId) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String key =null;
		String sql= ""; 
		sql="select l.d_appkey from inswaplist l inner join insorg i on i.uuid = l.uuid where l.d_appkey is not null and l.d_appkey <> '' and i.d_corpid='"+corpid+"' and l.d_applyid='"+bipAppId+"'";
		try{
			statement=connection.prepareStatement(sql);   
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				key = resultSet.getString("d_appkey");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return key;
	}

	/**
	 * 根据企业corpid 获取同步用户的应用
	 * @param corpid
	 * @return
	 * @throws Exception
	 */
	public String getDDUserAppID(String corpid) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String key =null;
		String sql= "select l.d_applyid  from  inswaplist l inner join insorg i on i.uuid = l.uuid where  i.d_corpid = ? and l.wapno='00'"; 
		try{
			statement=connection.prepareStatement(sql); 
			statement.setString(1, corpid);
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				key = resultSet.getString("d_applyid");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return key;
	}
}
