package com.example.applogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private ArrayList namect;

    public Adapter(Context context ,ArrayList namect){
        this.context =context;
        this.namect= namect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // return null;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.ctname.setText(String.valueOf(namect.get(position)));
    }

    @Override
    public int getItemCount() {
        return namect.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ctname;
        public ViewHolder(View view) {
            super(view);
            ctname= view.findViewById(R.id.namect);

        }
    }
}
