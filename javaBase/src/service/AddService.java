package service;

import bean.UserBean;
import dao.IUserDao;
import dao.impl.UserDaoImpl;
import server.Request;
import server.Response;
import server.Servlet;
import server.WebServlet;

import java.time.LocalDate;

@WebServlet("add")
public class AddService implements Servlet {
    private IUserDao dao = new UserDaoImpl();

    public void service(Request request, Response response){
        //得到表单数据
        String userName= request.getParameter("userName");
        String sex=request.getParameter("sex");
        String birthday = request.getParameter("birthday");

        //将表单数据封装成实体对象
        UserBean user = new UserBean(userName, sex,LocalDate.parse(birthday));
        //调用持久层组件的添加方法，完成添加
        dao.add(user);

        //重新调用FindAllService，显示最新结果，称为请求转发
        FindAllService s = new FindAllService();
        s.service(request,response);
    }

}
