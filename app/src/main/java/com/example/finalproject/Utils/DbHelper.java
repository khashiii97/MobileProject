package com.example.finalproject.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.finalproject.models.Course;
import com.example.finalproject.models.Exam;
import com.example.finalproject.models.Homework;
import com.example.finalproject.models.Note;

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


    private static final String EXAMS = "exams";
    private static final String EXAMS_ID = "id";
    private static final String EXAMS_SUBJECT = "subject";
    private static final String EXAMS_TEACHER = "teacher";
    private static final String EXAMS_ROOM = "room";
    private static final String EXAMS_DATE = "date";
    private static final String EXAMS_TIME = "time";



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
        String CREATE_NOTES = "CREATE TABLE " + NOTES + "("
                + NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOTES_TITLE + " TEXT,"
                + NOTES_TEXT + " TEXT"
                 + ")";
        String CREATE_EXAMS = "CREATE TABLE " + EXAMS + "("
                + EXAMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EXAMS_SUBJECT + " TEXT,"
                + EXAMS_TEACHER + " TEXT,"
                + EXAMS_ROOM + " TEXT,"
                + EXAMS_DATE + " TEXT,"
                + EXAMS_TIME + " TEXT"
                + ")";

        db.execSQL(CREATE_COURSES);

        db.execSQL(CREATE_HOMEWORKS);
        db.execSQL(CREATE_NOTES);
        db.execSQL(CREATE_EXAMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("dbbbbbb3","heyyyyy");
        switch (oldVersion) {
            case 1:
                db.execSQL("DROP TABLE IF EXISTS " + COURSE);
            case 2:
                db.execSQL("DROP TABLE IF EXISTS " + HOMEWORKS);
            case 3:
                db.execSQL("DROP TABLE IF EXISTS " + NOTES);


            case 5:
                db.execSQL("DROP TABLE IF EXISTS " + EXAMS);
                break;

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
        Log.d("dbbbbbb4","heyyyyy");
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

    public void updateHomeworkPath(int id,String path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HOMEWORKS_PATH, path);
        db.update(HOMEWORKS, contentValues, HOMEWORKS_ID + " = " + id, null);
        db.close();
    }
    public void deleteHomeworkById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HOMEWORKS,HOMEWORKS_ID + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Homework> getHomework() {
        Log.d("dbbbbbb3","heyyyyy");
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Homework> homelist = new ArrayList<>();
        Homework homework;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ HOMEWORKS ,null);
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

    public void insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, note.getTitle());
        contentValues.put(NOTES_TEXT, note.getText());
        db.insert(NOTES, null, contentValues);
        db.close();
    }

    public void updateNote(Note note)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, note.getTitle());
        contentValues.put(NOTES_TEXT, note.getText());
        db.update(NOTES, contentValues, NOTES_ID + " = " + note.getId(), null);
        db.close();
    }

    public void deleteNoteById(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTES, NOTES_ID + " =? ", new String[] {String.valueOf(note.getId())});
        db.close();
    }

    public ArrayList<Note> getNote() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Note> notelist = new ArrayList<>();
        Note note;
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTES, null);
        while (cursor.moveToNext()) {
            note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex(NOTES_ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(NOTES_TITLE)));
            note.setText(cursor.getString(cursor.getColumnIndex(NOTES_TEXT)));
            notelist.add(note);
        }
        cursor.close();
        db.close();
        return notelist;
    }

    public void insertExam(Exam exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXAMS_SUBJECT, exam.getSubject());
        contentValues.put(EXAMS_TEACHER, exam.getTeacher());
        contentValues.put(EXAMS_ROOM, exam.getRoom());
        contentValues.put(EXAMS_DATE, exam.getDate());
        contentValues.put(EXAMS_TIME, exam.getTime());
        db.insert(EXAMS, null, contentValues);
        db.close();
    }

    public void updateExam(Exam exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXAMS_SUBJECT, exam.getSubject());
        contentValues.put(EXAMS_TEACHER, exam.getTeacher());
        contentValues.put(EXAMS_ROOM, exam.getRoom());
        contentValues.put(EXAMS_DATE, exam.getDate());
        contentValues.put(EXAMS_TIME, exam.getTime());
        db.update(EXAMS, contentValues, EXAMS_ID + " = " + exam.getId(), null);
        db.close();
    }

    public void deleteExamById(Exam exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXAMS, EXAMS_ID + " =? ", new String[] {String.valueOf(exam.getId())});
        db.close();
    }

    public ArrayList<Exam> getExam() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Exam> examslist = new ArrayList<>();
        Exam exam;
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXAMS, null);
        while (cursor.moveToNext()) {
            exam = new Exam();
            exam.setId(cursor.getInt(cursor.getColumnIndex(EXAMS_ID)));
            exam.setSubject(cursor.getString(cursor.getColumnIndex(EXAMS_SUBJECT)));
            exam.setTeacher(cursor.getString(cursor.getColumnIndex(EXAMS_TEACHER)));
            exam.setRoom(cursor.getString(cursor.getColumnIndex(EXAMS_ROOM)));
            exam.setDate(cursor.getString(cursor.getColumnIndex(EXAMS_DATE)));
            exam.setTime(cursor.getString(cursor.getColumnIndex(EXAMS_TIME)));
            examslist.add(exam);
        }
        cursor.close();
        db.close();
        return examslist;
    }

}
