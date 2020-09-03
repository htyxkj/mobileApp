package mobile.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mobile.dao.BaseDao;
import mobile.dao.INumDao;
import mobile.entity.Num;

public class NumDaoImpl extends BaseDao<Num> implements INumDao {
	private static final Log log = LogFactory.getLog(NumDaoImpl.class);
	/**
	 * 添加发送记录
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public int insertNum(Num num) throws Exception {
		Connection connection = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		java.sql.Date sqlDate = new java.sql.Date(num.getTime().getTime());
		Object[] params = new Object[] { num.getW_corpid(), num.getAppid(),
				num.getScm(), num.getWeixinid(), sqlDate,num.getContent()};
		String sql = "insert into countnum (w_corpid,appid,scm,weixinid,time,content) values (?,?,?,?,?,?);";
		try {
			log.info(sql);
			statement = connection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			return statement.executeUpdate();
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace(); 
			return 0;
		} finally {
			closeAll(connection, statement, resultSet);
		}
	}

}
