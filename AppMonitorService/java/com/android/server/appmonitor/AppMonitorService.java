package com.android.server.appmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.android.server.SystemService;
import android.os.IBinder;
import android.os.Binder;
import android.os.Handler;
import android.util.Slog;

public class AppMonitorService extends SystemService  {
    private static final String TAG = "AppMonitorService";
    private Context mContext;
    private BroadcastReceiver Appcontrol;
    public AppMonitorService(Context context) {
        super(context);
        mContext = context;
        //publishBinderService(context.JOFFEE_SERVICE, mService);
        Slog.i(TAG, "AppMonitorService constructor launched");
    }
    @Override
    public void onStart() {
        Slog.i(TAG, "onStartMethod called");
        Appcontrol = new AppControlReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        mContext.registerReceiver(Appcontrol, filter);
        Slog.i(TAG, "AppControl receiver added");
    }

    public class AppControlReceiver extends BroadcastReceiver {
        public AppControlReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String packageName = intent.getData().getEncodedSchemeSpecificPart();
            if (Intent.ACTION_PACKAGE_ADDED.equals(action))
            {
                Slog.i(TAG, "app installed: " + packageName);
            }
            else
            {
                Slog.i(TAG, "app deleted: " + packageName);
            }
        }
    }
}