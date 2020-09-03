package mobile.servlet.examine;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.biz.IMessageBiz;
import mobile.biz.impl.MessageBizImpl;
import mobile.entity.Message;

public class SelectOneMessage extends HttpServlet {
	private static IMessageBiz msgBiz = new MessageBizImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			String keyid=request.getParameter("id");
			PrintWriter out = null;
			Message data=msgBiz.show(keyid);
			if(data!=null){
				String str="{\"success\":\"ok\"}";
				out = response.getWriter();
				out.append(str);
			}else{
				String str="{\"success\":\"no\"}";
				out = response.getWriter();
				out.append(str);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
