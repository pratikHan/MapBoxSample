package com.gotenna.mapboxdemo.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gotenna.mapboxdemo.Data.local.Users;
import com.gotenna.mapboxdemo.R;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {


    private final static String TAG = "DataAdapter";
    List<Users>  users = new ArrayList<>();


    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_item,parent,false);

        return new DataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {

        Users currentUser = users.get(position);
        holder.textViewName.setText(currentUser.getName());
        holder.textViewDescription.setText(currentUser.getDescription());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUserData(List<Users> userData){
        this.users = userData;
        notifyDataSetChanged();
    }

    class DataHolder extends RecyclerView.ViewHolder{

        private TextView textViewName;
        private TextView textViewDescription;



        public DataHolder(View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDescription = itemView.findViewById(R.id.text_view_description);

        }
    }
}
