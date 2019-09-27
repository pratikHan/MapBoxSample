package com.gotenna.mapboxdemo.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.gotenna.mapboxdemo.Data.local.Users;
import com.gotenna.mapboxdemo.Debug.Loggers;
import com.gotenna.mapboxdemo.R;
import com.gotenna.mapboxdemo.ViewModel.UserViewModel;

import java.util.List;

public class DataListActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final DataAdapter adapter = new DataAdapter();
        recyclerView.setAdapter(adapter);



        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsersData().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {

                adapter.setUserData(users);
            }
        });
        
    }
}
