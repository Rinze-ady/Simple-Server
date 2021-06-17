package server;

import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public MyServer(){
        try {
            //开启服务器，开放8088端口，端口号自己定：8088
            ServerSocket server = new ServerSocket(7808);
            //解决服务器开了后执行一次就关闭的情况

            while(true) {
                // 如果有客户端连接到服务器，就将连接信息封装成socket对象
                //监听端口，有服务的时候进行数据交互,
                //有客户端连接到服务器时才会执行后面的语句，
                //线程阻塞：监听一直存在，但一直没有客户端连接
                Socket socket = server.accept();

                //新建一个SocketThread类来解决多用户不能同时访问的问题-------------------------------------------
                //将原本写在监听语句后的代码放到SocketThread的run方法中
                //循环监听端口，每循环一次新建一个线程对象对象，为每个用户新建一个线程专门为其服务
                new SocketThread(socket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyServer();
    }
}
