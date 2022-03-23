package com.example.mietapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public MyRecyclerViewAdapter(Context context, Cursor cursor){
    mContext = context;
    mCursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textname;
        public TextView textvon;
        public TextView textbis;
        public TextView texthandy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textname = itemView.findViewById(R.id.getName);
            textbis = itemView.findViewById(R.id.getBis);
            textvon = itemView.findViewById(R.id.getVon);
            texthandy = itemView.findViewById(R.id.getHandy);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.single_item, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(DataBaseHelper.COLUMN_CUSTOMER_NAME));
        final int id = mCursor.getInt(mCursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
        String bis = mCursor.getString(mCursor.getColumnIndex(DataBaseHelper.COLUMN_CUSTOMER_BIS));
        String von = mCursor.getString(mCursor.getColumnIndex(DataBaseHelper.COLUMN_CUSTOMER_VON));
        String handy = mCursor.getString(mCursor.getColumnIndex(DataBaseHelper.COLUMN_CUSTOMER_HANDY));


        holder.textname.setText(name);
        holder.textvon.setText(von);
        holder.textbis.setText(bis);
        holder.texthandy.setText(handy);
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor  !=null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor !=null){
            notifyDataSetChanged();
        }
    }
}