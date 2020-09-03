package mobile.servlet.examine;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mobile.entity.Users;
import mobile.registered.SRegServ;


public class ButtonServlet extends HttpServlet {
	private static final long serialVersionUID = 8114021863350239475L;
	private static Logger log = LoggerFactory.getLogger(ButtonServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 点击按钮待审 已审 驳回
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			log.info("进入ButtonServlet");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			Users user = (Users) request.getSession().getAttribute("sessionUser");
			String state = request.getParameter("state");
			if (state == null || state.equals(""))
				state = "0";
			//判断应用注册时间
			SRegServ t=new SRegServ();
			String corpid ="";
			if(user.getLoginType().equals("w"))
				corpid = user.getW_corpid();
			else
				corpid = user.getD_corpid();
			Object[] obj=(Object[]) t.processOperator("isReg",corpid);
			if(obj[0].equals("1")||obj[0].equals("-1")){
				request.setAttribute("errorInfo", obj[1]);
				request.getRequestDispatcher("./expired.jsp").forward(request,response);
			}else if(2==(Integer)obj[0]){
				request.setAttribute("regdate", obj[1]);
			}
			//详情 按钮链接
			String xq_url1 ="./OAuthServlet?keyid=";
			//待审  已审   驳回  按钮链接
			String bu_url1 = "./ButtonServlet?state=";
			request.setAttribute("bu_url0", bu_url1);
			request.setAttribute("xqurl", xq_url1);
			request.setAttribute("title", "审批中心");
			log.info("进行页面跳转！");
			if (state.equals("0")) {//待审页面
				request.getRequestDispatcher("examine/daishen.jsp").forward(request, response);
			}
			if (state.equals("1")) {//已审页面
				request.getRequestDispatcher("examine/yishen.jsp").forward(request,response);
			}
			if (state.equals("2")) {//驳回页面
				request.getRequestDispatcher("examine/bohui.jsp").forward(request,response);
			}
			if (state.equals("3")) {//我提交的审批信息
				request.getRequestDispatcher("examine/wode.jsp").forward(request,response);
			} 
		} catch (Exception e) { 
			e.printStackTrace();
			return;
		}
	}
}