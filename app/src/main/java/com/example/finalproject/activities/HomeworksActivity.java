package com.example.finalproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.Utils.AlertDialogsHelper;
import com.example.finalproject.Utils.DbExecutor;
import com.example.finalproject.Utils.DbHelper;
import com.example.finalproject.adapters.HomeworksAdapter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Utils.ZoomRecyclerLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeworksActivity extends AppCompatActivity {

    private Context context = this;
    private HomeworksAdapter madapter;
    private DbHelper db;
    ActivityResultLauncher<Intent> getPdfActivity;
    private ZoomRecyclerLayout mZoomRecyclerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeworks);
        initAll();


    }

    private void initAll(){
        getPdfActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri selectedPdf = data.getData();
                            DbExecutor.getInstance(HomeworksActivity.this).updateHomeworkPath(DbExecutor.getInstance(HomeworksActivity.this).currentHomework.get(),selectedPdf.toString());
                            DbExecutor.getInstance(HomeworksActivity.this).savePDFImage(DbExecutor.getInstance(HomeworksActivity.this).currentHomework.get(),selectedPdf);


                        }
                    }
                });

        final RecyclerView recyclerView = findViewById(R.id.rvhomeworks);
        mZoomRecyclerLayout = new ZoomRecyclerLayout(this);
        mZoomRecyclerLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        mZoomRecyclerLayout.setReverseLayout(true);
        mZoomRecyclerLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(mZoomRecyclerLayout);
        madapter = new HomeworksAdapter(this,getPdfActivity);
        recyclerView.setAdapter(madapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabHomework);
        fab .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add_homework, null);
                AlertDialogsHelper.getAddHomeworkDialog(HomeworksActivity.this,view,madapter).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DbExecutor.getInstance(this).getHomeworks(madapter,this);
    }
}

