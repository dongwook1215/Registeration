package com.example.registeration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class QnARequest extends StringRequest {
    final static private String URL = "http://kdo6338.cafe24.com/UserQnA.php";

    private Map<String,String> parameters;
    public QnARequest(String questionContent,String questionEmail,String questionDate, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener,null);
        parameters=new HashMap<>();
        parameters.put("questionContent", questionContent);
        parameters.put("questionEmail", questionEmail);
        parameters.put("questionDate", questionDate);
    }

    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
