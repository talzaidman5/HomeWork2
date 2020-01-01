package com.example.homework1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainApp extends AppCompatActivity  {


    private Button main_BTN_NewGame, main_BTN_settings, main_BTN_scoreTable, main_BTN_exit;
    private int start_speed = 1000;
    private MediaPlayer ring = null;
    public static double[] vetLocation;
    private LocationManager locationManager;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ring = MediaPlayer.create(this, R.raw.start_song);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        if (TheGame.checkIfPlay) {
            ring = MediaPlayer.create(this, R.raw.start_song);
            ring.start();
        }
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("SPEED_VALUE"))
                start_speed = extras.getInt("SPEED_VALUE");
        }

        main_BTN_NewGame = findViewById(R.id.main_BTN_NewGame);

        main_BTN_settings = findViewById(R.id.main_BTN_settings);
        main_BTN_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        main_BTN_exit = findViewById(R.id.main_BTN_exit);
        main_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        main_BTN_scoreTable = findViewById(R.id.main_BTN_scoreTable);
        main_BTN_scoreTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoreTable();
            }
        });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                vetLocation = new double[]{location.getLongitude(), location.getLatitude()};
                Log.d(location.getLongitude()+location.getLatitude()+"","tal");
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                finish();
                startActivity(i);
            }
        };

        configure_button();
    }


    public void openSettings() {
        Intent intent1 = new Intent(this, Setting.class);
        finish();
        startActivity(intent1);
    }

    public void openScoreTable() {
        Intent intent = new Intent(this, allScores.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ring.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    public void openNewGame() {
        Intent intent = new Intent(this, TheGame.class);
        intent.putExtra("SPEED_VALUE", start_speed);
        startActivity(intent);
    }

    void configure_button() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        main_BTN_NewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                         return;
                    }
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,listener);
                openNewGame();
            }
        });
    }
}

