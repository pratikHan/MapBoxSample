package com.gotenna.mapboxdemo.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gotenna.mapboxdemo.Data.local.Users;
import com.gotenna.mapboxdemo.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    UserRepository userRepository;
    LiveData<List<Users>> allUsers;

    public UserViewModel (Application application){
        super(application);

        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllusers();

    }

    public LiveData<List<Users>> getAllUsersData(){return  allUsers;}

}
