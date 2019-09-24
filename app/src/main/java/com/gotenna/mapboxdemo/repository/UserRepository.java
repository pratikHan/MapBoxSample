package com.gotenna.mapboxdemo.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gotenna.mapboxdemo.Data.local.UserDatabase;
import com.gotenna.mapboxdemo.Data.local.Users;
import com.gotenna.mapboxdemo.Data.local.UsersDao;

import java.util.List;

public class UserRepository {


    private static final String TAG = "UserRepository";
    private UsersDao usersDao;
    LiveData<List<Users>> allusers;

    public UserRepository (Application application){
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        usersDao = userDatabase.usersDao();
        allusers = usersDao.getAllUsersByLat();
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
}

