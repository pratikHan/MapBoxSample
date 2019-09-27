package com.gotenna.mapboxdemo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gotenna.mapboxdemo.Data.local.Users;
import com.gotenna.mapboxdemo.Debug.Loggers;
import com.gotenna.mapboxdemo.UI.DataListActivity;
import com.gotenna.mapboxdemo.ViewModel.UserViewModel;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;


import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener,MapboxMap.OnMapClickListener {


    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private LocationComponent locationComponent;
    private static final String TAG = "MainActivity";

    UserViewModel userViewModel;

    private   LatLng pinWithMaxLat = new LatLng(36.532128, -93.489121);
    private   LatLng pinWitnMaxLon = new LatLng(25.837058, -106.646234);

    List<Users> usersListMain;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Loggers.show(this.getLocalClassName(),"OnCreate","_>");

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsersData().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {

                Loggers.show(TAG,"OnChanged","-->"+users.get(0).getName());
               usersListMain = users;
               setBounds();

            }
        });

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_main);


        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.menu_all:

                Intent intent = new Intent(MainActivity.this, DataListActivity.class);
                startActivityForResult(intent,1);

                return true;

            case R.id.menu_all_pins:
                showAllUsers();
                setCameraBounds(pinWithMaxLat,pinWitnMaxLon);
                return true;


            case R.id.menu_current:
                clearAllMarkers();
                locationComponent.setCameraMode(CameraMode.TRACKING);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Loggers.show(TAG,"OnActivityResult","-->");
    }




    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        MainActivity.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjerxnqt3cgvp2rmyuxbeqme7"),
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                    }
                });



        mapboxMap.addOnMapClickListener(MainActivity.this);
    }



    private void setBounds(){

        pinWithMaxLat = new LatLng(usersListMain.get(0).getLatitude(),usersListMain.get(0).getLongitude());
        pinWitnMaxLon = new LatLng(usersListMain.get(4).getLatitude(),usersListMain.get(4).getLongitude());


    }


    private void showAllUsers(){

        Loggers.show(TAG,"showAllUsers","-->");

        for (Users user: usersListMain
             ) {
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(user.getLatitude(),user.getLongitude()))
                    .title(user.getName()));
        }


    }

    private void clearAllMarkers(){

        mapboxMap.clear();

    }


    private void setCameraBounds(LatLng l1, LatLng l2){

        Toast.makeText(this, "OnMapClick", Toast.LENGTH_LONG).show();

        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(l1) // Northeast
                .include(l2) // Southwest
                .build();

        mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), 5000);
    }








    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
            // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, "USer Location permission granted", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        return true;
    }


    public void showToast (String msg){

        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
