package ru.olegshulika.asmeet3;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = "2_ServiceAct";
    private TextView mServiceStat;
    private EditText mServiceDataReceived;
    private Button mStopServiceButton;
    private Handler mHandler = new LocalHandler();
    private Looper mLooper;
    private TestMsgService mBoundService=null;
    private ServiceConnection mServiceConnection = new ServiceConnection()  {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((TestMsgService.LocalBinder)service).getService();
            ((TestMsgService.LocalBinder)service).setHandler(mHandler);
            mServiceStat.setText(getString(R.string.srv_data_label)+"-CONNECTED");
            Log.d(TAG, "TestMsgService connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService=null;
            mServiceStat.setText(getString(R.string.srv_data_label)+"-disconnected");
            Log.d(TAG, "TestMsgService disconnected");
        }
    };

    private class LocalHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if (mBoundService!=null)
                mServiceDataReceived.append(" "+msg.obj);
            super.handleMessage(msg);
        }
    }

    protected boolean isServiceBound() {
        return mBoundService!=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLooper = Looper.getMainLooper();
        setContentView(R.layout.activity_serviice);
        initViews();
        initListeners();
        Log.d(TAG, " onCreate");
    }

    void initViews(){
        mServiceDataReceived = findViewById(R.id.srv_data_received);
        mStopServiceButton = findViewById(R.id.button_stop_srv);
        mServiceStat = findViewById(R.id.srv_stat);
        mServiceDataReceived.setText("");
        this.setTitle(getString(R.string.app_name)+" "+getString(R.string.bind_service_text));
    }

    void initListeners(){
        mStopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "stop TestMsgService");
                startService(TestMsgService.newIntent(ServiceActivity.this, Command.STOP));
                mServiceStat.setText(getString(R.string.srv_data_label)+"-disconnected");
            }
        });


    }

    public void bindTestMsgService() {
        if (!isServiceBound()) {
            Log.d(TAG, "binding TestMsgService...");
            bindService(TestMsgService.newIntent(ServiceActivity.this),
                    mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public void unbindTestMsgService() {
        if (isServiceBound()) {
            unbindService(mServiceConnection);
            mBoundService = null;
            mServiceStat.setText(getString(R.string.srv_data_label)+"-disconnected");
            Log.d(TAG, "unbinding TestMsgService...");

        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, " onBackPressed - UNBIND");
        unbindTestMsgService();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, " onResume");
        bindTestMsgService();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, " onPause");
        unbindTestMsgService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, " onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, " onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, " onDestroy");
    }

}
