package com.gotenna.mapboxdemo.Data.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users_table")
public class Users {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("latitude")
    @ColumnInfo(name = "latitude")
    private double latitude;

    @SerializedName("longitude")
    @ColumnInfo(name = "longitude")
    private double longitude;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String description;

    public Users( String name, double latitude, double longitude, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }


    public void setId(int id){
        this.id= id;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
