package mobile.servlet.file;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnexServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AnnexServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String serverName = request.getServerName();//获取服务器名，localhost；
		int serverPort = request.getServerPort();//获取服务器端口号，8080；
		String contextPath = request.getContextPath();//获取项目名，/Example；
		String requestURL ="http://"+serverName+":"+serverPort+contextPath+"/";
//		String url = request.getParameter("url");
		String url= request.getParameter("url");  
		int d = url.lastIndexOf(".");
		String filehz = url.substring(d+1,url.length());
		if(filehz.equals("pdf")){
			url =URLEncoder.encode(url,"UTF-8"); 
			requestURL += "pdfjs/web/viewer.html?file="+url;
//		}else if ("docx、dotx、xlsx、xlsb、xls、xlsm、pptx、 ppsx、 ppt、 pps、 potx、 ppsm".indexOf(filehz)!=-1){
//			url =URLEncoder.encode(url,"utf-8");
//			requestURL="http://view.officeapps.live.com/op/view.aspx?src="+url;
		}else{
			String fjpath  = url.substring(url.lastIndexOf("&filepath=")+10,url.length());
			requestURL = url.substring(0,url.lastIndexOf("&filepath=")+10);
			requestURL += URLEncoder.encode(fjpath,"utf-8");
		}
//		request.getRequestDispatcher(requestURL).forward(request, response);
		log.info(requestURL);
		response.sendRedirect(requestURL);
	}

}
