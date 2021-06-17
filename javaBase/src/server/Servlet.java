package server;

/**
 * 所有业务组件都要实现这个接口,
 * 为了去掉SocketThread类中每个业务中对应的if语句
 * 建好这个接口后，利用工厂模式来优化代码，新建配置文件web.xml------------------------------
 */
public interface Servlet {
    /**
     *
     * @param request 请求对象
     * @param response 响应对象
     */
    public void service(Request request, Response response);
}
