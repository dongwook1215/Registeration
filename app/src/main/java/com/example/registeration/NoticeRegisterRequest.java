package com.example.registeration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.*;

public class NoticeRegisterRequest extends StringRequest {
    final static private String URL = "http://kdo6338.cafe24.com/ManagerNotice.php";

    private Map<String,String> parameters;
    public NoticeRegisterRequest(String noticeContent,String noticeName,String noticeDate, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener,null);
        parameters=new HashMap<>();
        parameters.put("noticeContent", noticeContent);
        parameters.put("noticeName", noticeName);
        parameters.put("noticeDate", noticeDate);
    }

    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
