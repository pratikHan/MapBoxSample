package com.gotenna.mapboxdemo.Data.local;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gotenna.mapboxdemo.Debug.Loggers;

@Database(entities = {Users.class}, version = 1,exportSchema = false)
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
        Loggers.show(TAG,"Constructor","-->");

        return  instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Loggers.show(TAG, "Callback","Oncreate");

            new PopulateDb(instance).execute();
        }
    };


    private static class PopulateDb extends  AsyncTask<Void,Void,Void>{

        private UsersDao usersDao;
        private PopulateDb(UserDatabase userDatabase){
            usersDao = userDatabase.usersDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Loggers.show(TAG, "PopulateDb", "-->");
            usersDao.insert(new Users("A",12.5,12.5,"D1"));
            usersDao.insert(new Users("B",10.2,19.3,"D2"));
            usersDao.insert(new Users("C",15.22,2.3,"D3"));


            return null;
        }
    }

}
