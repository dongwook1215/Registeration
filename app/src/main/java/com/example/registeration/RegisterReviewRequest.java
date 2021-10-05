package com.example.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterReviewRequest extends StringRequest {
    final static private String URL = "http://kdo6338.cafe24.com/ReviewRegister.php";

    private Map<String,String> parameters;
    public RegisterReviewRequest(String userID,String mobilityName,String mobilityScore,String reviewContent, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener,null);
        parameters=new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("mobilityName", mobilityName);
        parameters.put("mobilityScore", mobilityScore);
        parameters.put("reviewContent", reviewContent);

    }

    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
