package com.example.finalproject.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject.R;
import com.example.finalproject.Utils.AlertDialogsHelper;
import com.example.finalproject.Utils.DbExecutor;
import com.example.finalproject.adapters.CourseAdapter;
import com.example.finalproject.models.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class DayFragment extends Fragment{
    private int day;
    private ImageView dayPhoto;
    private RecyclerView rvCourses;
    private Toolbar toolbar;
    CourseAdapter madapter;
    FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TODO
        if (getArguments() != null) {
//            mSportCardModel = getArguments().getParcelable(EXTRA_SRORT_CARD_MODEL);
            day = getArguments().getInt("day");
            madapter = new CourseAdapter(getContext());
        }
//        if (savedInstanceState != null) {
//            mSportCardModel = savedInstanceState.getParcelable(EXTRA_SRORT_CARD_MODEL);
//        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        dayPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        rvCourses = (RecyclerView) view.findViewById(R.id.rvCourses);
        fab = (FloatingActionButton) view.findViewById(R.id.fabCourse);
        return view;
    }

    public String getDayName(int day)
    {
        switch(day){
            case 0:
                return "شنبه";
            case 1:
                return "یکشنبه";
            case 2:
                return "دوشنبه";
            case 3:
                return "سه شنبه";
            case 4:
                return "چهارشنبه";
            case 5:
                return "پنجشنبه";
            case 6:
                return "جمعه";
            default:
                return "هفت شنبه!";

        }
    }
    public int getPhoto(int day){
        switch(day){
            case 0:
                return R.drawable.saturday;
            case 1:
                return R.drawable.sunday;
            case 2:
                return R.drawable.monday;
            case 3:
                return R.drawable.tuesday;
            case 4:
                return R.drawable.wednesday;
            case 5:
                return R.drawable.thursday;
            default:
                return R.drawable.friday;

        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setTitle(getDayName(day));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Resources res = getContext().getResources();
        toolbar.setBackgroundColor(res.getColor(R.color.colorPrimaryNight));
        dayPhoto.setImageResource(getPhoto(day));


        rvCourses.setAdapter(madapter);
        rvCourses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvCourses.setItemAnimator(new DefaultItemAnimator());
        rvCourses.addItemDecoration(new DividerItemDecoration(getContext()));
        DbExecutor.getInstance(getContext()).getCourses(madapter,day,getActivity());




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add_course, null);
                AlertDialogsHelper.getAddSubjectDialog(getContext(),view,madapter,day).show();
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO
//        outState.putParcelable(EXTRA_SRORT_CARD_MODEL, mSportCardModel);
        super.onSaveInstanceState(outState);
    }
    static class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable mDivider;

        /**
         * Default divider will be used
         */
        public DividerItemDecoration(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        /**
         * Custom divider will be used
         */
        public DividerItemDecoration(Context context, int resId) {
            mDivider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }



}
