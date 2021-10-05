package com.example.registeration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReturnThingsDeleteRequest extends StringRequest {
    final static private String URL = "http://kdo6338.cafe24.com/ThingsDelete.php";

    private Map<String,String> parameters;
    public ReturnThingsDeleteRequest(String thingsID, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener,null);
        parameters=new HashMap<>();
        parameters.put("thingsID", thingsID);
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
