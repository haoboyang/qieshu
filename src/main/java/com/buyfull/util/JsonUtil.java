package com.buyfull.util;


import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.buyfull.openapiv1.BFException;

import okhttp3.Call;
import okhttp3.Response;

import java.io.IOException;

public class JsonUtil {









	public static JSONObject getDataObj(String url, Call call) throws BFException{
		try {
			Response resp = call.execute();
			if (!resp.isSuccessful()){
				throw new BFException(BFException.ERRORS.HTTP_ERROR, "Network error in: " + url + "\n Message: "+ resp.message());
			}
			String responseStr = resp.body().string();
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(responseStr);
				int code = jsonObject.getInt("code");
				String message = jsonObject.getString("message");
				if (code != 200){
					throw new BFException(code, message);
				}
				return jsonObject.getJSONObject("data");
			} catch (JSONException e) {
				throw new BFException(BFException.ERRORS.INVALID_JSON, "server return bad json");
			}
		} catch (IOException e) {
			throw new BFException(e, BFException.ERRORS.NETWORK_ERROR, "Network error in: " + url);
		}
	}
    public static <T> Object JSONToObj(String jsonStr, Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            t = objectMapper.readValue(jsonStr, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将实体POJO转化为JSON
     *
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static <T> JSONObject objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert object to JSON string
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return new JSONObject(jsonStr);
    }

}
