package com.example.finalproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.Utils.DbExecutor;
import com.example.finalproject.models.Course;
import com.example.finalproject.models.Homework;

import java.util.ArrayList;
import java.util.Objects;

public class HomeworksAdapter extends RecyclerView.Adapter<HomeworksAdapter.ViewHolder>  {
    Context context;
    public ArrayList<Homework> homeworks = new ArrayList<>();
    DbExecutor db;
    public HomeworksAdapter(Context context) {
        this.context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView subject;
        public final TextView date;
        public final TextView description;
        public final ImageButton trashBtn;
        public final ImageButton addBtn;


        public View self;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            self = view;
            image = (ImageView) view.findViewById(R.id.hwimage);
            subject = (TextView) view.findViewById(R.id.subjecthomework);
            date = (TextView) view.findViewById(R.id.datehomework);
            description = (TextView) view.findViewById(R.id.descriptionhomework);
            trashBtn = (ImageButton) view.findViewById(R.id.deleteHomework);
            addBtn = (ImageButton) view.findViewById(R.id.addpdfbtn);

        }


    }

    @NonNull
    @Override
    public HomeworksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.homework_card, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HomeworksAdapter.ViewHolder holder, int position)
    {
        Homework homework = homeworks.get(position);
        holder.subject.setText(homework.getSubject());
        holder.date.setText(homework.getDate());
        holder.description.setText(homework.getDescription());
        if (homework.getPath().equals("")){
            holder.image.setBackgroundResource(R.drawable.pdf);
        }
        else{
            //ToDo get image saved from pdf
        }


        holder.trashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbExecutor.getInstance(context).deleteHomework(homework.getId());
                homeworks.remove(position);
                notifyDataSetChanged();
            }

        });

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo
                if (homework.getPath().equals("")){
                    Toast.makeText(context," فایلی برای این تمرین انتخاب نکردی!",Toast.LENGTH_LONG).show();
                }
                else{

                }
            }
        });
    }
    public void loadHomeworks(ArrayList<Homework> loadedhomeworks)
    {
        //Log.d("courseaddapt", String.valueOf(loadedCourses.size()));
        homeworks.clear();
        homeworks.addAll(loadedhomeworks);
        notifyDataSetChanged();
    }
    public void addCourses(Homework newHomework){
        homeworks.add(newHomework);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return homeworks.size();
    }



}