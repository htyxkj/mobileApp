package mobile.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.registered.SRegServ;
 

/**
 * 
 * ClassName: ExpiredServlet
 * @Description: 功能描述:应用注册
 * company:北京斯坦德科技发展有限公司
 * @date 2018年9月10日下午12:04:03
 */
public class ExpiredServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String regInfo = request.getParameter("regInfo");
		String wxscmid = request.getParameter("wxscmid");
		try {
			SRegServ t=new SRegServ();
			Object obj= t.processOperator("RegA&"+regInfo,wxscmid);
			PrintWriter out = null;
			String str="{\"success\":\""+obj.toString()+"\"}";
			out = response.getWriter();
			out.append(str);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
