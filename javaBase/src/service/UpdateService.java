package service;

import dao.IUserDao;
import dao.impl.UserDaoImpl;
import server.Request;
import server.Response;
import server.Servlet;

/**
 * 修改业务分两步操作，先按id查询，在进行修改
 */
public class UpdateService implements Servlet {
    private IUserDao dao = new UserDaoImpl();

    public void service(Request request, Response response){
        //先得到表单数据
        int id = Integer.parseInt(request.getParameter("id"));

        String pwd = request.getParameter("pwd");
        //调用持久方法完成修改
        dao.update(id,pwd);
        FindAllService s = new FindAllService();
        s.service(request,response);

    }
}
