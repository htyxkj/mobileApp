package mobile.util;

public class APIAddr {
	///
	///  钉钉API地址
	///
	/** 获取钉钉用户编码 **/
	public static String DD_USER_CODE = "https://oapi.dingtalk.com/user/getuserinfo";
	/** 获取钉钉用户详细信息 **/
	public static String DD_USER_INFO = "https://oapi.dingtalk.com/user/get";
	/** 修改钉钉APP信息 **/				
	public static String DD_APP_UPDATE = "https://oapi.dingtalk.com/microapp/update";
	/** 发送消息给用户 **/
	public static String DD_APP_MESSAGE = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token=ACCESS_TOKEN";
	/** 获取钉钉应用列表 **/
	public static String DD_APP_LIST = "https://oapi.dingtalk.com/microapp/list";
	/** 上传文件 **/
	public static String DD_UPLOAD_FILE = "https://oapi.dingtalk.com/media/upload";
	/** 根据手机号获取userid **/
	public static String DD_USER_BY_MOBILE= "https://oapi.dingtalk.com/user/get_by_mobile";
	/** 创建员工 **/
	public static String DD_USER_CREATE = "https://oapi.dingtalk.com/user/create";
	/** 删除员工 **/
	public static String DD_USER_DELETE = "https://oapi.dingtalk.com/user/delete";
	
	
	///
	///	 微信API地址
	///
	/** 设置微信应用 **/
	public static String WX_APP_UPDATE = "https://qyapi.weixin.qq.com/cgi-bin/agent/set?access_token=ACCESS_TOKEN";
	/** 微信网页授权 **/
	public static String WX_OAUTH2 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&agentid=AGENTID&state=qaqaqa#wechat_redirect";
	/** 根据CODE获取用户票据 **/
	public static String WX_USER_CODE = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	/** 获取用户详细信息 **/
	public static String WX_USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=ACCESS_TOKEN";
}
