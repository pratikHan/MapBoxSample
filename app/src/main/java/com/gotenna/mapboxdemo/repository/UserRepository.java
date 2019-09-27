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
    private LiveData<List<Users>> allUsers;
    private WebService webService;

    public UserRepository(Application application) {
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        webService = ApiUtils.getSOService();
        fetchData();
        usersDao = userDatabase.usersDao();
        allUsers = usersDao.getAllUsersByLat();


    }


    private void insert(Users users) {
        new InsertUserAsyncTask(usersDao).execute(users);
    }


    public LiveData<List<Users>> getAllUsers() {
        return allUsers;
    }


    private static class InsertUserAsyncTask extends AsyncTask<Users, Void, Void> {

        UsersDao usersDao;

        private InsertUserAsyncTask(UsersDao usersDao) {
            this.usersDao = usersDao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            usersDao.insert(users[0]);

            return null;
        }
    }


    private void fetchData() {


        webService.getAllUsersByLatitude().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful()) {
                    for (Users users : response.body()
                    ) {
                        insert(users);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

                Loggers.show(TAG, "OnFailure", "Failure while Fetching Data");


            }
        });


    }
}

