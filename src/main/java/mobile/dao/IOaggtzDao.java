package mobile.dao;

import java.util.List;

import mobile.entity.Oaggtz;

public interface IOaggtzDao {

	
	/**
	 * 添加公告通知
	 * @param ggtz
	 * @return 行数
	 * @throws Exception
	 */
	public int insertOaggtz(Oaggtz ggtz)throws Exception;
	
	/**
	 * 删除公告信息
	 * @param sid 		公告编码
	 * @param corpid    唯一标识
	 * @return 行数
	 * @throws Exception
	 */
	public int delOaggtz(String sid,String corpid) throws Exception;
	
	/**
	 * 修改为已读
	 * @param w_corpid 	    微信唯一标识
	 * @param d_corpid    钉钉唯一标识
	 * @return 行数
	 * @throws Exception
	 */
	public int upOaggtzR(String sid,String userid ,String w_corpid,String d_corpid) throws Exception;
	
	/**
	 * 查询 某人 的某个状态的数据，根据offset 偏移量和 RESULT_SIZE 期望返回结果数量
	 * @param spweixinid       某人的微信编号
	 * @param state            状态 (0:未读,1:已读,2:我发布的)
	 * @param w_corpid         企业号标识
	 * @return List<Oaggtz>
	 */
	public List<Oaggtz> ShowAll(String spweixinid,String scm,String read,String w_corpid,String d_corpid,Integer offset) throws Exception;
	
	/**
	 * 根据编号查询详细信息
	 * @param keyid 主键ID
	 * @return
	 */
	public Oaggtz selectOne(String key)throws Exception;
}
