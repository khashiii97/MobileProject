package com.example.finalproject.Utils;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.finalproject.adapters.CourseAdapter;
import com.example.finalproject.adapters.HomeworksAdapter;
import com.example.finalproject.models.Course;
import com.example.finalproject.models.Homework;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DbExecutor {
    final static String TAG = "BookmarkModelView";
    static private DbExecutor me;

    static int NUMBER_OF_CORES = 1;
    static int KEEP_ALIVE_TIME = 2;
    static TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    static BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
    public static ExecutorService executorService = new ThreadPoolExecutor(1,
            1,
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            taskQueue);

    public DbHelper db;
    Context context;
    public DbExecutor(Context context) {
        this.context = context;
        db = new DbHelper(context.getApplicationContext());
    }

    public static DbExecutor getInstance(Context context) {
        if (me == null) me = new DbExecutor(context);
        return me;
    }

    public void addCourse(Course course){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.insertCourse(course);
                // add handler
            }
        });
    }

    public void deleteCourse( int id) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.deleteCourseById(id);
                // add handler
            }
        });
    }

    public void getCourses(CourseAdapter adapter, int day, Activity activity){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Course> result = db.getCourses(day);
//                String test = "yes";
//                if (adapter == null){
//                    test = "no";
//                }
//                Log.d("dbtest", test);
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        adapter.loadCourses(result);
                        // Stuff that updates the UI

                    }
                });

            }
        });
    }

    public void addHomework(Homework homework){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.insertHomework(homework);
                // add handler
            }
        });
    }

    public void deleteHomework(int id) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.deleteHomeworkById(id);
                // add handler
            }
        });
    }

    public void getHomeworks(HomeworksAdapter adapter, Activity activity){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Homework> result = db.getHomework();
//                String test = "yes";
//                if (adapter == null){
//                    test = "no";
//                }
//                Log.d("dbtest", test);
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        adapter.loadHomeworks(result);
                        // Stuff that updates the UI

                    }
                });

            }
        });
    }



}
