package dao;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
    //加载驱动
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //建立连接
    protected Connection con;
    //sql语句执行对象
    protected PreparedStatement ps;
    //结果集
    protected ResultSet rs;

    String url = "jdbc:mysql://localhost:6789/mydb?characterEncoding=utf-8";

    //建立连接
    public void setConnection(){
        try {
            this.con = DriverManager.getConnection(url,"root","lovo");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //关闭连接
    public void closeConnection(){

            try {
                if(rs!=null) {
                    rs.close();
                }
                ps.close();
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    /**
     * 进行删除和修改操作
     * @param sql
     * @param valueArray
     */
    public void updateDate(String sql, Object...valueArray){
        this.setConnection();
        try {
            ps = con.prepareStatement(sql);
            for(int i=0; i<valueArray.length; i++){
                ps.setObject(i+1, valueArray[i]);
            }
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }


    public List find(Class beanClass, String sql, Object...valueArray){
        List list = new ArrayList();
        this.setConnection();
        try {
            ps = con.prepareStatement(sql);
            if(valueArray !=null && valueArray.length != 0) {
                for (int i = 0; i < valueArray.length; i++) {
                    ps.setObject(i + 1, valueArray[i]);
                }
            }
            rs= ps.executeQuery();
            //得到结果集检查对象
            ResultSetMetaData rm = rs.getMetaData();
            //得到查询列的个数
            int num = rm.getColumnCount();
            while (rs.next()){
                //产生该实体类对象
                Object beanObj = beanClass.getDeclaredConstructor().newInstance();
                for(int i=1; i<=num  ;i++){
                    //得到指定编号对应的列名
//                System.out.println(rm.getColumnLabel(i));
                    String columnName = rm.getColumnLabel(i);
                    //从结果集中得到指定列的值
                    Object value = rs.getObject(columnName);
                    if (value instanceof Date) {
                        value = LocalDate.parse(value.toString());
                    }
//------------------------------------
                    Field f = getField(beanClass,columnName);
                    f.setAccessible(true);
                    f.set(beanObj, value);
//   -----------------------------------
                }
                list.add(beanObj);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            this.closeConnection();
        }
        return list;
    }

    private Field getField(Class beanClass, String columnName){
        //得到该类中的属性列表
//        Field[] farray = beanClass.getDeclaredFields();
//
//        for(Field f : farray){
            //判断该属性是否有Column注解
//            if(f.isAnnotationPresent(Column.class)){
//                //得到该属性的Column注解
//                Column column = f.getAnnotation(Column.class);
//                //得到该注解的value值
//                String cname = column.value();

//                if(cname.equals(columnName)){
//                    return f;
//                }
//            }
//        }
        return null;
    }


    public static void main(String[] args) {
        //测试数据库是否连接成功
        BaseDao dao = new BaseDao();
//        dao.setConnection();
//        System.out.println(dao.con);
        //测试删除、修改数据库的方法是否实现
//        dao.updateDate("DELETE FROM t_user WHERE pk_userId = ?;", 2);
        dao.updateDate("UPDATE t_user SET u_pwd = ? WHERE pk_userId = ?;", "456845",4);
    }

}
