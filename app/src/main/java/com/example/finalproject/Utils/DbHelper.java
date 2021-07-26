package com.example.finalproject.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.finalproject.models.Course;
import com.example.finalproject.models.Homework;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 6;
    private static final String DB_NAME = "projectdb";
    private static final String COURSE = "course";
    private static final String COURSE_DAY = "day";
    private static final String COURSE_ID = "id";
    private static final String COURSE_SUBJECT = "subject";
    private static final String COURSE_TEACHER = "teacher";
    private static final String COURSE_ROOM = "room";
    private static final String COURSE_FROM_TIME = "fromtime";
    private static final String COURSE_TO_TIME = "totime";

    private static final String HOMEWORKS = "homeworks";
    private static final String HOMEWORKS_ID  = "id";
    private static final String HOMEWORKS_SUBJECT = "subject";
    private static final String HOMEWORKS_DESCRIPTION = "description";
    private static final String HOMEWORKS_DATE = "date";
    private static final String HOMEWORKS_PATH = "path";

    private static final String NOTES = "notes";
    private static final String NOTES_ID = "id";
    private static final String NOTES_TITLE = "title";
    private static final String NOTES_TEXT = "text";
    private static final String NOTES_COLOR = "color";

    private static final String TEACHERS = "teachers";
    private static final String TEACHERS_ID = "id";
    private static final String TEACHERS_NAME = "name";
    private static final String TEACHERS_POST = "post";
    private static final String TEACHERS_PHONE_NUMBER = "phonenumber";
    private static final String TEACHERS_EMAIL = "email";
    private static final String TEACHERS_COLOR = "color";

    private static final String EXAMS = "exams";
    private static final String EXAMS_ID = "id";
    private static final String EXAMS_SUBJECT = "subject";
    private static final String EXAMS_TEACHER = "teacher";
    private static final String EXAMS_ROOM = "room";
    private static final String EXAMS_DATE = "date";
    private static final String EXAMS_TIME = "time";
    private static final String EXAMS_COLOR = "color";


    public DbHelper(Context context){
        super(context , DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COURSES = "CREATE TABLE " + COURSE + "("
                + COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COURSE_DAY + " INTEGER,"
                + COURSE_SUBJECT + " TEXT,"
                + COURSE_ROOM + " TEXT,"
                + COURSE_TEACHER + " TEXT,"
                + COURSE_FROM_TIME + " TEXT,"
                + COURSE_TO_TIME + " TEXT"  +  ")";
        String CREATE_HOMEWORKS = "CREATE TABLE " + HOMEWORKS + "("
                + HOMEWORKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HOMEWORKS_SUBJECT + " TEXT,"
                + HOMEWORKS_DESCRIPTION + " TEXT,"
                + HOMEWORKS_DATE + " TEXT,"
                + HOMEWORKS_PATH + " TEXT"
                + ")";
        db.execSQL(CREATE_COURSES);
        db.execSQL(CREATE_HOMEWORKS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("DROP TABLE IF EXISTS " + COURSE);
            case 2:
                db.execSQL("DROP TABLE IF EXISTS " + HOMEWORKS);

        }
        onCreate(db);
    }
    /**
     * Methods for course
     **/
    public void insertCourse(Course course){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_SUBJECT, course.getSubject());
        contentValues.put(COURSE_DAY, course.getDay());
        contentValues.put(COURSE_TEACHER, course.getTeacher());
        contentValues.put(COURSE_ROOM, course.getRoom());
        contentValues.put(COURSE_FROM_TIME, course.getFromtime());
        contentValues.put(COURSE_TO_TIME, course.getTotime());
        db.insert(COURSE,null, contentValues);
//        db.update(COURSE, contentValues, COURSE_ID, null);
        db.close();
    }
    public void deleteCourseById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COURSE, COURSE_ID + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_SUBJECT, course.getSubject());
        contentValues.put(COURSE_DAY, course.getDay());
        contentValues.put(COURSE_TEACHER, course.getTeacher());
        contentValues.put(COURSE_ROOM, course.getRoom());
        contentValues.put(COURSE_FROM_TIME, course.getFromtime());
        contentValues.put(COURSE_TO_TIME, course.getTotime());
        db.update(COURSE, contentValues, COURSE_ID + " = " + course.getId(), null);
        db.close();
    }


    public ArrayList<Course> getCourses(int day){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Course> courselist = new ArrayList<>();
        Course course;
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM "+COURSE+ ")" ,null);
        while (cursor.moveToNext()){
            course = new Course();
            course.setId(cursor.getInt(cursor.getColumnIndex(COURSE_ID)));
            course.setSubject(cursor.getString(cursor.getColumnIndex(COURSE_SUBJECT)));
            course.setDay(cursor.getInt(cursor.getColumnIndex(COURSE_DAY)));
            course.setRoom(cursor.getString(cursor.getColumnIndex(COURSE_ROOM)));
            course.setTeacher(cursor.getString(cursor.getColumnIndex(COURSE_TEACHER)));
            course.setFromtime(cursor.getString(cursor.getColumnIndex(COURSE_FROM_TIME)));
            course.setTotime(cursor.getString(cursor.getColumnIndex(COURSE_TO_TIME)));
            if(course.getDay() == day)
            {
                courselist.add(course);
            }


        }
        return  courselist;
    }


    public void insertHomework(Homework homework) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HOMEWORKS_SUBJECT, homework.getSubject());
        contentValues.put(HOMEWORKS_DESCRIPTION, homework.getDescription());
        contentValues.put(HOMEWORKS_DATE, homework.getDate());
        contentValues.put(HOMEWORKS_PATH, homework.getPath());
        db.insert(HOMEWORKS,null, contentValues);
        db.close();
    }
    public void updateHomework(Homework homework) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HOMEWORKS_SUBJECT, homework.getSubject());
        contentValues.put(HOMEWORKS_DESCRIPTION, homework.getDescription());
        contentValues.put(HOMEWORKS_DATE, homework.getDate());
        contentValues.put(HOMEWORKS_PATH, homework.getPath());
        db.update(HOMEWORKS, contentValues, HOMEWORKS_ID + " = " + homework.getId(), null);
        db.close();
    }
    public void deleteHomeworkById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HOMEWORKS,HOMEWORKS_ID + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Homework> getHomework() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Homework> homelist = new ArrayList<>();
        Homework homework;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ HOMEWORKS + " ORDER BY datetime(" + HOMEWORKS_DATE + ") ASC",null);
        while (cursor.moveToNext()){
            homework = new Homework();
            homework.setId(cursor.getInt(cursor.getColumnIndex(HOMEWORKS_ID)));
            homework.setSubject(cursor.getString(cursor.getColumnIndex(HOMEWORKS_SUBJECT)));
            homework.setDescription(cursor.getString(cursor.getColumnIndex(HOMEWORKS_DESCRIPTION)));
            homework.setDate(cursor.getString(cursor.getColumnIndex(HOMEWORKS_DATE)));
            homework.setPath(cursor.getString(cursor.getColumnIndex(HOMEWORKS_PATH)));
            homelist.add(homework);
        }
        cursor.close();
        db.close();
        return  homelist;
    }
}
