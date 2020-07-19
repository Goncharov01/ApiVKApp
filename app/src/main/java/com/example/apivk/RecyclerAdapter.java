package com.example.apivk;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private Context context;
    private List<TaskModel> listTaskModel;


    public RecyclerAdapter(Context context, List<TaskModel> listTaskModel) {
        this.context = context;
        this.listTaskModel = listTaskModel;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView lastName;
        TextView online;
        ImageView photo;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameView);
            lastName = itemView.findViewById(R.id.lastNameView);
            online = itemView.findViewById(R.id.onlineView);
            photo = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerViewHolder holder, int position) {
        if (listTaskModel != null) {
            TaskModel taskModel = this.listTaskModel.get(position);
            holder.name.setText(taskModel.getName());
            holder.lastName.setText(taskModel.getLastName());
            holder.online.setText(taskModel.getOnline());

            Picasso.get()
                    .load(Uri.parse(listTaskModel.get(position).getPhoto()+"png"))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.photo);
        }
    }

    @Override
    public int getItemCount() {
        return listTaskModel.size();
    }

    public void onChange(List<TaskModel> taskModels) {
        notifyDataSetChanged();
    }

}
