package com.gotenna.mapboxdemo.Data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gotenna.mapboxdemo.Debug.Loggers;

@Database(entities = {Users.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static final String TAG = "USERDATABASE";
    private static UserDatabase instance;
    public abstract UsersDao usersDao();


    public static synchronized UserDatabase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context,
                    UserDatabase.class, "user_database.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }


        return  instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Loggers.show(TAG, "Callback","Oncreate");


        }
    };

}
