package com.example.tas_pam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
  private AppBarConfiguration mAppBarConfiguration;
  private boolean mPermissionDenied;

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
  public void onClick(View v) {
    Intent intent= new Intent(MainActivity.this, MapsOfCafe.class);
    startActivity(intent);
  }
}
