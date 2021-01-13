package com.example.myquiz2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SetsAdapter extends BaseAdapter {

    Integer setsList;

    public SetsAdapter(Integer setsList ){
        this.setsList = setsList;
    }


    @Override
    public int getCount() {
        return setsList;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view;

        if (convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_layout,parent,false);
        }else {
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(),QuestionActivity.class);
                intent.putExtra("SetNo",position +1);
                parent.getContext().startActivity(intent);

            }
        });

        ((TextView) view.findViewById(R.id.txv_set_No)).setText(String.valueOf(position+1));


        return view;
    }
}
