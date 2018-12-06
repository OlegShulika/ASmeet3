package ru.olegshulika.asmeet3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = "2_ServiceAct";
    private EditText mServiceDataReceived;
    private Button mStopServiceButton;
    private TestMsgService mBoundService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((TestMsgService.LocalBinder)service).getService();
            Log.d(TAG, "ServiceActivity connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "ServiceActivity disconnected");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviice);
        initViews();
        initListeners();
        Log.d(TAG, this.getLocalClassName()+" onCreate");
    }

    void initViews(){
        mServiceDataReceived = findViewById(R.id.srv_data_received);
        mStopServiceButton = findViewById(R.id.button_stop_srv);
        mServiceDataReceived.setText("");
        this.setTitle(getString(R.string.app_name)+" "+getString(R.string.bind_service_text));
    }

    void initListeners(){
        mStopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "unbind TestMsgService");
                unbindService();
            }
        });

    }


    private void init() {
        bindService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, this.getLocalClassName()+" onResume");
        bindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, this.getLocalClassName()+" onPause");
        unbindService();
    }


    public void bindService() {
        bindService(TestMsgService.newIntent(ServiceActivity.this), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }


    public void unbindService() {
        unbindService(mServiceConnection);
    }


}
