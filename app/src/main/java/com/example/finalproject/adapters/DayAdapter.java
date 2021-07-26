package com.example.finalproject.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.res.Resources;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.finalproject.R;
import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder>{
    private Context mcontext;
    private final List<Integer> days = new ArrayList();
    private OnItemClickListener mOnItemClickListener;
    public DayAdapter(Context context) {
        mcontext = context;
    }
    public void addAllDays(){
        for(int i = 0; i< 7; i++)
        {
            days.add(i);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_card, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        Resources res = mcontext.getResources();
        if(position == 0){
            holder.relativeView.setBackground(res.getDrawable(R.drawable.saturday));
        }
        else if (position == 1){
            holder.relativeView.setBackground(res.getDrawable(R.drawable.sunday));
        }
        else if (position == 2){
            holder.relativeView.setBackground(res.getDrawable(R.drawable.monday));
        }else if (position == 3){
            holder.relativeView.setBackground(res.getDrawable(R.drawable.tuesday));
        }else if (position == 4){
            holder.relativeView.setBackground(res.getDrawable(R.drawable.wednesday));
        }else if (position == 5){
            holder.relativeView.setBackground(res.getDrawable(R.drawable.thursday));
        }else if (position == 6){
            holder.relativeView.setBackground(res.getDrawable(R.drawable.friday));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.relativeView.setTransitionName("shared" + String.valueOf(position));
        }
        holder.relativeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClicked(holder.getAdapterPosition(), holder.relativeView);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return 7;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout relativeView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            relativeView = (RelativeLayout) view.findViewById(R.id.day_linear);
        }


    }
    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public interface  OnItemClickListener {
        void onItemClicked(int pos, View view);
    }

}

