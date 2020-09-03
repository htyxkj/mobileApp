package mobile.biz.impl;

import mobile.biz.INumBiz;
import mobile.dao.INumDao;
import mobile.dao.impl.NumDaoImpl;
import mobile.entity.Num;

public class NumBizImpl implements INumBiz {

	INumDao numDao = new NumDaoImpl();
	/**
	 * 添加发送记录
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public int insertNum(Num num) throws Exception {
		return numDao.insertNum(num);
	}

}
