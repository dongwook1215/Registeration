package com.example.registeration;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyMobilityAdapter extends BaseAdapter {
    private Context context;
    private List<MyMobility> mymobilityList;
    private Activity parentActiviy;
    private List<MyMobility> saveList;

    public MyMobilityAdapter(Context context, List<MyMobility> mymobilityList, Activity parentActivity, List<MyMobility> saveList) {
        this.context = context;
        this.mymobilityList = mymobilityList;
        this.parentActiviy=parentActivity;
        this.saveList=saveList;
    }

    @Override
    public int getCount() {
        return mymobilityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mymobilityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= View.inflate(context, R.layout.mymobility, null);
        TextView mobilityid= (TextView)v.findViewById(R.id.mobilityid);
        final TextView mobilityname= (TextView)v.findViewById(R.id.mobilityname);
        TextView mobilitywhere= (TextView)v.findViewById(R.id.mobilitywhere);
        TextView mobilitywhen= (TextView)v.findViewById(R.id.mobilitywhen);

        mobilityid.setText(""+mymobilityList.get(position).getMobilityID());
        mobilityname.setText(mymobilityList.get(position).getMobilityName());
        mobilitywhere.setText(mymobilityList.get(position).getMobilityWhere());
        mobilitywhen.setText(mymobilityList.get(position).getMobilityWhen());

        v.setTag(mymobilityList.get(position).getMobilityName());

        return v;
    }
}
