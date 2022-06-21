package com.example.happydoniatestproject.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class CustomJSONParser {
    public static List<Map<String, String>> ParseModel(Response<ResponseBody> response) throws IOException, JSONException {
        assert response.body() != null;
        List<Map<String, String>> parsedData = new ArrayList<>();
        JSONObject result = new JSONObject(response.body().string());
        JSONObject query = new JSONObject(result.get("query").toString());
        JSONObject pages = new JSONObject(query.get("pages").toString());
        Iterator<String> keys = pages.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            if(pages.has(key)){
                JSONObject element = (JSONObject) pages.get(key);
                //Getting title
                String title = element.get("title").toString();
                //Getting image
                String image;
                try {
                    JSONObject thumbnail = (JSONObject) element.get("thumbnail");
                    image = thumbnail.getString("source");
                } catch (JSONException e) {
                    image = null;
                }
                //Getting distance
                JSONArray coordinatesArray = (JSONArray) element.get("coordinates");
                JSONObject coordinates = (JSONObject) coordinatesArray.get(0);
                String distance = coordinates.getString("dist");

                Map<String, String> hashData = new HashMap<>();
                hashData.put("title", title);
                hashData.put("image", image);
                hashData.put("distance", distance + " meters");
                parsedData.add(hashData);
            }
        }
        return parsedData;
    }
}
