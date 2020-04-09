package com.example.tas_pam;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.ResourceBusyException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.tas_pam.dummy.DummyContent;
import com.example.tas_pam.ui.slideshow.SlideshowViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CafeFragment.OnListFragmentInteractionListener {
  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
  private static final int GMAP_ACTIVITY = 2;
  private AppBarConfiguration mAppBarConfiguration;
  private boolean mPermissionDenied;
  public SlideshowViewModel mainViewModel; // refactor class name?

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(this);
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    mAppBarConfiguration = new AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
            .setDrawerLayout(drawer)
            .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);
    enableMyLocation();
    // app level viewModel? i am not sure, it work on TTS
    mainViewModel= new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(SlideshowViewModel.class);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode==GMAP_ACTIVITY) {
      if (resultCode==Activity.RESULT_OK) {
        int numCafe= data.getIntExtra("numCafe", 0);
        if (numCafe>0) {
//          activate listing
        }
      }
      if (resultCode==Activity.RESULT_CANCELED) {
        Toast.makeText(this, "you cancel?", Toast.LENGTH_LONG).show();
      }
    }
  }

  private void enableMyLocation() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

    } else {
      // Permission to access the location is missing. Show rationale and request permission
      PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
              Manifest.permission.ACCESS_FINE_LOCATION, true);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
      return;
    }

    if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
      // Enable the my location layer if the permission has been granted.
      enableMyLocation();
    } else {
      // Permission was denied. Display an error message
      // [START_EXCLUDE]
      // Display the missing permission error dialog when the fragments resume.
      mPermissionDenied = true;
      // [END_EXCLUDE]
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onSupportNavigateUp() {
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
            || super.onSupportNavigateUp();
  }

  @Override
  protected void onStart() {
    super.onStart();
    try {
      this.fetchData();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    System.err.println("ma onstart");
  }

  @Override
  public void onClick(View v) {
    Intent intent= new Intent(MainActivity.this, MapsOfCafe.class);
    startActivityForResult(intent, GMAP_ACTIVITY);
  }

  private void fetchData() throws JSONException {
    Context context = this;
    InputStream inputStream = context.getResources().openRawResource(R.raw.dummy_cafe);
    String jsonString = new Scanner(inputStream).useDelimiter("\\A").next();
    JSONObject cafes= new JSONObject(jsonString);
    ArrayList<DummyContent.Cafe> dataCafe= new ArrayList<>();
    for (int i= 0; i<cafes.length();i++) {
      JSONObject cafe= (JSONObject) cafes.get(String.valueOf(i));
      DummyContent.Cafe c= new DummyContent.Cafe(String.valueOf(i), cafe.optString("establishment"));
      c.address= cafe.optString("address");
      c.description= cafe.optString("description");
      c.hours= cafe.optString("hours");
      c.phone= cafe.optString("phone");
      dataCafe.add(c);
//      System.err.println("loop: "+ cafe.optString("establishment", "nama cafe"));
    }
//    set viewModel, currently based on dummy data
    mainViewModel.setCafes(dataCafe);
  }

  @Override
  public void onListFragmentInteraction(DummyContent.Cafe item) {
    System.err.println("onListFragmentInteraction : "+ item.establishment);
  }
}