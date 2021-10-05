package com.example.registeration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RentThingsRequest extends StringRequest {
    final static private String URL = "http://kdo6338.cafe24.com/ThingsRegister.php";

    private Map<String,String> parameters;
    public RentThingsRequest(String userID, String thingsName, String thingsWhere, String thingsWhen,String thingsContent,String bankName,String bankNumber,String bankPassword, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener,null);
        parameters=new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("thingsName", thingsName);
        parameters.put("thingsWhere", thingsWhere);
        parameters.put("thingsWhen", thingsWhen);
        parameters.put("thingsContent", thingsContent);
        parameters.put("bankName", bankName);
        parameters.put("bankNumber", bankNumber);
        parameters.put("bankPassword", bankPassword);
    }

    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
