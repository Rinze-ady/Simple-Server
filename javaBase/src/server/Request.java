package server;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

//处理读取流的请求类
public class Request {
    //客户端访问的url路径
    private String url;

    //封装表单数据的Map集合
    private Map<String, String> paramMap = new HashMap<>();

    public Request(InputStream in) {
        try {
            //接收客户端发送的数据，并以字符串的方式打印出来
            byte[] by = new byte[1024];
            in.read(by);
            //trim()用于去掉空格
            String str = new String(by).trim();

            //分别处理以get方式请求文件、以get方式请求业务组件、以post方式请求业务组件这三种情况
            String[] array =  str.split("\\s+");

            if(str.startsWith("GET")){
                pressGet(array[1]);
            }
            else if(str.startsWith("POST")){
                pressPost(array);
            }
//            System.out.println(paramMap);
//            this.url = array[1].substring(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "server.Request{" +
                "url='" + url + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }

    /**
     * 处理Post请求
     * @param array
     */
    public void pressPost(String[] array){
            this.url = array[1].substring(1);

            this.fullParam(array[array.length-1]);
    }

    /**
     * 处理Get请求
     * @param url
     */
    private void pressGet(String url) {
        if (url.indexOf("?") == -1) {
            this.url = url.substring(1);
        } else {
            //这里“[?]”是将“？”和正则表达式中的问号区分开来
            //?：等价于{0,1} 前一个规则可以不出现，最多出现一次
            String[] urlArray = url.split("[?]");
            this.url = urlArray[0].substring(1);
            this.fullParam(urlArray[1]);
        }

    }

    /**
     * 将账户密码放入map集合
     * @param s
     */
    private void fullParam(String s){
        String[] paramArray =s.split("&");
        for (String str : paramArray) {
            String[] parray = str.split("=");
            //考虑到用户输入的非法性，进行判断
            if(parray.length == 2) {
                paramMap.put(parray[0], parray[1]);
            }
            else{
                this.paramMap.put(parray[0],"");
            }
        }
    }

    /**
     * 根据表单名得到表单值
     * @param key 表单名
     * @return 表单值
     */
    public String getParameter(String key){
        return this.paramMap.get(key);
    }

}
