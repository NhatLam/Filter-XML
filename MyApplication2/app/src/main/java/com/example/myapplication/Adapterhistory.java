package com.example.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class Adapterhistory extends RecyclerView.Adapter<Adapterhistory.ViewHolder> {

    List<String> history;
    List<String> content;

    Context context;

    public Adapterhistory(List<String> history, Context context,  List<String> content) {
        this.history = history;
        this.context = context;
       this.content=content;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_histo, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.cbhis.setText(history.get(i));
        viewHolder.tvcontent.setText(content.get(i));
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        TextView tvcontent;
        CheckBox cbhis;

        public ViewHolder(View itemView) {
            super(itemView);

            cbhis=itemView.findViewById(R.id.cbhistory);
            tvcontent=itemView.findViewById(R.id.tvcontent);
            cbhis.setOnCheckedChangeListener(this);
        }

       @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                tvcontent.setVisibility(View.VISIBLE);
            }
            else {

                tvcontent.setVisibility(View.GONE);

            }
        }
    }
}
