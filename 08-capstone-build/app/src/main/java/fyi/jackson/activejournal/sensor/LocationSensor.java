package fyi.jackson.activejournal.sensor;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public abstract class LocationSensor implements LocationListener {

    private static final String TAG = "LOCATION_LISTENER";
    private static final long MIN_TIME_BETWEEN_UPDATES = 2000;
    private static final float MIN_DIST_BETWEEN_UPDATES = 10f;
    private final LocationManager locationManager;
    
    public LocationSensor(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
    
    public void start() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MIN_TIME_BETWEEN_UPDATES,
                    MIN_DIST_BETWEEN_UPDATES, this);
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.d(TAG, "start: We do not have location permissions");
        }
    }

    public void stop() {
        locationManager.removeUpdates(this);
    }

    public abstract void onUpdate(Location location);

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG,
                "onLocationChanged: Lat: " + location.getLatitude() +
                        ",  lng: " + location.getLongitude() +
                        ", alt: " + location.getAltitude() +
                        ", acc: " + location.getAccuracy()
        );
        onUpdate(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
