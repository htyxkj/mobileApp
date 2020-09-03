package mobile.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mobile.dao.BaseDao;
import mobile.dao.IMessageDao;
import mobile.entity.Message;
import mobile.util.StringUtil;

public class MessageDaoImpl extends BaseDao<Message> implements IMessageDao {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Logger log = LoggerFactory.getLogger(MessageDaoImpl.class);
	//滚动加载数据每次加载数据数量 --期望值
	private static Integer RESULT_SIZE = 10;
	/**
	 * 统计某人的待审任务数量 被驳回任务数量
	 * 
	 * @param spweixinid
	 *            某人的微信编号
	 * @param state
	 *            状态 (0:待审,2:驳回)
	 * @param w_corpid
	 *            企业号标识
	 * @return 统计的记录数
	 */

	public Integer showNum(String spweixinid, String state, String w_corpid) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int num = 0;
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间
		ca.add(Calendar.MONTH, -1); // 月份减1
		Date lastMonth = ca.getTime(); // 结果 
		String tjtime = sdf.format(lastMonth);
		String sql = "select count(id) countid from  message where spweixinid='"
				+ spweixinid
				+ "' and state="
				+ state
				+ " and ( w_corpid='"
				+ w_corpid + "' or d_corpid= '"+w_corpid+"') and tjtime >='"+tjtime+"'";
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				num = resultSet.getInt("countid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, resultSet);
		}
		return num;
	}

	/**
	 * 根据编号查询审批内容
	 * 
	 * @param keyid
	 *            编号
	 * @return message
	 */
	public Message show(String keyid) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Message data = null;
		String sql = "select * from  message where id=" + keyid;
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				data = new Message();
				data.setId(resultSet.getInt("id"));//主键 
				data.setTitle(resultSet.getString("title"));//标题 
				data.setName(resultSet.getString("name"));//提交人 姓名（编号） 
				data.setSpweixinid(resultSet.getString("spweixinid"));//审批人 微信企业号 UserID
				data.setSpname(resultSet.getString("spname"));//审批人 姓名（编号）
				//data.setSpname2(resultSet.getString("spname2"));/** 审批人// 姓名（编号） **/
				data.setContent(resultSet.getString("content"));//审批内容 
				data.setYjcontent(resultSet.getString("yjcontent"));//审批意见
				data.setTjtime(sdf.parse(resultSet.getString("tjtime")));// 提交时间 
				if (resultSet.getString("sptime") != null) {
					data.setSptime(sdf.parse(resultSet.getString("sptime")));// 审批时间 
				}
				data.setDocumentsid(resultSet.getString("documentsid"));//单据编号 **/
				data.setDocumentstype(resultSet.getString("documentstype"));//单据类型 **/
				data.setTablename(resultSet.getString("tablename"));//单据表名 **/
				data.setSbuId(resultSet.getString("sbuid"));//业务号 **/
				data.setState(resultSet.getInt("state"));//状态 0未审批 1审批通过 2驳回申请 **/
				data.setState0(resultSet.getString("state0"));//状态 **/
				data.setState1(resultSet.getString("state1"));//状态 **/
				// data.setState2(resultSet.getString("state2"));/** 状态**/
				data.setDbid(resultSet.getString("dbid"));//数据库标识 **/
				data.setGs(resultSet.getString("scm"));//公司 **/
				data.setScm(resultSet.getString("scm"));//公司 **/
				data.setDepartment(resultSet.getString("department"));// 部门 **/
				data.setW_appid(resultSet.getString("w_appid"));//微信应用id **/
				data.setD_appid(resultSet.getString("d_appid"));//钉钉应用id
				data.setWapno(resultSet.getString("wapno"));//平台定义的应用id **/
				data.setW_corpid(resultSet.getString("w_corpid"));//微信企业号id **/
				data.setD_corpid(resultSet.getString("d_corpid"));//钉钉企业号id **/
				data.setSmake(resultSet.getString("smake"));//制单人
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, resultSet);
		}
		return data;
	}

	
	/**
	 * 查询符合条件的单据审批记录
	 * 
	 * @param documentsid 单据id
	 * @param w_corpid 微信企业号标识
	 * @param d_corpid 钉钉企业号标识
	 * @return List<Message>
	 */
	public List<Message> showSPJL(String documentsid, String w_corpid, String d_corpid) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Message> list = new ArrayList<Message>();
		Message mess = null;
		String sql = "select m.state as state,ifNULL(u.username,m.smake) as name,IFNULL(u.w_imgurl,'img/ren.png') as w_img,IFNULL(u.d_imgurl,'img/ren.png') as d_img from  message m left join users u on m.spweixinid = u.userid  where documentsid='" + documentsid
					+ "' and (m.w_corpid='" + w_corpid + "' or m.d_corpid='" + d_corpid + "') order by m.id";
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				mess = new Message();
				mess.setSpname(resultSet.getString("name"));//审批人 姓名（编号） **/
				String tuUrl = "";
				if(d_corpid.equals("n-u-l-l")){
					tuUrl = resultSet.getString("w_img");
				}else{
					tuUrl = resultSet.getString("d_img");
				}
				mess.setTuUrl(tuUrl);
				mess.setState(resultSet.getInt("state"));
				list.add(mess);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, resultSet);
		}
		return list;
	}

	
	
	
	/**
	 * 查询开始单据   提交人
	 * 
	 * @param documentsid 单据id
	 * @param w_corpid 微信企业号标识
	 * @param d_corpid 钉钉企业号标识
	 * @return List<Message>
	 */
	public Message showDJKS(String documentsid, String w_corpid, String d_corpid) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Message mess = null;
		String sql = "select m.title as title,m.state as state,IFNULL(u.username,m.smake) as name,IFNULL(u.w_imgurl,'img/ren.png') as w_img,IFNULL(u.d_imgurl,'img/ren.png') as d_img from  message m left join users u on m.smake = u.userid  where documentsid='" + documentsid
					+ "' and (m.w_corpid='" + w_corpid + "' or m.d_corpid='" + d_corpid + "') order by m.id asc limit 0,1";
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				mess = new Message();
				mess.setName(resultSet.getString("name"));//审批人 姓名（编号） **/
				String tuUrl = "";
				if(d_corpid.equals("n-u-l-l")){
					tuUrl = resultSet.getString("w_img");
				}else{
					tuUrl = resultSet.getString("d_img");
				}
				mess.setTuUrl(tuUrl);
				mess.setState(resultSet.getInt("state"));
				mess.setTitle(resultSet.getString("title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, resultSet);
		}
		return mess;
	}


	
	/**
	 * 根据状态编号查找状态文字
	 * 
	 * @param cid
	 *            状态编号
	 * @return 状态文字
	 */
	public String showStateWZ(String cid) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "select sname from  insstate where cid=" + cid;
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("sname");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, resultSet);
		}
		return "";
	}


	
	/**
	 * 查询 某人 的某个状态的数据，根据offset 偏移量和 RESULT_SIZE 期望返回结果数量
	 *
	 * @param spweixinid 某人的微信编号
	 * @param state 状态 (0:待审,1:已审,2:驳回,3:本人提交记录)
	 * @param w_corpid 微信企业号标识
	 * @param d_corpid 钉钉企业号标识
	 * @return List<Message>
	 */
	public List<Message> showStateByPage(String spweixinid, String state, String w_corpid, String d_corpid,
			Integer offset) throws Exception {
		//pageIndex 从1开始
				Connection connection = getConnection();
				PreparedStatement statement = null;
				ResultSet resultSet = null;
				List<Message> list = new ArrayList<Message>();
				Message mess = null;
				Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
				ca.setTime(new Date()); // 设置时间为当前时间
				ca.add(Calendar.MONTH, -1); // 月份减1
				Date lastMonth = ca.getTime(); // 结果
				String sql = "";
				String sptime = sdf.format(lastMonth);
				if (state.equals("0"))
					sql = "select m.*,'-1' as lsstate,ms.name as sbumitName from  v_message m left join  v_message_last ml on  m.documentsid=ml.documentsid left join v_message_state ms on m.documentsid=ms.documentsid where (w_corpid='"+w_corpid+"' or d_corpid='"+d_corpid+"') and  spweixinid='"+spweixinid+"' and m.state = 0 ORDER BY tjtime DESC LIMIT ? OFFSET ?";
//					  sql = " select DISTINCT t1.*,ifnull(t3.username,t2.NAME) as submit,'-1' as lastState from " +
//							" message  t1 left join v_message_start t2 on t1.documentsid = t2.documentsid " +
//							" left join users t3 on t3.userid =  t2.NAME and t3.w_corpid = t2.w_corpid " +
//							" where t1.w_corpid = '" + w_corpid + "' and t1.state = '" + state + "' and t1.spweixinid = '" + spweixinid + "' ORDER BY t1.tjtime DESC"+
//							" LIMIT ? OFFSET ? ";
				if (state.equals("1"))
					sql = "select m.*,ml.state as lsstate,ms.name as sbumitName from  v_message m left join  v_message_last ml on  m.documentsid=ml.documentsid left join v_message_state ms on m.documentsid=ms.documentsid where (w_corpid='"+w_corpid+"' or d_corpid='"+d_corpid+"') and  spweixinid='"+spweixinid+"' and  id IN (SELECT MAX(id) FROM  message where state <>0 and spweixinid='"+ spweixinid +"' GROUP BY documentsid ) ORDER BY tjtime DESC LIMIT ? OFFSET ?";
//					sql = "select DISTINCT t1.*,ifnull(t3.username,t2.NAME) as submit, t4.state as lastState from  message t1 "
//							+ " left join v_message_start t2 on t1.documentsid = t2.documentsid "
//							+ " left join users t3 on t3.userid =  t2.NAME and t3.w_corpid = t2.w_corpid "
//							+ " left join v_message_last t4 on t1.documentsid = t4.documentsid "
//							+ "where t1.spweixinid='"
//							+ spweixinid
//							+ "' and t1.state in('"+state+"','2') and t1.w_corpid='"
//							+ w_corpid
//							+ "' and t1.sptime>='"
//							+ sptime
//							+ "' and t1.id IN (SELECT MAX(id) FROM  message where state <>0 and spweixinid='"+ spweixinid +"' GROUP BY documentsid )  ORDER BY t1.sptime DESC " +
//							"  LIMIT ? OFFSET ? ";
				if (state.equals("3"))
					sql = "select m.*,ml.state as lsstate,ml.spname as sbumitName from  v_message m left join  v_message_last ml on  m.documentsid=ml.documentsid left join v_message_state ms on m.documentsid=ms.documentsid where (w_corpid='"+w_corpid+"' or d_corpid='"+d_corpid+"') and  smake='"+spweixinid+"' and id IN (SELECT MIN(id) FROM  message where smake='"+spweixinid+"'  GROUP BY documentsid )  ORDER BY tjtime DESC LIMIT ? OFFSET ?";
//					sql = " select DISTINCT t1.*,ifnull(t3.username,t2.NAME) as submit,t2.state as lastState from " +
//							" message t1 left join v_message_last t2 on t1.documentsid = t2.documentsid " +
//							" left join v_users t3 on t1.name = t3.userid " +
//							" where t1.name='"+ spweixinid +"' and t1.tjtime>= '"+ sptime +"' and t1.w_corpid='"+ w_corpid +"' " +
//							" and t1.id IN (SELECT MIN(id) FROM  message GROUP BY documentsid )  ORDER BY t1.tjtime DESC " +
//							"  LIMIT ? OFFSET ? ";
				log.info(sql);
				try {
					statement = connection.prepareStatement(sql);
					statement.setInt(1,RESULT_SIZE);
					statement.setInt(2,offset);
					resultSet = statement.executeQuery();
					resultSet.last(); //移到最后一行
					Integer rowCount = resultSet.getRow(); //得到当前行号 记录最大数据
					resultSet.beforeFirst();
					Integer resultOffset = offset + rowCount;
					while (resultSet.next()) {
						mess = new Message();
						mess.setId(resultSet.getInt("id"));//主键 **/
						mess.setTitle(resultSet.getString("title"));//标题 **/
						mess.setSpweixinid(resultSet.getString("spweixinid"));//审批人 微信企业号 UserID **/
						mess.setTjtime(sdf.parse(resultSet.getString("tjtime")));// 提交时间 **/
						mess.setDocumentsid(resultSet.getString("documentsid"));//单据编号 **/
						mess.setState(resultSet.getInt("state"));//状态 0未审批 1审批通过 2驳回申请 **/
						mess.setLastState(resultSet.getString("lsstate"));
						mess.setSmake(resultSet.getString("smake"));//制单人
						String sbumit = resultSet.getString("sbumitName");
						sbumit = sbumit == null ? resultSet.getString("smake") : sbumit;
						mess.setSubmit(sbumit);//提交人
						mess.setTjtimeStr(StringUtil.timePass(mess.getTjtime(), 4));
						mess.setOffset(resultOffset);
						list.add(mess);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					closeAll(connection, statement, resultSet);
				}
				return list;
	}

	
	/**
	 * 进行审批 修改message状态
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public int update(Message message) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int rows = 0;
		String sql = "update  message set yjcontent='" + message.getYjcontent()
				+ "', sptime='" + sdf.format(message.getSptime())
				+ "', state='" + message.getState() + "' where id="
				+ message.getId() + ";";
		try {
			statement = connection.prepareStatement(sql);
			rows = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, null);
		}
		return rows;
	}

	
	/**
	 * 平台进行审批(同意,驳回)后 修改message状态
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public String BipUpdate(Message message) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int rows = 0;
//		String sql = "update message set yjcontent='" + message.getYjcontent()
//				+ "', sptime='" + sdf.format(message.getSptime())
//				+ "', state='" + message.getState() + "' where documentsid ='"
//				+ message.getDocumentsid() + "' and state1='"
//				+ message.getState1() + "' and (w_corpid='"
//				+ message.getW_corpid() + "' or d_corpid = '"+message.getD_corpid()+"' )and spname='"
//				+ message.getSpname() + "' and state=0";
		String sql = "update message set yjcontent='" + message.getYjcontent()
		+ "', sptime='" + sdf.format(message.getSptime())
		+ "', state='" + message.getState() + "' where documentsid ='"
		+ message.getDocumentsid() + "' and (w_corpid='"
		+ message.getW_corpid() + "' or d_corpid = '"+message.getD_corpid()+"' )and spname='"
		+ message.getSpname() + "' and state=0";
		log.info(sql);
		try {
			statement = connection.prepareStatement(sql);
			rows = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, null);
		}
		return rows + "";
	}


	
	/**
	 * 审批人为多个时 一个人进行审批后 将其他人的单据删除
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public String BipTuiHui(Message message) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int rows = 0;
		String sql = "delete from  message  where documentsid ='"
				+ message.getDocumentsid() + "'  and state1='"
				+ message.getState1() + "' and spname <> '"
				+ message.getSpname() + "' and (w_corpid='"
				+ message.getW_corpid() + "' or d_corpid='"
				+ message.getD_corpid() + "' ) and  state=0";
		try {
			statement = connection.prepareStatement(sql);
			rows = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, null);
		}
		return rows + "";
	}

	
	/**
	 * 平台执行退回后 将统一单据 状态为0 的删除掉
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public String TuiHuiDelete(Message message) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int rows = 0;
		String sql = "delete from  message  where documentsid ='"
				+ message.getDocumentsid() + "' and w_corpid='"
				+ message.getW_corpid() + "' and  state=0";
		try {
			statement = connection.prepareStatement(sql);
			rows = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, null);
		}
		return rows + "";
	}

	
	/**
	 * 平台执行退回后 返回退回时删除的单据 审批人
	 * 
	 * @param message  单据消息
	 * @return spname
	 */
	public String TuiHuiSpName(Message message) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String spname="";
		String sql = "select spweixinid,spname from  message  where documentsid ='"
				+ message.getDocumentsid()
				+ "' and w_corpid='"
				+ message.getW_corpid() + "' and  state=0";
		try { 
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				spname += resultSet.getString("spname")+"|";
			}
			spname = spname.substring(0,spname.length()-1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, null);
		}
		return spname;
	}
	 
	
	/***
	 * 用于接收平台传过来的审批数据 保存到数据库里
	 * @param mess Message对象
	 * @return zt 状态是否成功
	 * @throws ParseException
	 */
	public String jieshou(Message mess) {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String zt="";
		int count = 0;
		String sql = "INSERT INTO message(title,name,spweixinid,spname,content,yjcontent,tjtime,documentsid,documentstype,tablename"
				+ ",sbuid,state,state0,state1,dbid,scm,department,w_appid,d_appid,wapno,w_corpid,d_corpid,smake)VALUES ("
				+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		try {
			statement=connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);  
			statement.setString(1, mess.getTitle());
			statement.setString(2, mess.getName());
			statement.setString(3, mess.getSpweixinid());
			statement.setString(4, mess.getSpname());
			statement.setString(5, mess.getContent());
			statement.setString(6, mess.getYjcontent());
			statement.setTimestamp(7, new Timestamp(mess.getTjtime().getTime()));  
			statement.setString(8, mess.getDocumentsid());
			statement.setString(9, mess.getDocumentstype());
			statement.setString(10, mess.getTablename());
			statement.setString(11, mess.getSbuId());
			statement.setInt(12, mess.getState());
			statement.setString(13, mess.getState0());
			statement.setString(14, mess.getState1());
			statement.setString(15, mess.getDbid());
			statement.setString(16, mess.getScm());
			statement.setString(17, mess.getDepartment());
			statement.setString(18, mess.getW_appid());
			statement.setString(19, mess.getD_appid());
			statement.setString(20, mess.getWapno());
			statement.setString(21, mess.getW_corpid());
			statement.setString(22, mess.getD_corpid());
			statement.setString(23, mess.getSmake());
			statement.executeUpdate();
			resultSet=statement.getGeneratedKeys();
			if(resultSet.next()){
				count = resultSet.getInt(1);//返回录入数据的id
				zt="ok";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return zt;
	}

	/**
	 * 查询单据当前节点审批人信息
	 * 
	 * @param documentsid 单据id
	 * @param w_corpid 企业号标识 
	 * @return List<Message>
	 */
	public List<Message> showSPUser(String documentsid, String w_corpid,String d_corpid){
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Message> list = new ArrayList<Message>();
		Message mess = null;
		String sql = "select spweixinid,spname,d_appid,w_appid from message where id in (SELECT max(id) FROM  message where documentsid='"
					+ documentsid
					+ "' and ( w_corpid='" + w_corpid + "' or  d_corpid='" + d_corpid + "') and state=0 GROUP BY spname)";
		log.info(sql);
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				mess = new Message(); 
				mess.setSpweixinid(resultSet.getString("spweixinid"));//审批人 微信企业号 UserID **/
				mess.setSpname(resultSet.getString("spname"));//审批人 姓名（编号） **/ 
				mess.setD_appid(resultSet.getString("d_appid"));
				mess.setW_appid(resultSet.getString("w_appid"));
				list.add(mess);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, resultSet);
		}
		return list;
	}

	/**
	 * 将审批信息重置为 未读状态(审批退回时)
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public String ToWeiDu(Message message) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int rows = 0;
		String sql = "update message set yjcontent='', sptime=null,state=0 where documentsid ='"
				+ message.getDocumentsid()
				+ "'  and spname='"
				+ message.getSpname()
				+ "' and ( w_corpid='"
				+ message.getW_corpid()
				+ "' or d_corpid = '"+ message.getD_corpid() +"') and state1='"
				+ message.getState1() + "'";
		try {
			statement = connection.prepareStatement(sql);
			rows = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(connection, statement, null);
		}
		return rows + "";
	}


}
