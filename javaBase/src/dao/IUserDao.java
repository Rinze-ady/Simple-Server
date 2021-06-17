package dao;

import bean.UserBean;

import java.util.List;

public interface IUserDao {

    public UserBean login(String user, String pwd);
    public void add(UserBean user);

    public void del(int id);

    public void update(int id, String pwd);

    public List<UserBean> findAll();

    public UserBean findById(int id);
}
