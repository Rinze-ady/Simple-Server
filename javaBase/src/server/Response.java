package server;

import java.io.*;

//处理写入流的响应类
//先由客户端发送请求，由这个类来响应
public class Response {

    //socket写入流
    //这个写入流的关闭在类SocketThread中
    private OutputStream out;

    //此构造方法完成写入流的初始化
    public Response(OutputStream out) {
        this.out = out;
    }

    //向客户端发送文本数据
    public void sendMessage(String info){
        try {
            this.out.write(info.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //向客户端发送文件数据
    public void sendFile(String filePath){
        File f = new File(filePath);
        //判断文件是否存在，如果不存在，则跳出方法
        if(f.exists() == false){
            return;
        }

        //给本地文件创建一个读取流
        InputStream in = null;
        try {
            in = new FileInputStream(filePath);

            //进行文件拷贝，建立byte数组，一个缓冲的地方，一次读取1024字节，
            //在建一个变量len用于统计当前读了多少了字节
            //每次循环读取1024个字节到byte数组并让变量len接收，当前不等于-1就循环（数据还未读完）
            byte[] by = new byte[1024];
            int len = 0;

            while ((len = in.read(by))!= -1){
                //out写入流来自于SocketThread类，来自于客户端
                out.write(by,0,len);
//                this.out = out;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
