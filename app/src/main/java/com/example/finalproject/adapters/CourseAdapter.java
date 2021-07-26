package com.example.finalproject.adapters;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Utils.DbExecutor;
import com.example.finalproject.models.Course;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    Context context;
    public ArrayList<Course> courses = new ArrayList<>();
    DbExecutor db;

    public CourseAdapter(Context context) {
        this.context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView subject;
        public final TextView room;
        public final TextView time;
        public final TextView teacher;
        public final ImageButton trashBtn;

        public TextView name;
        public TextView coordinates;
        public View self;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            self = view;
            subject = (TextView) view.findViewById(R.id.subject);
            room = (TextView) view.findViewById(R.id.room);
            time = (TextView) view.findViewById(R.id.time);
            teacher = (TextView) view.findViewById(R.id.teacher);
            trashBtn = (ImageButton) view.findViewById(R.id.deletebtn);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Course course = courses.get(position);
        holder.subject.setText(course.getSubject());
        holder.room.setText(course.getRoom());
        holder.teacher.setText(course.getTeacher());
        String time = course.getTotime() +  " تا " + course.getFromtime();
        holder.time.setText(time);


        holder.trashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbExecutor.getInstance(context).deleteCourse(course.getId());
                courses.remove(position);
                notifyDataSetChanged();
            }

        });
    }
    public void loadCourses(ArrayList<Course> loadedCourses)
    {
        //Log.d("courseaddapt", String.valueOf(loadedCourses.size()));
        courses.clear();
        courses.addAll(loadedCourses);
        notifyDataSetChanged();
    }
    public void addCourses(Course newCourse){
        courses.add(newCourse);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}
