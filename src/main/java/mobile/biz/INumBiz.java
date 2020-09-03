package mobile.biz;

import mobile.entity.Num;

public interface INumBiz {
	
	/**
	 * 添加发送记录
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public int insertNum(Num num) throws Exception;
}
