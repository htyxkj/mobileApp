package mobile.servlet.oaggtz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.biz.IOaggtzBiz;
import mobile.biz.impl.OaggtzBizImpl;
import mobile.entity.Oaggtz;
import mobile.servlet.oaggtz.task.MyTask;
import mobile.servlet.oaggtz.task.NoticeI;
import net.sf.json.JSONObject;
/**
 * 平台通过这个servlet传递公告数据
 * @author Administrator
 * 添加公告
 */
public class InsertOaggtzServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IOaggtzBiz oaggtzBiz = new OaggtzBizImpl();
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			String jsonstr = "";
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			jsonstr=br.readLine();
			jsonstr=URLDecoder.decode(jsonstr,"UTF-8");
			isr.close();
			is.close();
			//去除数据中的换行符
			jsonstr=jsonstr.replaceAll("\r", "");
			jsonstr=jsonstr.replaceAll("\n", "|");
			jsonstr=jsonstr.replaceAll("null", "");
			//字符串转Json
			JSONObject jsonObject = JSONObject.fromObject(jsonstr);
			Oaggtz oagg=new Oaggtz();
			//json转oaggtz对象
			oagg = (Oaggtz)JSONObject.toBean(jsonObject,Oaggtz.class);
        	oagg.setSusr("All");
        	oagg.setSorgto("All");
        	oagg.setState(0);
        	oagg.setCkdate(new Date());
        	oagg.setMkdate(new Date());
        	oagg.setSource("B");
        	oagg.setRead("0");
        	oaggtzBiz.insertOaggtz(oagg);
            NoticeI noi = new MyTask();
            noi.init(oagg.getW_corpid(), oagg.getW_appid(), oagg.getScm(),oagg.getD_corpid(),oagg.getD_appid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}