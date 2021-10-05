package com.example.registeration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PayValidateRequest extends StringRequest {
    final static private String URL = "http://kdo6338.cafe24.com/PayValidate.php";

    private Map<String,String> parameters;
    public PayValidateRequest(String bankName,String bankNumber,String bankPassword, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener,null);
        parameters=new HashMap<>();
        parameters.put("bankName", bankName);
        parameters.put("bankNumber", bankNumber);
        parameters.put("bankPassword", bankPassword);
    }

    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
