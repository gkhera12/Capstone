package com.example.eightleaves.comedybox.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ComedySyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static ComedySyncAdapter comedySyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (comedySyncAdapter == null) {
                comedySyncAdapter = new ComedySyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return comedySyncAdapter.getSyncAdapterBinder();
    }
}
