package top.zcwfeng.customui.baseui.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import top.zcwfeng.customui.R;
import top.zcwfeng.customui.databinding.ActivitySocetClientActiityJavaBinding;
import top.zcwfeng.customui.utils.NetworkUtils;

public class SocetClientActiityJava extends AppCompatActivity {
    //定义相关变量,完成初始化
    private static final String HOST = "192.168.31.126";
    //    private static final String HOST = "169.254.177.122";
    private static final int PORT = 10065;

    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    private StringBuilder sb = null;

    private boolean writerFlag = false;
    //定义一个handler对象,用来刷新界面
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                sb.append(content);
                binding.tvShow.setText(sb.toString());
            }
        }
    };
    private LinkedBlockingQueue<String> msgs = new LinkedBlockingQueue<>();
    ActivitySocetClientActiityJavaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_socet_client_actiity_java);

        binding.tvIp.setText(NetworkUtils.getIPAddress(this));
        binding.btnSend.setOnClickListener(v -> onViewClicked());
        sb = new StringBuilder();

        //网络操作不能放在UI主线程 4.0
        new Thread(){
            public void run(){
                try {
                    //和服务器连接
                    socket = new Socket(HOST, PORT);
                    //获取输入流，读取服务器发送过来的信息
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // 获取输出流 向服务器写数据
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(readRunnable).start();
    }

    private Runnable readRunnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                if(socket ==null)continue;
                if(socket.isConnected() && !socket.isInputShutdown()){
                    try {
                        if((content = in.readLine()) !=null){
                            content += "\n";
                            handler.sendEmptyMessage(0x123);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    private Runnable writeRunnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                if(socket ==null)break;
                if(socket.isConnected() && !socket.isOutputShutdown()){
                    try {
                        out.println(msgs.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.flush();
                }
            }
        }
    };

    public void onViewClicked() {
        if(!writerFlag){
            new Thread(writeRunnable).start();
        }
        writerFlag = true;

        String msg = binding.etSend.getText().toString();
        try {
            msgs.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}