package mobile.dao;

import java.text.ParseException;
import java.util.List;

import mobile.entity.Message;

public interface IMessageDao {

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
	public Integer showNum(String spweixinid, String state, String w_corpid) throws Exception;
	
	/**
	 * 根据编号查询审批内容
	 * 
	 * @param keyid
	 *            编号
	 * @return message
	 */
	public Message show(String keyid) throws Exception; 
	
	/**
	 * 查询符合条件的单据审批记录
	 * 
	 * @param documentsid 单据id
	 * @param w_corpid 微信企业号标识
	 * @param d_corpid 钉钉企业号标识
	 * @return List<Message>
	 */
	public List<Message> showSPJL(String documentsid, String w_corpid,String d_corpid) throws Exception;
	
	/**
	 * 查询开始单据   提交人
	 * 
	 * @param documentsid 单据id
	 * @param w_corpid 微信企业号标识
	 * @param d_corpid 钉钉企业号标识
	 * @return List<Message>
	 */
	public Message showDJKS(String documentsid, String w_corpid,String d_corpid) throws Exception;
	
	
	/**
	 * 根据状态编号查找状态文字
	 * 
	 * @param cid
	 *            状态编号
	 * @return 状态文字
	 */
	public String showStateWZ(String cid) throws Exception;
	
	/**
	 * 查询 某人 的某个状态的数据，根据offset 偏移量和 RESULT_SIZE 期望返回结果数量
	 *
	 * @param spweixinid 某人的微信编号
	 * @param state 状态 (0:待审,1:已审,2:驳回,3:本人提交记录)
	 * @param w_corpid 微信企业号标识
	 * @param d_corpid 钉钉企业号标识
	 * @return List<Message>
	 */
	public List<Message> showStateByPage(String spweixinid,String state,String w_corpid,String d_corpid,Integer offset) throws Exception;
	
	
	/**
	 * 进行审批 修改message状态
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public int update(Message message) throws Exception;
	
	/**
	 * 平台进行审批(同意,驳回)后 修改message状态
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public String BipUpdate(Message message) throws Exception;
	
	/**
	 * 审批人为多个时 一个人进行审批后 将其他人的单据删除
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public String BipTuiHui(Message message) throws Exception;
	
	
	/**
	 * 平台执行退回后 将统一单据 状态为0 的删除掉
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public String TuiHuiDelete(Message message)  throws Exception;
	
	/**
	 * 平台执行退回后 返回退回时删除的单据 审批人
	 * 
	 * @param message  单据消息
	 * @return spname
	 */
	public String TuiHuiSpName(Message message) throws Exception;
	
	/***
	 * 用于接收平台传过来的审批数据 保存到数据库里
	 * @param mess Message对象
	 * @return zt 状态是否成功
	 * @throws ParseException
	 */
	public String jieshou(Message mess) throws Exception ;
	
	/**
	 * 查询单据当前节点审批人信息
	 * 
	 * @param documentsid 单据id
	 * @param w_corpid 企业号标识 
	 * @return List<Message>
	 */
	public List<Message> showSPUser(String documentsid, String w_corpid,String d_corpid) throws Exception;
	
	/**
	 * 将审批信息重置为 未读状态(审批退回时)
	 * 
	 * @param message
	 *            单据消息
	 * @return 受影响行数
	 */
	public String ToWeiDu(Message message) throws Exception;
}
