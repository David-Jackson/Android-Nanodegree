package fyi.jackson.activejournal.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import fyi.jackson.activejournal.ActivityMain;
import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.AppViewModel;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Position;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    public static final String TAG = MapFragment.class.getSimpleName();

    public static final String EXTRA_ACTIVITY = "EXTRA_ACTIVITY";

    private Activity currentActivity;
    private List<Position> currentActivityPositions;

    private GoogleMap googleMap;

    public MapFragment() {}

    public static MapFragment newInstance(Activity activity) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ACTIVITY, activity);
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentActivity = getArguments().getParcelable(EXTRA_ACTIVITY);

        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        appViewModel.getPositionsForActivity(currentActivity.getActivityId())
                .observe(this, new Observer<List<Position>>() {
                    @Override
                    public void onChanged(@Nullable List<Position> positions) {
                        currentActivityPositions = positions;
                        updateMap();
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ActivityMain) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        updateMap();
    }

    private void updateMap() {
        if (googleMap == null || currentActivityPositions == null) {
            return;
        }

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        PolylineOptions options = new PolylineOptions();
        options.clickable(true);
        for (Position p : currentActivityPositions) {
            LatLng latlng = new LatLng(p.getLat(), p.getLng());
            boundsBuilder.include(latlng);
            options.add(latlng);
        }

        googleMap.addPolyline(options);

        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30));
    }
}
