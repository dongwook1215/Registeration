package com.example.registeration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RentMobilityRequest extends StringRequest {
    final static private String URL = "http://kdo6338.cafe24.com/MobilityRegister.php";

    private Map<String,String> parameters;
    public RentMobilityRequest(String userID, String mobilityName, String mobilityWhere, String mobilityWhen,String mobilityContent,String bankName,String bankNumber,String bankPassword, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener,null);
        parameters=new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("mobilityName", mobilityName);
        parameters.put("mobilityWhere", mobilityWhere);
        parameters.put("mobilityWhen", mobilityWhen);
        parameters.put("mobilityContent", mobilityContent);
        parameters.put("bankName", bankName);
        parameters.put("bankNumber", bankNumber);
        parameters.put("bankPassword", bankPassword);
    }

    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
