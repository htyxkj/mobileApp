package mobile.biz.impl;

import java.util.List;

import mobile.biz.IUserBiz;
import mobile.dao.IUserDao;
import mobile.dao.impl.UserDaoImpl;
import mobile.entity.Users;

public class UserBizImpl implements IUserBiz {
	
	IUserDao userDao = new UserDaoImpl();

	/**
	 * 获取用户信息
	 * @param user 用户对象
	 * @return 用户信息
	 * @throws Exception
	 */
	public Users getUser(Users user) throws Exception {
		return userDao.getUser(user);
	}
	/**
	 * 修改用户信息
	 * @param user 用户对象
	 * @return 用户信息
	 * @throws Exception
	 */ 
	public Users updateUser(Users user) throws Exception {
		return userDao.updateUser(user);
	}
	
	/**
	 * 添加用户信息
	 * @param user 用户对象
	 * @return 用户信息
	 * @throws Exception
	 */
	public Users insertUser(Users user) throws Exception {
		return userDao.insertUser(user);
	}
	
	/**
	 * 删除用户
	 * @param userid  用户编码
	 * @param corid   企业corid
	 * @return
	 * @throws Exception
	 */
	public int delUsers(String userid, String corid) throws Exception {
		return userDao.delUsers(userid,corid);
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
		return userDao.getUserList(scm,w_corpID,d_corpID);
	}
	/**
	 * 修改用户头像
	 * @param user
	 * @param type
	 * @throws Exception
	 */
	public void uodateUsersImgUrl(Users user, String type) throws Exception {
		userDao.uodateUsersImgUrl(user,type);
	}
	
	/**
	 * 根据钉钉用户编码获取BIP用户编码
	 * @param d_userid	钉钉用户编码
	 * @param d_corpid
	 * @return
	 * @throws Exception
	 */
	public Users getUserIdByDID(String d_userid, String d_corpid) throws Exception {
		return userDao.getUserIdByDID(d_userid, d_corpid);
	}
	/**
	 * 删除用户
	 * @param tel  用户手机号
	 * @param corid   企业corid
	 * @return
	 * @throws Exception
	 */
	public int delUsersByTel(String tel, String corid) throws Exception {
		return userDao.delUsersByTel(tel, corid);
	}
	
}
