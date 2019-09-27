package com.gotenna.mapboxdemo.Data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (Users user);

    @Query("DELETE from users_table")
    void deleteAllNodes();

    @Query("Select * from users_table ORDER BY latitude DESC")
    LiveData<List<Users>> getAllUsersByLat();

    @Query("Select * from users_table ORDER BY longitude DESC")
    LiveData<List<Users>> getAllUSersByLongitude();
}
