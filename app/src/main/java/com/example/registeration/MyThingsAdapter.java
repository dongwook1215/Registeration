package com.example.registeration;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class MyThingsAdapter extends BaseAdapter {
    private Context context;
    private List<MyThings> mythingsList;
    private Activity parentActiviy;
    private List<MyThings> saveList;

    public MyThingsAdapter(Context context, List<MyThings> mythingsList, Activity parentActivity, List<MyThings> saveList) {
        this.context = context;
        this.mythingsList = mythingsList;
        this.parentActiviy=parentActivity;
        this.saveList=saveList;
    }

    @Override
    public int getCount() {
        return mythingsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mythingsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= View.inflate(context, R.layout.mythings, null);
        TextView thingsid= (TextView)v.findViewById(R.id.thingsid);
        final TextView thingsname= (TextView)v.findViewById(R.id.thingsname);
        TextView thingswhere= (TextView)v.findViewById(R.id.thingswhere);
        TextView thingswhen= (TextView)v.findViewById(R.id.thingswhen);

        thingsid.setText(""+mythingsList.get(position).getThingsID());
        thingsname.setText(mythingsList.get(position).getThingsName());
        thingswhere.setText(mythingsList.get(position).getThingsWhere());
        thingswhen.setText(mythingsList.get(position).getThingsWhen());

        v.setTag(mythingsList.get(position).getThingsName());

        return v;
    }
}
