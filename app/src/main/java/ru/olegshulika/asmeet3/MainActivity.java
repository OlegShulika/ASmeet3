package ru.olegshulika.asmeet3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SERVICE = 1321;

    private static final String TAG = "1_MainAct";
    private Button mStartServiceButton;
    private Button mBindServiceButton;
    private Button mStopServiceButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        Log.d(TAG, " onCreate");
    }

    void initViews(){
        mStartServiceButton = findViewById(R.id.button_start_srv);
        mBindServiceButton = findViewById(R.id.button_bind_srv);
        mStopServiceButton = findViewById(R.id.button_stop_srv);
        this.setTitle(getString(R.string.app_name)+" "+getString(R.string.start_service_text));
    }

    void initListeners() {
        mStartServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "start TestMsgService");
                startService(TestMsgService.newIntent(MainActivity.this, Command.START));
            }
        });

        mBindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "start ServiceActivity");
                startActivityForResult(newIntent(MainActivity.this), REQUEST_CODE_SERVICE);
            }
        });

        mStopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "stop TestMsgService");
                startService(TestMsgService.newIntent(MainActivity.this, Command.STOP));
            }
        });
    }

    public static final Intent newIntent(Context context){
        Intent intent = new Intent(context, ServiceActivity.class);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, " onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, " onPause");
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
