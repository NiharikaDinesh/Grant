package com.example.dbms.adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

import java.util.List;

public class BloodRecyclerAdapter extends RecyclerView.Adapter<BloodRecyclerAdapter.BloodViewHolder>{


    private  java.lang.String[] String=null;
    private List<LauncherActivity.ListItem>listItems;
    private Context context;



    public BloodRecyclerAdapter(FragmentActivity listItems, Context context){
        this.listItems= (List<LauncherActivity.ListItem>) listItems;
        this.context=context;
    }




    @NonNull
    @Override
    public BloodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view=  LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_blood,parent,false);
        return new BloodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodViewHolder holder, int position) {

        LauncherActivity.ListItem listItem=listItems.get(position);
        holder.textView.setText((CharSequence) listItem);


    }



    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class BloodViewHolder extends RecyclerView.ViewHolder{
       public TextView textView;

        public BloodViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =(TextView)itemView.findViewById(R.id.txtblood);


        }
    }

}
