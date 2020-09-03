package mobile.servlet.examine;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opslab.util.StringUtil;

import mobile.biz.IMessageBiz;
import mobile.biz.impl.MessageBizImpl;
import mobile.entity.Message;
import mobile.entity.Users;



public class PageDataServlet extends HttpServlet {
    private static IMessageBiz msgBiz = new MessageBizImpl();
    private static Logger log = LoggerFactory.getLogger(PageDataServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 滚动加载数据
     **/
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
    		log.info("审批滚动加载");
			Users user = (Users) request.getSession().getAttribute("sessionUser");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String pageType = request.getParameter("pageType");
			String offsetStr = request.getParameter("offset");
			List<Message> list;
			//对请求参数进行校验
			if (!StringUtil.isEmpty(pageType) && !StringUtil.isEmpty(offsetStr) && offsetStr.matches("\\d+")) {
			    Integer offset = Integer.parseInt(offsetStr);
			    String state = "9";
			    if (pageType.equals("wode")) {
			        state = "3";
			    }
			    if (pageType.equals("daishen")) {
			        state = "0";
			    }
			    if (pageType.equals("yishen")) {
			        state = "1";
			    }
			    list = msgBiz.showStateByPage(user.getUserid(), state, user.getW_corpid(), user.getD_corpid(), offset);
			}else{
			    list = new ArrayList<Message>();
			}
			JSONArray json = JSONArray.fromObject(list);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(json.toString().getBytes("UTF-8"));
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}