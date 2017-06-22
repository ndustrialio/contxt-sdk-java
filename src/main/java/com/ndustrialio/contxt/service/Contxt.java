package com.ndustrialio.contxt.service;

import com.ndustrialio.contxt.http.ApiRequest;
import com.ndustrialio.contxt.http.ApiResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by jmhunt on 6/22/17.
 */
public class Contxt extends Service
{
    public Contxt(String clientID, String clientSecret)
    {
        super(clientID, clientSecret);
    }

    String baseURL()
    {
        return "https://contxt.api.ndustrial.io";
    }

    String audience()
    {
        return "8qY2xJob1JAxhmVhIDLCNnGriTM9bct8";
    }


    public Map<String, Object> getServiceConfiguration(String environment) throws IOException
    {
        ApiRequest request = new ApiRequest(String.format("clients/%s/configurations?environment_id=%s", _clientID, environment));

        JSONObject response = new JSONObject(_get(request).Data);

        JSONArray configValues = response.getJSONArray("ConfigurationValues");

        return StreamSupport
                .stream(configValues.spliterator(), false)
                .map((obj) -> ((JSONObject)obj))
                .collect(Collectors.toMap(
                        (obj)->obj.getString("key"),
                        (obj)->obj.get("value")));

    }
}
