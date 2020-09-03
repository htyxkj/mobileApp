package mobile.servlet.examine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.biz.IInsorgBiz;
import mobile.biz.IMessageBiz;
import mobile.biz.impl.InsorgBizImpl;
import mobile.biz.impl.MessageBizImpl;
import mobile.entity.AccessToken;
import mobile.entity.FuJian;
import mobile.entity.Message;
import mobile.entity.Users;
import mobile.tokenThread.TokenThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opslab.util.StringUtil;


public class OAuthServlet extends HttpServlet {
	private static final long serialVersionUID = -572430383982463747L;
	private static String SERVER_PATH_BODY = "mydoc/db_";
    public final static char DIV_CELL = (char) 0x1F;//单元分隔
    private static Logger log = LoggerFactory.getLogger(OAuthServlet.class);

    private static IMessageBiz msgBiz = new MessageBizImpl();
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 点击一条审批信息查看详情
     **/
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
	        request.setCharacterEncoding("utf-8");
	        response.setCharacterEncoding("utf-8");
	        String state = request.getParameter("state");
	        String keyid = request.getParameter("keyid");
	        Users user = (Users) request.getSession().getAttribute("sessionUser");
	        String userid = user.getUserid();
	        AccessToken accessToken = null;
	        if(user.getLoginType().equals("w")){
	        	accessToken = TokenThread.getWXAccToken(user.getW_corpid()+"-"+user.getLoginAppid());
	        }else{
				IInsorgBiz insorgBiz = new InsorgBizImpl();  
				String key = insorgBiz.getWorkKey(user.getD_corpid(),user.getLoginAppid()); 
	        	accessToken = TokenThread.getDDAccToken(key+"-"+user.getLoginAppid());
	        }
	        Message mess = null;
        
                Message data = msgBiz.show(keyid); 
                List<Message> listM = new ArrayList<Message>();
                List<FuJian> listFuJian = new ArrayList<FuJian>();
                String fujianPath = "";
                if (data != null) {
                    if (data.getContent() != null && !"".equals(data.getContent())) {
                        String content = data.getContent();
                        String noFJcontent = "";
                        int fjPathIndex = content.indexOf("附件路径:");
                        int fjNameIndex = content.indexOf("附件名称:");
                        if (fjPathIndex != -1 && fjNameIndex != -1) {
                            noFJcontent = content.substring(0, fjPathIndex);
                            String fjPathStr = content.substring(fjPathIndex, fjNameIndex);
                            String fjNameStr = content.substring(fjNameIndex, content.length());
                            String[] fjPathstrs = fjPathStr.split(";");
                            if (fjPathstrs.length == 1 && !StringUtil.isEmpty(fjPathstrs[0])) {
                                String[] fjPaths = fjPathstrs[0].split(":");
                                if (fjPaths.length > 1) {
                                    fujianPath = fjPaths[1];
                                }
                                String[] fjNamestrs = fjNameStr.split(";");
                                if (fjNamestrs.length > 0) {
                                    for (int i = 0; i < fjNamestrs.length; i++) {
                                        String fjName  = "";
                                        if(i==0){
                                            String[] fjNames = fjNamestrs[0].split(":");
                                            if(fjNames.length>1&&!StringUtil.isEmpty(fjNames[1])){
                                                fjName = fjNames[1];
                                            }
                                        }
                                        if (i > 0) {
                                            if (!StringUtil.isEmpty(fjNamestrs[i])) {
                                                fjName = fjNamestrs[i];
                                            }
                                        }
                                        if(!StringUtil.isEmpty(fjName)){
                                            FuJian fj = new FuJian();
                                            fj.setServerPathHead(accessToken.getServerurl());
                                            fj.setServerPathBody(SERVER_PATH_BODY+data.getDbid()+"/");
                                            fj.setFilePath(fujianPath);
                                            fj.setFileName(fjName);
                                            fj.setFullPath(accessToken.getServerurl() +"fileupdown?fud=1&rid=4&isweb=1&dbid="+data.getDbid()+"&filepath=" + fujianPath + fjName);
                                            log.info(fj.getFullPath());
                                            listFuJian.add(fj);
                                        }
                                    }
                                }
                            }
                        }
                        if (!StringUtil.isEmpty(noFJcontent)) {
                        	String bStr=noFJcontent;
                        	int i = bStr.indexOf(DIV_CELL);
                    		while (i>0) {
                    			noFJcontent+=bStr.substring(0,i)+"<br/>";
                    			bStr = bStr.substring(i+1);
                    			i = bStr.indexOf(DIV_CELL);
                    		}
                    		noFJcontent+=bStr;
                    		noFJcontent=noFJcontent.replaceAll("\r\n", "<br/>");
                            data.setContenthuanhang(noFJcontent);
                        } else {
                        	String aStr="";
                        	String bStr=data.getContent();
                        	int i = bStr.indexOf(DIV_CELL);
                    		while (i>0) {
                    			aStr+=bStr.substring(0,i)+"<br/>";
                    			bStr = bStr.substring(i+1);
                    			i = bStr.indexOf(DIV_CELL);
                    		}
                    		aStr+=bStr;
                    		aStr=aStr.replaceAll("\r\n", "<br/>");
                            data.setContenthuanhang(aStr);
                        }
                    }
                    listM = msgBiz.showSPJL(data.getDocumentsid(), user.getW_corpid(),user.getD_corpid());
                    mess = msgBiz.showDJKS(data.getDocumentsid(), user.getW_corpid(),user.getD_corpid());
                    data.setState1wenzi(msgBiz.showStateWZ(data.getState1())); 
                }
                data.setServerurl(accessToken.getServerurl());
                request.setAttribute("_right", listM.get(listM.size() - 1).getState());
                if (state != null && state.equals("3")) {
                    request.setAttribute("dateks", mess);
                    request.setAttribute("listM", listM);
                    request.setAttribute("data", data);
                    request.setAttribute("usercode", userid);
                    request.setAttribute("fujians", listFuJian);
                    request.getRequestDispatcher("examine/wodexq.jsp").forward(request, response);
                } else if (userid.equals(data.getSpweixinid())) {
                    request.setAttribute("dateks", mess);
                    request.setAttribute("listM", listM);
                    request.setAttribute("data", data);
                    request.setAttribute("usercode", userid);
                    request.setAttribute("fujians", listFuJian);
                    request.getRequestDispatcher("examine/shenpi.jsp").forward(request, response);
                } else {
                    request.setAttribute("jg", 2);
                    request.getRequestDispatcher("examine/jieguo.jsp").forward(request, response);
                }
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}