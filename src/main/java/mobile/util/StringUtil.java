package mobile.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	
	private static Logger log = LoggerFactory.getLogger(StringUtil.class);  
	
	public static String dateFormat(Date date, String formatString){
		SimpleDateFormat formatter = new SimpleDateFormat(formatString);
		return formatter.format(date);
	}

	public static String timePass(Date date,int level){
		if (date==null) {
			return null;
		}
		String result = null;
		String res = dateFormat(date, "yyyy-MM-dd");
		if (level==0) {
			return res;
		}
		long time = 0;
		time = System.currentTimeMillis() - date.getTime();
		if (time<=0) {
			return "刚才";
		}
		if (time < 60 * 1000L) {
			result = time / 1000 + "秒前";
			if (level < 1) {
				return res;
			}
		} else if (time < 60 * 60 * 1000L) {
			result = time / 1000 / 60 + "分钟前";
			if (level < 2) {
				return res;
			}
		} else if (time < 24 * 60 * 60 * 1000L) {
			result = time / 1000 / 60 / 60 + "小时前";
			if (level < 3) {
				return res;
			}
		} else if (time < 15 * 24 * 60 * 60 * 1000L) {
			result = time / 1000 / 60 / 60 / 24 + "天前";
			if (level < 3) {
				return res;
			}
		} else {
			result =res;
		}
		return result;
	}
}
