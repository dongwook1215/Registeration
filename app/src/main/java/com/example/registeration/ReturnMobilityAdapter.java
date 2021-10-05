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

public class ReturnMobilityAdapter extends BaseAdapter {
    private Context context;
    private List<ReturnMobility> returnmobilityList;
    private Activity parentActiviy;
    private List<ReturnMobility> saveList;

    public ReturnMobilityAdapter(Context context, List<ReturnMobility> returnmobilityList, Activity parentActivity, List<ReturnMobility> saveList) {
        this.context = context;
        this.returnmobilityList = returnmobilityList;
        this.parentActiviy=parentActivity;
        this.saveList=saveList;
    }

    @Override
    public int getCount() {
        return returnmobilityList.size();
    }

    @Override
    public Object getItem(int position) {
        return returnmobilityList.get(position);
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

        idText.setText(returnmobilityList.get(position).getUserID());
        thingsid.setText(""+returnmobilityList.get(position).getMobilityID());
        thingsname.setText(returnmobilityList.get(position).getMobilityName());
        thingswhere.setText(returnmobilityList.get(position).getMobilityWhere());
        thingswhen.setText(returnmobilityList.get(position).getMobilityWhen());

        v.setTag(returnmobilityList.get(position).getUserID());

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
                                returnmobilityList.remove(position);
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
                ReturnMobilityDeleteRequest deleteRequest =new ReturnMobilityDeleteRequest(""+returnmobilityList.get(position).getMobilityID(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActiviy);
                queue.add(deleteRequest);
            }
        });

        return v;
    }
}
