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

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<UserList> userList;
    private Activity parentActiviy;
    private List<UserList> saveList;

    public UserListAdapter(Context context, List<UserList> userList, Activity parentActivity, List<UserList> saveList) {
        this.context = context;
        this.userList = userList;
        this.parentActiviy=parentActivity;
        this.saveList=saveList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= View.inflate(context, R.layout.user, null);
        final TextView idText= (TextView)v.findViewById(R.id.idText);
        TextView passwordText= (TextView)v.findViewById(R.id.passwordText);
        TextView genderText= (TextView)v.findViewById(R.id.genderText);
        TextView majorText= (TextView)v.findViewById(R.id.majorText);
        TextView emailText= (TextView)v.findViewById(R.id.emailText);

        idText.setText(userList.get(position).getUserID());
        passwordText.setText(userList.get(position).getUserPassword());
        genderText.setText(userList.get(position).getUserGender());
        majorText.setText(userList.get(position).getUserMajor());
        emailText.setText(userList.get(position).getUserEmail());

        v.setTag(userList.get(position).getUserID());

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
                                userList.remove(position);
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
                UserDeleteRequest deleteRequest =new UserDeleteRequest(idText.getText().toString(), responseListener);
                UserMobilityDeleteRequest mobilitydeleteRequest =new UserMobilityDeleteRequest(idText.getText().toString(), responseListener);
                UserReviewDeleteRequest reviewdeleteRequest =new UserReviewDeleteRequest(idText.getText().toString(), responseListener);
                UserThingsDeleteRequest thingsdeleteRequest =new UserThingsDeleteRequest(idText.getText().toString(), responseListener);
                UserLicenseDeleteRequest licensedeleteRequest =new UserLicenseDeleteRequest(idText.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActiviy);
                RequestQueue queue1 = Volley.newRequestQueue(parentActiviy);
                RequestQueue queue2 = Volley.newRequestQueue(parentActiviy);
                RequestQueue queue3 = Volley.newRequestQueue(parentActiviy);
                RequestQueue queue4 = Volley.newRequestQueue(parentActiviy);
                queue.add(deleteRequest);
                queue1.add(mobilitydeleteRequest);
                queue2.add(reviewdeleteRequest);
                queue3.add(thingsdeleteRequest);
                queue4.add(licensedeleteRequest);
            }
        });

        return v;
    }
}
