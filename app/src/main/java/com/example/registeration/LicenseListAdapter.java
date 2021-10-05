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

public class LicenseListAdapter extends BaseAdapter {

    private Context context;
    private List<LicenseList> licenseList;
    private Activity parentActiviy;
    private List<LicenseList> saveList;

    public LicenseListAdapter(Context context, List<LicenseList> licenseList, Activity parentActivity, List<LicenseList> saveList) {
        this.context = context;
        this.licenseList = licenseList;
        this.parentActiviy=parentActivity;
        this.saveList=saveList;
    }

    @Override
    public int getCount() {
        return licenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return licenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= View.inflate(context, R.layout.license, null);
        final TextView idText= (TextView)v.findViewById(R.id.idText);

        idText.setText(licenseList.get(position).getUserID());

        v.setTag(licenseList.get(position).getUserID());

        Button userdeleteButton=(Button)v.findViewById(R.id.deleteButton);
        userdeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse =new JSONObject(response);
                            boolean success =jsonResponse.getBoolean("success");
                            if(success){
                                licenseList.remove(position);
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
                LicenseDeleteRequest deleteRequest =new LicenseDeleteRequest(idText.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActiviy);
                queue.add(deleteRequest);
            }
        });

        return v;
    }
}
