package ru.olegshulika.asmeet3;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class SomeMsgIntentService extends IntentService {
    private static final String ACTION_SENDMSG = "ru.olegshulika.asmeet3.action.SENDMSG";
    private static final String EXTRA_PARAM_DELTA = "ru.olegshulika.asmeet3.extra.DELTA";

    public SomeMsgIntentService() {
        super("SomeMsgIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSendMsg(Context context, String param1) {
        Intent intent = new Intent(context, SomeMsgIntentService.class);
        intent.setAction(ACTION_SENDMSG);
        intent.putExtra(EXTRA_PARAM_DELTA, param1);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SENDMSG.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM_DELTA);
                handleActionSendMsg(param1);
            }
        }
    }

    /**
     * Handle action SendMsg in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSendMsg(String param1) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
