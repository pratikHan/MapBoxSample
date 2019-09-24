package com.gotenna.mapboxdemo.Data.remote;

import com.gotenna.mapboxdemo.Data.local.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("/development/scripts/get_map_pins.php")
    Call<List<Users>> getAllUsersByLatitude();
}
