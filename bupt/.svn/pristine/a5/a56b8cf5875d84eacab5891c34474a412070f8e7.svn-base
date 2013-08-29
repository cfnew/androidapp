package com.example.bupt.http;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonUtil {
	public static JSONObject parseResponse(String responseBody) throws JSONException {
        JSONObject result = null;
        //trim the string to prevent start with blank, 
        //and test if the string is valid JSON, 
        //because the parser don't do this :(. If Json is not valid this will return null
		responseBody = responseBody.trim();
		if(responseBody.startsWith("{") || responseBody.startsWith("[")) {
			result = (JSONObject) new JSONTokener(responseBody).nextValue();
		}
		return result;
    }
}
