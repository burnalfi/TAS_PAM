package com.example.tas_pam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tas_pam.dummy.DummyContent;
import com.example.tas_pam.dummy.CafesViewModel;
import com.example.tas_pam.dummy.SingletonNameViewModelFactory;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsOfCafe extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {


  private static final String TAG = "mapofcafe";
  private GoogleMap mMap;
  private String placeApiKey= "AIzaSyAAxp2pDDOPsA4oyIQR2hhNGNJY5pz7fdU";
  private PlacesClient placesClient;
  private CafesViewModel mvm;
  private int counter=0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps_of_cafe);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);


    Places.initialize(getApplicationContext(), placeApiKey);
    placesClient = Places.createClient(this);
//  @FIXME possible cause of not bug free.
//    ma = (MainActivity) getBaseContext();
    SingletonNameViewModelFactory singletonNameViewModelFactory= new SingletonNameViewModelFactory(CafesViewModel.getInstance());
    mvm= ViewModelProviders.of(this, singletonNameViewModelFactory).get(CafesViewModel.class);
  }


  /**
   * Manipulates the map once available.
   * This callback is triggered when the map is ready to be used.
   * This is where we can add markers or lines, add listeners or move the camera. In this case,
   * we just add a marker near Sydney, Australia.
   * If Google Play services is not installed on the device, the user will be prompted to install
   * it inside the SupportMapFragment. This method will only be triggered once the user has
   * installed Google Play services and returned to the app.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    mMap.setOnMyLocationButtonClickListener(this);
    mMap.setOnMyLocationClickListener(this);
    // Add a marker in a. live b. kanfak fti
    if (mMap != null) {
      mMap.setMyLocationEnabled(true);
      this.listingPlace();
    }
    Log.e(TAG, "onMapReady: ");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.e(TAG, "onPause: "+ counter);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    Log.e(TAG, "onLowMemory: ");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.e(TAG, "onDestroy: ");
  }

  public void listingPlace() {
    List<Place.Field> placeFields = Collections.singletonList(Place.Field.NAME);
    // Use the builder to create a FindCurrentPlaceRequest.
    FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);
    Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
//    placesClient.fetchPlace()
    placeResponse.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
        if (task.isSuccessful()) {
          FindCurrentPlaceResponse response = task.getResult();
          List<DummyContent.Cafe> cx = null;
          for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
            Log.d(TAG, String.format("Place '%s' has id: %f",
                    placeLikelihood.getPlace().getName(),
                    placeLikelihood.getPlace().getLatLng()));
            Place place = placeLikelihood.getPlace();
            cx = new ArrayList<>();
            DummyContent.Cafe placeApiCafe = new DummyContent.Cafe(String.valueOf(placeLikelihood.getLikelihood()), place.getName());
            placeApiCafe.phone = place.getPhoneNumber();
            counter++;
            if (placeApiCafe.phone == null || placeApiCafe.phone.isEmpty()) {
//            empty data from place api
              placeApiCafe.phone = "123456";
            }
            if (place.getOpeningHours() != null) {
              placeApiCafe.hours = place.getOpeningHours().toString();
              if (placeApiCafe.hours == null || placeApiCafe.hours.isEmpty()) {
                placeApiCafe.hours = "09:00-20:00";
              }
            }
            if (null == place.getWebsiteUri()) {
              // no url we can't find the place desc
              placeApiCafe.description = "Sorry we can't find this info, visit them :)";
            } else {
              Uri aCafeUri = place.getWebsiteUri();
              // @TODO visit web and grab the cafe description
//              placeApiCafe.description= resultFromUri;
            }
            placeApiCafe.address = place.getAddress();
            if (placeApiCafe.address == null || placeApiCafe.address.isEmpty()) {
              placeApiCafe.address = "address is empty, but available on gmap";
            }
//            if (!mvm.addCafe(placeApiCafe)) {
//              Log.e(TAG, " killed by android");
//            }
            //            failover
            cx.add(placeApiCafe);
          }
          mvm.setCafesFromPlaceApi(cx);
          mvm.addCafes(cx);
          Log.e(TAG, "about to finish");
          getIntent().putExtra("numCafe", counter);
          finish();
        } else {
          Exception exception = task.getException();
          if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            Log.e(TAG, "Place not found: " + apiException.getStatusCode());
          }
        }
      }
    });
  }
  @Override
  public void onMyLocationClick(@NonNull Location location) {
//    implication??
    Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
  }

  @Override
  public boolean onMyLocationButtonClick() {
    Toast.makeText(this, R.string.open_map_activity, Toast.LENGTH_SHORT).show();
    // Return false so that we don't consume the event and the default behavior still occurs
    // (the camera animates to the user's current position).
    return false;
  }

}
