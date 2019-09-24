package com.gotenna.mapboxdemo.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gotenna.mapboxdemo.Data.local.UserDatabase;
import com.gotenna.mapboxdemo.Data.local.Users;
import com.gotenna.mapboxdemo.Data.local.UsersDao;
import com.gotenna.mapboxdemo.Data.remote.ApiUtils;
import com.gotenna.mapboxdemo.Data.remote.WebService;
import com.gotenna.mapboxdemo.Debug.Loggers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {


    private static final String TAG = "UserRepository";
    private UsersDao usersDao;
    LiveData<List<Users>> allusers;

    WebService webService;

    public UserRepository (Application application){
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        usersDao = userDatabase.usersDao();
        allusers = usersDao.getAllUsersByLat();
        webService = ApiUtils.getSOService();
    }

    public void insert (Users users) {}


    private static class InsertUserAsyncTask extends AsyncTask<Users, Void, Void>{

        UsersDao usersDao;

        public InsertUserAsyncTask(UsersDao usersDao) {this.usersDao=usersDao;};

        @Override
        protected Void doInBackground(Users... users) {
            usersDao.insert(users[0]);
            return null;
        }
    }


    public void fetchData(){

        webService.getAllUsersByLatitude().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {

                if(response.isSuccessful()){
                    Loggers.show(TAG,"FetchData","OnResponse :"+response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });


    }
}
