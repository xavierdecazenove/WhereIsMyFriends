package com.example.xavierdecazenove1.ass2.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.xavierdecazenove1.ass2.Connection.Message;
import com.example.xavierdecazenove1.ass2.Controller;
import com.example.xavierdecazenove1.ass2.TranslateClass.LocaleHelper;
import com.example.xavierdecazenove1.ass2.R;
import com.google.android.gms.maps.model.LatLng;

public class ManagerActivity extends AppCompatActivity {

    private Button create;
    private Button leave;
    private Button showGroups;
    private Button maps;

    private TextView currentGroup;
    private TextView whatToDo;

    private Spinner spinner;

    private Controller controller;
    private LocationManager locationManager;
    private LocationListener locationListener;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        controller = (Controller) getApplication();

        create = (Button)findViewById(R.id.creat);
        leave = (Button)findViewById(R.id.leave);
        showGroups = (Button)findViewById(R.id.showGroups);
        maps = (Button)findViewById(R.id.map);
        currentGroup = (TextView)findViewById(R.id.currentGroup);
        whatToDo = (TextView)findViewById(R.id.textView2);
        spinner = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));
        spinner.setAdapter(mAdapter);

        if (LocaleHelper.getLanguage(ManagerActivity.this).equalsIgnoreCase("en")) {
            spinner.setSelection(mAdapter.getPosition("English"));
        } else if (LocaleHelper.getLanguage(ManagerActivity.this).equalsIgnoreCase("sv")) {
            spinner.setSelection(mAdapter.getPosition("Swedish"));
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Context context;
                Resources resources;
                switch (position) {
                    case 0:
                        context = LocaleHelper.setLocale(ManagerActivity.this, "en");
                        resources = context.getResources();
                        create.setText(resources.getString(R.string.CreateAGroup));
                        leave.setText(resources.getString(R.string.LeaveTheGroup));
                        showGroups.setText(resources.getString(R.string.ViewGroups));
                        maps.setText(resources.getString(R.string.SeeMap));
                        currentGroup.setText(resources.getString(R.string.yourGroup));
                        whatToDo.setText(resources.getString(R.string.WhatToDo));
                        break;
                    case 1:
                        context = LocaleHelper.setLocale(ManagerActivity.this, "sv");
                        resources = context.getResources();
                        create.setText(resources.getString(R.string.CreateAGroup));
                        leave.setText(resources.getString(R.string.LeaveTheGroup));
                        showGroups.setText(resources.getString(R.string.ViewGroups));
                        maps.setText(resources.getString(R.string.SeeMap));
                        currentGroup.setText(resources.getString(R.string.yourGroup));
                        whatToDo.setText(resources.getString(R.string.WhatToDo));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                controller.user.setCoordonnee(new LatLng(location.getLatitude(),location.getLongitude()));

                if(!controller.nameGroupCurrent.getId().equals("")){
                    controller.sendMessage(Message.
                            setPosition(
                                    controller.nameGroupCurrent.getId(),
                                    Double.toString(controller.user.getCoordonnee().latitude),
                                    Double.toString(controller.user.getCoordonnee().longitude)));
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1 );
        } else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locationListener);
        }



        if(controller.nameGroupCurrent.getId().equals("")){
            currentGroup.setText(getResources().getString(R.string.noGroup));
        } else{
            currentGroup.setText(getResources().getString(R.string.yourGroup) + controller.nameGroupCurrent.getName());
        }




        ////// LAUNCH ACTIVITIES //////

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(),CreatGroupActivity.class);
                startActivity(otherActivity);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!controller.nameGroupCurrent.getId().equals("")){
                    controller.sendMessage(Message.unregistration(controller.nameGroupCurrent.getId()));
                    currentGroup.setText(getResources().getString(R.string.noGroup));
                }
            }
        });

        showGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(),CurrentGroupActivity.class);
                controller.currentGroups.clear();
                controller.sendMessage(Message.currentGroups());
                startActivity(otherActivity);
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(otherActivity);
            }
        });


    }
}
