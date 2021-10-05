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

public class ReturnThingsAdapter extends BaseAdapter {
    private Context context;
    private List<ReturnThings> returnthingsList;
    private Activity parentActiviy;
    private List<ReturnThings> saveList;

    public ReturnThingsAdapter(Context context, List<ReturnThings> returnthingsList, Activity parentActivity, List<ReturnThings> saveList) {
        this.context = context;
        this.returnthingsList = returnthingsList;
        this.parentActiviy=parentActivity;
        this.saveList=saveList;
    }

    @Override
    public int getCount() {
        return returnthingsList.size();
    }

    @Override
    public Object getItem(int position) {
        return returnthingsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= View.inflate(context, R.layout.things, null);
        final TextView idText= (TextView)v.findViewById(R.id.idText);
        TextView thingsid= (TextView)v.findViewById(R.id.thingsid);
        TextView thingsname= (TextView)v.findViewById(R.id.thingsname);
        TextView thingswhere= (TextView)v.findViewById(R.id.thingswhere);
        TextView thingswhen= (TextView)v.findViewById(R.id.thingswhen);

        idText.setText(returnthingsList.get(position).getUserID());
        thingsid.setText(""+returnthingsList.get(position).getThingsID());
        thingsname.setText(returnthingsList.get(position).getThingsName());
        thingswhere.setText(returnthingsList.get(position).getThingsWhere());
        thingswhen.setText(returnthingsList.get(position).getThingsWhen());

        v.setTag(returnthingsList.get(position).getUserID());

        Button returnthingsdeleteButton=(Button)v.findViewById(R.id.deleteButton);
        returnthingsdeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse =new JSONObject(response);
                            boolean success =jsonResponse.getBoolean("success");
                            if(success){
                                returnthingsList.remove(position);
                                for(int i=0;i<saveList.size();i++)
                                {
                                    if(saveList.get(i).getUserID().equals(idText.getText().toString()))
                                    {
                                        saveList.remove(i);
                                        break;
                                    }
                                }
                                notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                ReturnThingsDeleteRequest deleteRequest =new ReturnThingsDeleteRequest(""+returnthingsList.get(position).getThingsID(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActiviy);
                queue.add(deleteRequest);
            }
        });

        return v;
    }
}
