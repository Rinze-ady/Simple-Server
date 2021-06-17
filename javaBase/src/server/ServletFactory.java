package server;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//工厂类
public class ServletFactory {
    private static Properties pro = new Properties();
    //单例模式实现各个业务,立即加载类型
    //类加载的时候请求对象，效率更高
    private static Map<String, Object> servletMap = new HashMap<>();

    static{
        try {
            pro.load(new FileReader("web.txt"));

            //得到properties键的集合
           Set<String> kset =  pro.stringPropertyNames();
           for(String s : kset){
               //根据键得到值，值为servlet类路径
               String classPath = pro.getProperty(s);
               Class c = Class.forName(classPath);
               servletMap.put(s,c.getDeclaredConstructor().newInstance());

           }
//           --------------------------------------------
          /* //利用注解方式实现
            List<Class> classList = getClassList(packageName);
            //判断该类中是否有WebServlet注解
            if(c.isAnnotationPresent(WebServlet.class)){
                //取出WebServlet注解
                WebServlet w = (WebServlet)c.getAnnotation(WebServlet.class);
                String url = w.value();
                servletMap.put(url,c.getDeclaredConstructor().newInstance());
            }
            //还要在这个里面添加一个getClassList方法，在动态代理例子2中可以找到
            */

//--------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(servletMap);
    }

    //

    /**
     * 根据url得到处理该请求的业务组件对象
     * @param url
     * @return 业务组件对象
     */
    //要求利用单例模式进行改进，每new一次产生的是同一个对象
    public static Object getServlet(String url){
//        String classPath = pro.getProperty(url);
//        System.out.println(classPath + "=======================");
//        if(classPath == null){
//            return null;
//        }
//
//        try {
//            //加载类，得到类模板
//            Class c = Class.forName(classPath);
//            return c.getDeclaredConstructor().newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;

        return servletMap.get(url);
    }

    public static void main(String[] args) {
//        System.out.println(pro.getProperty());
    }
}
