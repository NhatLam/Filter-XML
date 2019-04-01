package com.example.myapplication;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.io.File;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
   private static SparseBooleanArray itemStateArray= new SparseBooleanArray();

    ArrayList<File> data;
    Context context;
    public Adapter(ArrayList<File> data, Context context) {
        this.data = data;
        this.context = context;
    }


    public ViewHolder onCreateViewHolder( ViewGroup viewgroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewgroup.getContext());
        View itemView=layoutInflater.inflate(R.layout.item,viewgroup,false);
        return new ViewHolder(itemView);
    }
    //lay danh sach cac checkbox da chon
    public ArrayList<File> getList(){

        ArrayList<File> res = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            boolean check = itemStateArray.get(i, false);
            if(check){
                res.add(data.get(i));
            }

        }
        return res;
    }
    public void clear() {
        for (int i = 0; i < data.size(); i++) {
            itemStateArray.put(i, false);

        }
    }
    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        viewHolder.cb.setText(data.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


   /* public long getItemId(int position) {
        return super.getItemId(position);
    }
*/
    public static class ViewHolder extends RecyclerView.ViewHolder  implements  CompoundButton.OnCheckedChangeListener{
        CheckBox cb;

        public ViewHolder( View itemView) {
            super(itemView);

            cb= itemView.findViewById(R.id.cb);
            cb.setOnCheckedChangeListener(this);


        }


       @Override
       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


               if(isChecked==true)
               {
                   itemStateArray.put(getAdapterPosition(),true);
               }
               else
               {
                   itemStateArray.put(getAdapterPosition(),false);
               }
       }
   }

}

