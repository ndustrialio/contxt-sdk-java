package com.ndustrialio.contxt.service;

import com.ndustrialio.contxt.http.ApiRequest;
import com.ndustrialio.contxt.http.ApiResponse;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by jmhunt on 6/22/17.
 */
public abstract class Service
{
    protected String _accessToken, _clientID, _clientSecret;


    public Service(String clientID, String clientSecret)
    {
        _clientID = clientID;
        _clientSecret = clientSecret;

        _accessToken = getAccessToken();
    }

    protected abstract String baseURL();

    protected abstract String audience();

    private String getAccessToken()
    {
        ApiRequest auth0Request = new ApiRequest("oauth/token")
                .version(false)
                .baseURL("https://ndustrialio.auth0.com/")
                .authorize(false)
                .body("client_id", _clientID)
                .body("client_secret", _clientSecret)
                .body("audience", audience())
                .method(ApiRequest.METHOD.POST)
                .body("grant_type", "client_credentials");

        String token;

        try
        {
           token = new JSONObject(performRequest(auth0Request).Data)
                .getString("access_token");
        } catch (IOException e)
        {
            // Re throwing as a runtime exception, since you can't continue
            // past this point if something goes wrong.
            throw new RuntimeException(e.getMessage());
        }

        return token;
    }

    protected final ApiResponse _get(ApiRequest req) throws IOException
    {
        req.method(ApiRequest.METHOD.GET);

        return performRequest(req);
    }

    protected final ApiResponse _put(ApiRequest req) throws IOException
    {
        req.method(ApiRequest.METHOD.PUT);

        return performRequest(req);
    }

    protected final ApiResponse _post(ApiRequest req) throws IOException
    {
        req.method(ApiRequest.METHOD.POST);

        return performRequest(req);
    }

    protected final ApiResponse _delete(ApiRequest req) throws IOException
    {
        req.method(ApiRequest.METHOD.DELETE);

        return performRequest(req);
    }

    protected final ApiResponse performRequest(ApiRequest request) throws IOException
    {
        // Set our base URL, if it is not already set
        request.baseURL(
                Optional.ofNullable(
                        request.baseURL())
                        .orElse(this.baseURL()));

        HttpURLConnection connection = request.toHttpURLConnection();

        // Authorize request?
        if (request.authorize())
        {
            connection.setRequestProperty("Authorization", "Bearer " + _accessToken);
        }

        try
        {

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                    Charset.forName("UTF-8")));

            return new ApiResponse(connection.getResponseCode(), connection.getResponseMessage(), readAll(rd));


        } catch (IOException e)
        {

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getErrorStream(),
                    Charset.forName("UTF-8")));

            JSONObject errorMessage = new JSONObject(readAll(rd));

            int errorCode = connection.getResponseCode();

            if ((errorCode >= 400) && (errorCode <= 500))
            {
                throw new RuntimeException(errorCode + " - Client Error: " + errorMessage.getString("message"));
            } else
            {
                throw new RuntimeException(errorCode + " - Server Error: " + errorMessage.getString("message"));
            }
        }

    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
