package service;

import bean.UserBean;
import dao.IUserDao;
import dao.impl.UserDaoImpl;
import server.Request;
import server.Response;
import server.Servlet;

public class FindByIdService implements Servlet {
    private IUserDao dao = new UserDaoImpl();

    public void service(Request request, Response response){
        //得到表单数据
        int id = Integer.parseInt(request.getParameter("id"));

        UserBean user = dao.findById(id);
        String info = "<form action='update' >";
        info +="用户名： "+user.getUserName()+"<br>";
        info += "密码: <input type='password' name='pwd'><br>";
        info +="性别： "+user.getSex()+"<br>";
        info +="生日： "+user.getBirthday()+"<br>";

        info += "<input type='hidden' name='id' value='"+user.getId() +"'>";
        info +="<input type='submit' value='修改'>";
        info += "</form>";
        response.sendMessage(info);
    }
}
