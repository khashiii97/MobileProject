package com.example.finalproject.adapters;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.Utils.DbExecutor;
import com.example.finalproject.Utils.PDFUtils;
import com.example.finalproject.models.Course;
import com.example.finalproject.models.Homework;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class HomeworksAdapter extends RecyclerView.Adapter<HomeworksAdapter.ViewHolder>  {
    Context context;
    ActivityResultLauncher<Intent> getPdfActivity;
    public ArrayList<Homework> homeworks = new ArrayList<>();
    DbExecutor db;
    public HomeworksAdapter(Context context,ActivityResultLauncher<Intent> launcher) {
        this.context = context;
        this.getPdfActivity = launcher;
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
            holder.image.setBackgroundResource(R.drawable.pdf1);
        }
        else{
            String path = context.getFilesDir() + "/" + String.valueOf(homework.getId())+".jpg";
            File imgFile = new  File(path);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.image.setImageBitmap(myBitmap);

            }
            else{
                holder.image.setBackgroundResource(R.drawable.pdf);
            }
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
                DbExecutor.getInstance(context).currentHomework.set(homework.getId());
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                getPdfActivity.launch(intent);
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
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(homework.getPath());
                    Log.d("pdfffff", uri.toString());
//                    Uri uri2 = Uri.parse("content://providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FAdobe%20Acrobat%2Fmodulation.pdf");
                    intent.setDataAndType(uri, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    try
                    {
                        context.startActivity(intent);
                    }
                    catch(ActivityNotFoundException e)
                    {
                        Toast.makeText(context, "اپلیکشینی برای باز کردن فایل نداری!", Toast.LENGTH_LONG).show();
                    }

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
    public void addHomework(Homework newHomework){
        homeworks.add(newHomework);
        notifyDataSetChanged();
    }
    public void updateHomeworkPath(int id,String path){
        for(int i = 0; i < homeworks.size(); i++)
        {
            if (homeworks.get(i).getId() == id){
                homeworks.get(i).setPath(path);
            }
        }
    }



    @Override
    public int getItemCount() {
        return homeworks.size();
    }



}