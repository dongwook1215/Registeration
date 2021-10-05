package com.example.registeration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserLicenseDeleteRequest extends StringRequest {

    final static private String URL = "http://kdo6338.cafe24.com/UserLicenseDelete.php";

    private Map<String,String> parameters;
    public UserLicenseDeleteRequest(String userID, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener,null);
        parameters=new HashMap<>();
        parameters.put("userID", userID);
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
