package ru.olegshulika.asmeet3;

import android.content.Intent;
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
                Log.d(TAG, "Stop TestMsgService");
                stopService(TestMsgService.newIntent(ServiceActivity.this));
            }
        });

    }


}
