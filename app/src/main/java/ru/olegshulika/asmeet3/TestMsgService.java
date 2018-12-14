package ru.olegshulika.asmeet3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Handler;

@interface Command {
    int INVALID = -1;
    int STOP = 0;
    int START = 1;
}

public class TestMsgService extends Service {
    private static final int MODE = Service.START_NOT_STICKY;
    private static final String TAG = "_TestMsgService";
    private static final String KEY_COMMAND = "service.cmd";
    private static final String KEY_DATA = "service.data";
    private static final int SERVICE_WORKTIME_LIMIT = 1000;   // max work time in sec
    private IBinder mBinder = new LocalBinder();
    private Handler handlerMsg = null;
    private boolean serviceStarted = false;

    private boolean isServiceStarted() {
        return serviceStarted;
    }

    private void setServiceStarted(boolean serviceStarted) {
        this.serviceStarted = serviceStarted;
    }

    public class LocalBinder extends Binder {
        TestMsgService getService() {
            return TestMsgService.this;
        }
        void setHandler(Handler hMsg) {
            handlerMsg = hMsg;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Log.d(TAG, " onStartCommand "+flags+" "+startId);
        int cmd = getCommand(intent);
        if (cmd==Command.INVALID)
            return MODE;
                                        // start service
        if (cmd==Command.START && !isServiceStarted()) {
            Log.d(TAG, " started..." + startId);
            setServiceStarted(true);
            startSrv(startId);
            return MODE;
        }
                                        // stop service
        if (cmd==Command.STOP && isServiceStarted()) {
            Log.d(TAG, " stopped...");
            stopSelf(startId);
            setServiceStarted(false);
            return MODE;
        }

        return MODE;
    }

    private void startSrv(final int startId) {              // Service workout thread
        new Thread(new Runnable() {         // Start service
            @Override
            public void run() {
                int workTime=SERVICE_WORKTIME_LIMIT;
                while (--workTime>0 && isServiceStarted()){
                    try {
                        Thread.sleep(1000);             // wait 1 sec
                        String strMsg = "msg"+System.currentTimeMillis();
                        Log.d(TAG,"==>"+strMsg);
                        if (handlerMsg!=null) {
                            Message msg = handlerMsg.obtainMessage(0, strMsg);
                            handlerMsg.sendMessage(msg);
                        }
                    } catch (Exception ex){
                        Log.d(TAG,ex.getMessage());
                    }
                }
                stopSelf(startId);
                setServiceStarted(false);
            }
        }).start();
    }

    public static final Intent newIntent(Context context, int serviceCommand){
        Intent intent = new Intent(context, TestMsgService.class);
        intent.putExtra(KEY_COMMAND, serviceCommand);
        return intent;
    }

    public static final Intent newIntent(Context context){
        return new Intent(context, TestMsgService.class);
    }


    public int getCommand (Intent intent) {
        return intent.getIntExtra(KEY_COMMAND, Command.INVALID);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, " onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, " onBind");
        if (!isServiceStarted()) {
            setServiceStarted(true);
            startSrv(0);
        }
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, " onRebind");
        if (!isServiceStarted()) {
            setServiceStarted(true);
            startSrv(0);
        }
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, " onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, " onDestroy");
        stopSelf();
        setServiceStarted(false);
        super.onDestroy();
    }

}
