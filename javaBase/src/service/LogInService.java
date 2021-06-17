package service;

import dao.IUserDao;
import dao.impl.UserDaoImpl;
import server.Request;
import server.Response;
import server.Servlet;

/**
 * 登录业务组件
 */
public class LogInService implements Servlet {

    IUserDao dao = new UserDaoImpl();
    /**
     * 业务方法
     * @param request 请求对象
     * @param response 响应对象
     */
    public void service(Request request, Response response){
        //得到表单数据
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");

        if(dao.login(userName,pwd) != null){
            response.sendMessage("登陆成功");
        }else {
            response.sendMessage("登录失败<br><a href='login.html'> 请重新登录");
        }




    };

}
