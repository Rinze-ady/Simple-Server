package service;

import dao.IUserDao;
import dao.impl.UserDaoImpl;
import server.Request;
import server.Response;
import server.Servlet;

public class DelService implements Servlet {
    private IUserDao dao = new UserDaoImpl();

    public void service(Request request, Response response){
        //先得到表单数据
        int id = Integer.parseInt(request.getParameter("id"));

        dao.del(id);
        FindAllService s = new FindAllService();
        s.service(request,response);
    }

}
