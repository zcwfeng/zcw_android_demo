package top.zcwfeng.javalib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private ServerSocket serverSocket = null;
    private static final int PORT = 10065;
    private List<Socket> mClients = new ArrayList();
    private ExecutorService mExec = null;

    public ChatServer() {
        //开启服务
        System.out.println("服务器运行中。。。。。。");
        try {
            serverSocket = new ServerSocket(PORT);
            mExec = Executors.newCachedThreadPool();
            Socket client = null;
            while(true){

                System.out.println("等待客户上门。。。");

                client = serverSocket.accept();
                System.out.println("有客户来了。。。 ,客户是： " + client.getInetAddress());

                mClients.add(client);
                mExec.execute(new Service(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Service implements Runnable{
        private Socket mSocket;
        private BufferedReader br = null;
        private String msg;

        public Service(Socket client) {
            mSocket = client;
            try {
                br = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                this.sendMsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMsg(){
            int num = mClients.size();
            OutputStream out = null;
            try {
                out = mSocket.getOutputStream();
                PrintWriter pw = new PrintWriter(out);
                pw.write("hello,你是" + num + "个链接上的客户端" );
                pw.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            while (true){
                try {
                    if((msg = br.readLine()) != null){
                        System.out.println("客户端消息："+msg);
                        if("bye".equals(msg)){
                            mSocket.close();
                            break;
                        } else {
                            sendMsg();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();

    }
}
