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

public class ManagerNoticeListAdapter extends BaseAdapter {
    private Context context;
    private List<Notice> noticeList;
    private Activity parentActiviy;
    private List<Notice> saveList;

    public ManagerNoticeListAdapter(Context context, List<Notice> userList, Activity parentActivity, List<Notice> saveList) {
        this.context = context;
        this.noticeList = userList;
        this.parentActiviy=parentActivity;
        this.saveList=saveList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= View.inflate(context, R.layout.managernotice, null);
        final TextView nameText= (TextView)v.findViewById(R.id.nameText);
        TextView noticeText= (TextView)v.findViewById(R.id.noticeText);
        TextView dateText= (TextView)v.findViewById(R.id.dateText);

        nameText.setText(noticeList.get(position).getName());
        noticeText.setText(noticeList.get(position).getNotice());
        dateText.setText(noticeList.get(position).getDate());

        v.setTag(noticeList.get(position).getNotice());

        Button noticedeleteButton=(Button)v.findViewById(R.id.noticedeleteButton);
        noticedeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse =new JSONObject(response);
                            boolean success =jsonResponse.getBoolean("success");
                            if(success){
                                noticeList.remove(position);
                                for(int i=0;i<saveList.size();i++)
                                {
                                    if(saveList.get(i).getName().equals(nameText.getText().toString()))
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
                NoticeDeleteRequest deleteRequest =new NoticeDeleteRequest(nameText.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActiviy);
                queue.add(deleteRequest);
            }
        });

        return v;
    }
}
