package mobile.servlet.oaggtz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.biz.IOaggtzBiz;
import mobile.biz.IUserBiz;
import mobile.biz.impl.OaggtzBizImpl;
import mobile.biz.impl.UserBizImpl;
import mobile.entity.AccessToken;
import mobile.entity.FuJian;
import mobile.entity.Oaggtz;
import mobile.entity.Users;
import mobile.tokenThread.TokenThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author Administrator
 *查询单个公告
 */
public class OneOaggtzServlet extends HttpServlet {
 
	private static Logger log = LoggerFactory.getLogger(OneOaggtzServlet.class);
	
	private static IUserBiz userBiz = new UserBizImpl();
	private static IOaggtzBiz oaggtzBiz = new OaggtzBizImpl();
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Users user = (Users) request.getSession().getAttribute("sessionUser");
        AccessToken accessToken = null;
        if(user.getLoginType().equals("w"))
        	accessToken = TokenThread.getWXAccToken(user.getW_corpid()+"-"+user.getLoginAppid());
        else
        	accessToken = TokenThread.getDDAccToken(user.getD_corpid()+"-"+user.getLoginAppid());
        //用户同意授权后，能获取到code
        String keyid = request.getParameter("keyid");
        try {
                Oaggtz data= oaggtzBiz.selectOne(keyid);
		    	Users uu = new Users();
		    	uu.setUserid(data.getSmaker());
		    	uu.setD_corpid(data.getD_corpid());
		    	uu.setW_corpid(data.getW_corpid());
                Users u = userBiz.getUser(uu);
                List<FuJian> listFuJian = new ArrayList<FuJian>();
                if(u!=null)
                data.setSmaker(u.getUsername());
                if (data != null) {
                    if (data.getFj_root() != null && data.getSuri()!=null&& !data.getFj_root().equals("")&& !data.getSuri().equals("")) {
                    	String fjNameStr =data.getSuri();
                    	String[] fjName = fjNameStr.split(";");
                    	for (int i = 0; i < fjName.length; i++) {
                    		fjNameStr = fjName[i];
                    		String fjPathStr =data.getFj_root();
                            FuJian fj = new FuJian();
                            fj.setServerPathBody("");
                            fj.setFilePath(fjPathStr);
                            fj.setFileName(fjNameStr);
                            if(data.getSource().equals("W")){
                            	fj.setServerPathHead(accessToken.getDomainName());
                            	fj.setFullPath(accessToken.getDomainName()+fjPathStr + fjNameStr);
                            }else if(data.getSource().equals("B")){
                            	fj.setServerPathHead(accessToken.getServerurl());
                            	fj.setFullPath(accessToken.getServerurl()+"fileupdown?fud=1&rid=4&isweb=1&dbid="+data.getDbid()+"&filepath=" + fjPathStr + fjNameStr);
                            }
                            log.info(fj.getFullPath());
                            listFuJian.add(fj);
						}
                    }
                }
                String content=data.getContent();
                data.setContent(content.replaceAll("\\|", "<\\br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
                data.setServerurl(accessToken.getServerurl());
                request.setAttribute("data", data);
                request.setAttribute("fujians", listFuJian);
                request.getRequestDispatcher("oaggtz/oneOaggtz.jsp").forward(request, response);
        } catch (Exception e) {
            log.info(e + "");
            e.printStackTrace();
            return;
        }
	}
}