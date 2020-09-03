package mobile.dao;

import mobile.entity.Num;

public interface INumDao {
	/**
	 * 添加发送记录
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public int insertNum(Num num) throws Exception;
}
