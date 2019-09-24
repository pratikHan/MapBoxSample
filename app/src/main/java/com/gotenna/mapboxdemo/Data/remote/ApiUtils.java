package com.gotenna.mapboxdemo.Data.remote;

public class ApiUtils {

    private static final String BASE_URL = "https://annetog.gotenna.com";

    public static WebService getSOService() {
        return RetrofitService.getRetrofitInstance(BASE_URL).create(WebService.class);
    }
}
