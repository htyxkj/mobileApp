package mobile.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mobile.dao.BaseDao;
import mobile.dao.IUserDao;
import mobile.entity.Users;

public class UserDaoImpl extends BaseDao<Users> implements IUserDao {

	
	
	/**
	 * 获取用户信息
	 * @param user 用户对象
	 * @return 用户信息
	 * @throws Exception
	 */
	public Users getUser(Users user) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String sql="select * from users where userid =? and (w_corpid =? or d_corpid =?)";
		try {
			statement=connection.prepareStatement(sql);  
			statement.setString(1,user.getUserid());
			statement.setString(2,user.getW_corpid());
			statement.setString(3,user.getD_corpid());
			resultSet=statement.executeQuery();
			user = null;
			if(resultSet.next()){
				user=new Users();
				user.setUserid(resultSet.getString("userid"));
				user.setTel(resultSet.getString("tel"));
				user.setUsername(resultSet.getString("username"));
				user.setW_imgurl(resultSet.getString("w_imgurl"));
				user.setD_imgurl(resultSet.getString("d_imgurl"));
				user.setEmail(resultSet.getString("email"));
				user.setD_userid(resultSet.getString("d_userid"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return user;
	}

	/**
	 * 修改用户信息
	 * @param user 用户对象
	 * @return 用户信息
	 * @throws Exception
	 */ 
	public Users updateUser(Users user) throws Exception {
		if(user.getEmail().equals("\"null\""));
			user.setEmail(null);
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String sql = "update users  set username=?,tel=?,email=?,w_corpid=?,d_corpid=? where userid=? and (w_corpid=? or d_corpid=?)";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getTel());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getW_corpid());
			statement.setString(5, user.getD_corpid());
			statement.setString(6, user.getUserid());
			statement.setString(7, user.getW_corpid());
			statement.setString(8, user.getD_corpid());
			statement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return null;
	}
	
	/**
	 * 添加用户信息
	 * @param user 用户对象
	 * @return 用户信息
	 * @throws Exception
	 */
	public Users insertUser(Users user) throws Exception {
		if(user.getEmail().equals("\"null\""));
			user.setEmail(null);
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String sql = "INSERT INTO users(userid,username,tel,scm,w_corpid,d_corpid,email,w_imgurl,d_imgurl,d_userid)VALUES (?,?,?,?,?,?,?,?,?,?);";
		try {
			statement=connection.prepareStatement(sql); 
			statement.setString(1, user.getUserid());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getTel());
			statement.setString(4, user.getScm());
			statement.setString(5, user.getW_corpid());
			statement.setString(6, user.getD_corpid());
			statement.setString(7, user.getEmail());
			statement.setString(8, user.getW_imgurl());
			statement.setString(9, user.getD_imgurl());
			statement.setString(10, user.getD_userid());
			statement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return null;
	}

	
	/**
	 * 删除用户
	 * @param userid  用户编码
	 * @param corid   企业corid
	 * @return
	 * @throws Exception
	 */
	public int delUsers(String userid, String corid) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		int row =0;
		String sql = "delete from users where userid=? and ( w_corpid=? or d_corpid =?)";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userid);
			statement.setString(2, corid);
			statement.setString(3, corid);
			row = statement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return row;
	}

	/**
	 * 获取用户列表
	 * @param scm     	公司
	 * @param w_corpID	微信corpid
	 * @param d_corpID 	钉钉corpid
	 * @return
	 * @throws Exception
	 */
	public List<String> getUserList(String scm, String w_corpID, String d_corpID) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		List<String> listUI=new ArrayList<String>();
		try {
			String sql="";
			if(!scm.equals(""))
				sql="select userid from users where scm in ("+scm+") and (w_corpid=? or d_corpid=?)";
			if(scm.equals(""))
				sql="select userid from users where  (w_corpid=? or d_corpid=?)";
			statement=connection.prepareStatement(sql);
			statement.setString(1,w_corpID);
			statement.setString(2,d_corpID);
			resultSet=statement.executeQuery();
			while(resultSet.next()){
				listUI.add(resultSet.getString("userid"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return listUI;
	}

	
	
	/**
	 * 修改用户头像
	 * @param user
	 * @param type
	 * @throws Exception
	 */
	public void uodateUsersImgUrl(Users user, String type) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String sql = "";
		if(type.equals("d")){
			sql = "update users  set  d_imgurl='"+user.getD_imgurl()+"' where userid='"+user.getUserid()+"' and d_corpid='"+user.getD_corpid()+"' ";
		}else {
			sql = "update users  set  w_imgurl='"+user.getW_imgurl()+"' where userid='"+user.getUserid()+"' and w_corpid='"+user.getW_corpid()+"' ";
		}
		try {
			statement=connection.prepareStatement(sql);  
			statement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
	}

	/**
	 * 根据钉钉用户编码获取BIP用户编码
	 * @param d_userid	钉钉用户编码
	 * @param d_corpid
	 * @return
	 * @throws Exception
	 */
	public Users getUserIdByDID(String d_userid, String d_corpid) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		Users user = null;
		String sql="select * from users where d_userid =? and (w_corpid =? or d_corpid =?)";
		try {
			statement=connection.prepareStatement(sql);  
			statement.setString(1,d_userid);
			statement.setString(2,d_corpid);
			statement.setString(3,d_corpid);
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				user=new Users();
				user.setUserid(resultSet.getString("userid"));
				user.setTel(resultSet.getString("tel"));
				user.setUsername(resultSet.getString("username"));
				user.setW_imgurl(resultSet.getString("w_imgurl"));
				user.setD_imgurl(resultSet.getString("d_imgurl"));
				user.setEmail(resultSet.getString("email"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return user;
	}

	public int delUsersByTel(String tel, String corid) throws Exception {
		Connection connection=getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		int row =0;
		String sql = "delete from users where tel=? and ( w_corpid=? or d_corpid =?)";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, tel);
			statement.setString(2, corid);
			statement.setString(3, corid);
			row = statement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(connection, statement, resultSet);
		}
		return row;
	}

	 
}
