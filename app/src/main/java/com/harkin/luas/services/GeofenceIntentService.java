package com.harkin.luas.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.harkin.luas.R;

import java.util.List;

/**
 * Created by Intercom on 06/12/14.
 */
public class GeofenceIntentService extends IntentService {

    public GeofenceIntentService() {
        super("geofencin");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (LocationClient.hasError(intent)) {

        } else {
            int transitionType =
                    LocationClient.getGeofenceTransition(intent);
            // Test that a valid transition was reported
            if (
                    (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER)
                            ||
                            (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT)
                    ) {
                List<Geofence> triggerList = LocationClient.getTriggeringGeofences(intent);

                String[] triggerIds = new String[triggerList.size()];

                for (int i = 0; i < triggerIds.length; i++) {
                    // Store the Id of each geofence
                    triggerIds[i] = triggerList.get(i).getRequestId();
                }
                /*
                 * At this point, you can store the IDs for further use
                 * display them, or display the details associated with
                 * them.
                 */

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentTitle("Geofence")
                        .setContentText("You're near " + triggerIds[0]);

                mNotificationManager.notify(123, mBuilder.build());

                // An invalid transition was reported
            } else {
                Log.e("ReceiveTransitionsIntentService",
                        "Geofence transition error: " +
                                Integer.toString(transitionType));
            }
        }
    }
}
