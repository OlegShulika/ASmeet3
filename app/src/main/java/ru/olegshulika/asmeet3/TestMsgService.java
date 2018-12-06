package ru.olegshulika.asmeet3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class TestMsgService extends Service {
    private static final int MODE = Service.START_NOT_STICKY;
    private static final String TAG = "_TestMsgService";
    private IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        TestMsgService getService() {
            return TestMsgService.this;
        }
    }

    public TestMsgService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        Log.d(TAG, this.getClass().getName()+" onStartCommand");
        return MODE;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, this.getClass().getName()+" onBind");
        return mBinder;
    }

    public static final Intent newIntent(Context context){
        Intent intent = new Intent(context, TestMsgService.class);
        return intent;
    }
}
