package com.gotenna.mapboxdemo.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.gotenna.mapboxdemo.Data.local.UserDatabase;
import com.gotenna.mapboxdemo.Data.local.Users;
import com.gotenna.mapboxdemo.Data.local.UsersDao;
import com.gotenna.mapboxdemo.Data.remote.ApiUtils;
import com.gotenna.mapboxdemo.Data.remote.WebService;
import com.gotenna.mapboxdemo.Debug.Loggers;
import com.gotenna.mapboxdemo.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {


    private static final String TAG = "UserRepository";
    private UsersDao usersDao;
    LiveData<List<Users>> allusers;

    List<Users> test_users;


    WebService webService;

    public UserRepository (Application application){
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        usersDao = userDatabase.usersDao();
        allusers = usersDao.getAllUsersByLat();
        webService = ApiUtils.getSOService();
        fetchData();
    }

    public void insert (Users users) { new InsertUserAsyncTask(usersDao).execute(users);}

    public LiveData<List<Users>> getAllusers(){return allusers;}


    private static class InsertUserAsyncTask extends AsyncTask<Users, Void, Void>{

        UsersDao usersDao;

        public InsertUserAsyncTask(UsersDao usersDao) {this.usersDao=usersDao;};

        @Override
        protected Void doInBackground(Users... users) {
            Loggers.show(TAG,"InsertuserAsyncTask","-->");
            usersDao.insert(users[0]);
            return null;
        }
    }


    public void fetchData(){

        webService.getAllUsersByLatitude().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {



                if(response.isSuccessful()){
                    test_users = response.body();
                    for (Users user : test_users
                         ) {
                        Loggers.show(TAG,"FetchData","OnResponse :"+user.getName());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

               Loggers.show(TAG,"OnFailure", "-->");

            }
        });


    }
}

