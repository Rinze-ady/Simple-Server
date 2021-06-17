package dao.impl;

import bean.UserBean;
import dao.BaseDao;
import dao.IUserDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDao implements IUserDao {
    @Override
    public UserBean login(String userName, String pwd) {
        UserBean user = new UserBean();
        this.setConnection();

        try {
            ps = con.prepareStatement("select*from t_user where u_name=? and u_pwd = ?");
            ps.setObject(1,userName);
            ps.setObject(2,pwd);
            rs = ps.executeQuery();
            if(rs.next()!=false){
                UserBean us = new UserBean();
                us.setId(rs.getInt("pk_userId"));
                us.setUserName(rs.getString("u_name"));
                us.setPwd(rs.getString("u_pwd"));
                us.setBirthday(LocalDate.parse(rs.getString("u_birthday")));
                us.setSex(rs.getString("u_sex"));

                user = us;
            }
            if(user.getPwd().equals(pwd)){
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public void add(UserBean user) {
        this.updateDate("INSERT INTO t_user(u_name,u_pwd,u_birthday,u_sex)\n" +
                "            VALUES(?,?,?,?)",user.getUserName(),user.getPwd(),user.getBirthday(),user.getSex());
    }

    @Override
    public void del(int id) {
        this.updateDate("DELETE FROM t_user WHERE pk_userId = ?",id);
    }

    @Override
    public void update(int id, String pwd) {
        this.updateDate("UPDATE t_user SET u_pwd = ? WHERE pk_userId = ?",pwd,id);
    }

    @Override
    public List<UserBean> findAll() {
        List<UserBean> list = new ArrayList<>();
        this.setConnection();

        try {
            ps = con.prepareStatement("select*from t_user");
            rs = ps.executeQuery();
            while(rs.next()){
                UserBean us = new UserBean();
                us.setId(rs.getInt("pk_userId"));
                us.setUserName(rs.getString("u_name"));
                us.setPwd(rs.getString("u_pwd"));
                us.setBirthday(LocalDate.parse(rs.getString("u_birthday")));
                us.setSex(rs.getString("u_sex"));

                list.add(us);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            this.closeConnection();
        }
        return list;
    }

    @Override
    public UserBean findById(int id) {
        UserBean user = new UserBean();
        this.setConnection();
        try {
            ps = con.prepareStatement("select*from t_user where pk_userId = ?");
            ps.setObject(1,id);
            rs = ps.executeQuery();
            if(rs.next()!=false){
                user.setId(rs.getInt("pk_userId"));
                user.setUserName(rs.getString("u_name"));
                user.setPwd(rs.getString("u_pwd"));
                user.setBirthday(LocalDate.parse(rs.getString("u_birthday")));
                user.setSex(rs.getString("u_sex"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            this.closeConnection();
        }

        return user;
    }

    public static void main(String[] args) {
        IUserDao dao = new UserDaoImpl();
//        dao.del(3);
//        dao.update(4,"454835464");
//        System.out.println(dao.login("loth","123"));
//        dao.add(new UserBean("youu","man",LocalDate.parse("1998-12-15")));
//        System.out.println(dao.findAll());
        System.out.println(dao.findById(2));
    }
}
