package ru.olegshulika.asmeet3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "1_MainAct";
    private Button mStartServiceButton;
    private Button mBindServiceButton;
    public static final int REQUEST_CODE_SERVICE = 1321;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        Log.d(TAG, this.getLocalClassName()+" onCreate");
    }

    void initViews(){
        mStartServiceButton = findViewById(R.id.button_start_srv);
        mBindServiceButton = findViewById(R.id.button_bind_srv);
        this.setTitle(getString(R.string.app_name)+" "+getString(R.string.start_service_text));
    }

    void initListeners() {
        mStartServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "start TestMsgService");
                startService(TestMsgService.newIntent(MainActivity.this));
            }
        });

        mBindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startBind = new Intent(MainActivity.this, ServiceActivity.class);
                Log.d(TAG, "start Service Activity");
                startActivityForResult(startBind, REQUEST_CODE_SERVICE);
            }
        });

    }

}
