package mobile.servlet.oaggtz.task;

public interface NoticeI extends Runnable {
	public void init(String w_corpid,String w_appid,String scm,String d_corpid,String d_appid);
}
