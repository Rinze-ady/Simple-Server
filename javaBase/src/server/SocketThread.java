package server;

import service.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//线程类
public class SocketThread implements Runnable{
    String header = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html\r\n" +
            "\r\n";
    //socket对象
    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
        //线程启动，只要new这个类的时候，就会启动线程，完成多线程的开启
        new Thread(this).start();
    }

    @Override
    public void run() {

        try {
            //从socket中得到读取流，用于读取客户端给服务器的数据
            InputStream in = socket.getInputStream();
            //从socket中得到写入流，用于发送数据到客户端
            OutputStream out = socket.getOutputStream();

            //将写入流封装成响应对象，以便更好的发送数据
            Response response = new Response(out);

            //新建Request类后用下面这两句替代
            /**
             *             byte[] by = new byte[1024];
             *             in.read(by);
             *             //trim()用于去掉空格
             *             String info = new String(by).trim();
             * 这一段被放到了Request类中
             *         */
            //将读取流封装成请求对象，以便更好地读取数据
            Request request = new Request(in);
//            System.out.println(request);

            //得到Url
            String url = request.getUrl();


//            System.out.println(url);
            response.sendMessage(header);

            //根据url，得到serlvet对象if-else
            Servlet servlet = (Servlet)ServletFactory.getServlet(url);
            //利用这个if-else来替代下面一长串的
            //首先要求业务组件都实现Servlet这个接口，其次配置文件中要有相应的信息
            if(servlet!=null){//请求的是业务组件
                response.sendMessage("<html><meta charset='utf-8'>");
                servlet.service(request,response);
                response.sendMessage("</html>");
            }else{//将文件数据发送给客户端
                response.sendFile(url);
            }

//            if("land".equals(url)){//请求的是登录业务
//                LogInService s = new LogInService();
//                response.sendMessage("<html><meta charset='utf-8'>");
//                s.service(request,response);
//                response.sendMessage("</html>");
//            } else if("findAll".equals(url)){//请求的是查询所有业务
//                FindAllService s = new FindAllService();
//                response.sendMessage("<html><meta charset='utf-8'>");
//                s.service(request,response);
//                response.sendMessage("</html>");
//
//            }else if("add".equals(url)){//请求的是添加业务
//                AddService s = new AddService();
//                response.sendMessage("<html><meta charset='utf-8'>");
//                s.service(request,response);
//                response.sendMessage("</html>");
//
//            }else if("del".equals(url)){//请求的是删除业务
//                DelService s = new DelService();
//                response.sendMessage("<html><meta charset='utf-8'>");
//                s.service(request,response);
//                response.sendMessage("</html>");
//
//            }else if("findById".equals(url)){//请求的是删除业务
//                FindByIdService s = new FindByIdService();
//                response.sendMessage("<html><meta charset='utf-8'>");
//                s.service(request,response);
//                response.sendMessage("</html>");
//
//            }else if("update".equals(url)){//请求的是修改业务
//                UpdateService s = new UpdateService();
//                response.sendMessage("<html><meta charset='utf-8'>");
//                s.service(request,response);
//                response.sendMessage("</html>");
//
//            }else {//将文件数据发送给客户端
//                response.sendFile(url);
//            }


            //向客户端发送数据，并刷新
            //"你好，欢迎光临"，不能每次访问服务器时都回这一句话，应该将本地文件给客户端
            //新建一个文件夹file，里面放一些图片和文件，重新建一个类Response，完成数据的读取和发送-------------------------------
//            out.write("你好，欢迎光临".getBytes());
            //为了完成对文件的更换，新建一个类Request----------------------------------------
//            response.sendFile("file/2.gif");
//            response.sendFile(url);
            //当想在图片后加一行描述信息时，如果把sendMessage这句话放在sendFile前，则图片显示乱码，
            // 若把sendMessage这句话放在sendFile后，则描述信息显示不处来
            //新建一个html文件来解决--------------------------------------------------------------------
//            response.sendMessage("神奇动物");

            out.flush();

            //关闭流
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
