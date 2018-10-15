package fyi.jackson.activejournal.worker;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.AppDatabase;
import fyi.jackson.activejournal.data.entities.Position;

public class ThumbnailService extends IntentService {

    public static final String TAG = ThumbnailService.class.getSimpleName();
    public static final String EXTRA_ACTIVITY_ID = "fyi.jackson.activejournal.EXTRA_ACTIVITY_ID";

    public ThumbnailService() {
        super(ThumbnailService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        long activityId = intent.getLongExtra(EXTRA_ACTIVITY_ID, -1);

        if (activityId == -1) {
            Log.e(TAG, "onHandleIntent: EXTRA_ACTIVITY_ID not set");
            return;
        }

        AppDatabase appDatabase = AppDatabase.getDatabase(getApplicationContext());



        List<Position> positions = appDatabase.activityDao().getPositionsForActivity(activityId);

        String encodedPath = encodePositions(positions);
        String thumbnailFileName = generateFileName(encodedPath) + ".png";

        try {

            Bitmap bitmap = Picasso.get().load(buildStaticMapUrl(encodedPath)).get();

            String newFileName = new ImageSaver(getApplicationContext())
                    .setExternal(false)
                    .setDirectoryName("thumbnails")
                    .setFileName(thumbnailFileName)
                    .save(bitmap);

            appDatabase.activityDao().updateThumbnail(activityId, newFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onHandleIntent: Done handling reqest for " + activityId);
    }

    private String encodePositions(List<Position> positions) {
        return PolyUtil.encode(positionToLatLngs(positions));
    }

    private List<LatLng> positionToLatLngs(List<Position> positions) {
        List<LatLng> latLngs = new ArrayList<>();
        for (Position position : positions) {
            latLngs.add(new LatLng(position.getLat(), position.getLng()));
        }
        return latLngs;
    }

    private String buildStaticMapUrl(String encodedPath) {
        String apiKey = getApplicationContext().getString(R.string.google_static_maps_key);
        int width = getImageWidth();
        int height = width * 9 / 16;
        Log.d(TAG, "buildStaticMapUrl: getting image " + width + " wide and " + height + " high");
        return new Uri.Builder()
                .scheme("https")
                .authority("maps.googleapis.com")
                .appendPath("maps")
                .appendPath("api")
                .appendPath("staticmap")
                .appendQueryParameter("key", apiKey)
                .appendQueryParameter("autoscale", "false")
                .appendQueryParameter("size", width + "x" + height)
                .appendQueryParameter("scale", "2")
                .appendQueryParameter("maptype", "roadmap")
                .appendQueryParameter("format", "png")
                .appendQueryParameter("visual_refresh", "true")
                .appendQueryParameter("path", "enc:" + encodedPath)
                .build()
                .toString();
    }

    private String generateFileName(String str) {
        str += System.currentTimeMillis();
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getImageWidth() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels);
        return Math.min(screenWidth / 2, 640);
    }
}
