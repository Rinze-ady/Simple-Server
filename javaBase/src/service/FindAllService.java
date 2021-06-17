package service;

import bean.UserBean;
import dao.IUserDao;
import dao.impl.UserDaoImpl;
import server.Request;
import server.Response;
import server.Servlet;

import java.util.List;

public class FindAllService implements Servlet {

    private IUserDao dao = new UserDaoImpl();
    public void service(Request request, Response response){

        List<UserBean> list = dao.findAll();
        String info = "<table border='1' width='50%'>";
        //thead是表头，tr代表一行，th代表一个单元格
        //"操作"这个单元格对应删除操作
        info += "<thead><tr><th>用户名</th><th>密码</th><th>操作</th></tr></thead>";
        info +="<tbody>";

        for(UserBean user : list){
            info +="<tr><td>"+user.getUserName()+"</td>"+
                    "<td>"+ user.getPwd()+"</td>"+
                    "<td><a href='del?id="+user.getId()+"'>删除</a>" +
                     "<a href='findById?id="+user.getId()+"'>修改</a></td></tr>";
        }

        info += "</tbody></table>";
        info +="<a href='add.html'>添加用户</a>";
        response.sendMessage(info);
    }


}
