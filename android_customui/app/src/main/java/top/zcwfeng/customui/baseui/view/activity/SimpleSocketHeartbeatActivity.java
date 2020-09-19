package top.zcwfeng.customui.baseui.view.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.lang.ref.WeakReference;

import top.zcwfeng.customui.ISocket;
import top.zcwfeng.customui.R;
import top.zcwfeng.customui.databinding.ActivitySimpleSocketHeartbeatBinding;
import top.zcwfeng.customui.socketdemo.SocketService;

public class SimpleSocketHeartbeatActivity extends AppCompatActivity {
    private static final String TAG = "zcw";

    private MessageBackReciver mReciver;
    private Intent mServiceIntent;

    private ISocket iSocket;
    private IntentFilter mIntentFilter;

    private LocalBroadcastManager mLocalBroadcastManager;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSocket =ISocket.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iSocket =null;

        }
    };

    class MessageBackReciver extends BroadcastReceiver{
        private WeakReference<TextView> textView;

        public MessageBackReciver(TextView tv) {
            textView = new WeakReference<TextView>(tv);
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            TextView tv = textView.get();
            if (action.equals(SocketService.HEART_BEAT_ACTION)) {
                if (null != tv) {
                    Log.i(TAG, "Get a heart heat");
                    tv.setText("Get a heart heat");
                }
            } else {
                Log.i(TAG, "Get a heart heat");
                String message = intent.getStringExtra("message");
                tv.setText("服务器消息:" + message);
            }
        };
    }
    ActivitySimpleSocketHeartbeatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_simple_socket_heartbeat);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mReciver = new MessageBackReciver(binding.tvShow);
        mServiceIntent =new Intent(this, SocketService.class);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(SocketService.HEART_BEAT_ACTION);
        mIntentFilter.addAction(SocketService.MESSAGE_ACTION);
        binding.btnSend.setOnClickListener(v -> onViewClicked());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocalBroadcastManager.registerReceiver(mReciver,mIntentFilter);
        bindService(mServiceIntent,conn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(conn);
        mLocalBroadcastManager.unregisterReceiver(mReciver);
    }
    public void onViewClicked() {
        String content = binding.etSend.getText().toString();
        try {
            boolean isSend = iSocket.sendMessage(content);//Send Content by socket
            Toast.makeText(this, isSend ? "success" : "fail",
                    Toast.LENGTH_SHORT).show();
            binding.etSend.setText("");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}