package com.example.xavierdecazenove1.ass2.ClassStatic;

import com.google.android.gms.maps.model.LatLng;

public class User {

    private String name;
    private LatLng coordonnee;

    public User(String name, LatLng coordonnee){
        this.name = name;
        this.coordonnee = coordonnee;
    }


    public void setName(String name) { this.name = name; }

    public void setCoordonnee(LatLng coordonnee) { this.coordonnee = coordonnee; }


    public String getName() { return name; }

    public LatLng getCoordonnee() { return coordonnee; }

}
