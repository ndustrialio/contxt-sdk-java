package com.ndustrialio.contxt.http;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmhunt on 5/18/16.
 */
public class ApiRequest
{

    private String _baseURL = null;
    private CONTENT_TYPE _contentType = CONTENT_TYPE.JSON;
    private METHOD _method;
    private String _uri = null;
    private Map<String, String> _body;

    public enum CONTENT_TYPE
    {
        URLENCODED,
        JSON
    }

    public enum METHOD
    {
        GET,
        POST,
        PUT,
        DELETE
    }

    private Map<String, String> _params;

    private boolean _version = true, _authorize = true;


    public ApiRequest(String uri)
    {
        _uri = uri;

        _params = new HashMap<>();
        _body = new HashMap<>();
    }

    public ApiRequest contentType(CONTENT_TYPE contentType)
    {
        _contentType = contentType;

        return this;
    }

    public CONTENT_TYPE contentType()
    {
        return _contentType;
    }

    public ApiRequest baseURL(String baseURL)
    {
        _baseURL = baseURL;

        return this;
    }

    public String baseURL()
    {
        return _baseURL;
    }

    public ApiRequest method(METHOD method)
    {
        _method = method;

        return this;
    }

    public METHOD method()
    {
        return _method;
    }

    public ApiRequest authorize(boolean authorize)
    {
        _authorize = authorize;
        return this;
    }

    public boolean authorize()
    {
        return _authorize;
    }

    public ApiRequest version(boolean version)
    {
        _version = version;

        return this;
    }

    public Map<String, String> params()
    {
        return _params;
    }

    public ApiRequest params(String key, String value)
    {
        _params.put(key, value);

        return this;
    }

    public ApiRequest body(String key, String value)
    {
        _body.put(key, value);

        return this;
    }


    public boolean version()
    {
        return _version;
    }

    public HttpURLConnection toHttpURLConnection() throws IOException
    {
        StringBuilder sb = new StringBuilder(_baseURL);

        // Append version string
        if (_version)
        {
            sb.append("/v1/");
        }

        // Append URI extension
        if (_uri != null)
        {
            sb.append(_uri);
        }

        // Append params, if any
        if (!_params.isEmpty())
        {
            sb.append("?");
            sb.append(urlEncode(_params));
        }

        HttpURLConnection ret = (HttpURLConnection) new URL(sb.toString()).openConnection();

        ret.setRequestMethod(_method.name());

        if (!_body.isEmpty())
        {

            String bodyString;

            // Properly encode body
            if (_contentType == CONTENT_TYPE.URLENCODED)
            {
                bodyString = urlEncode(_body);

                ret.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            } else
            {
                // CONTENT_TYPE.JSON

                ret.setRequestProperty("Content-Type", "application/json");

                bodyString = new JSONObject(_body).toString();
            }

            ret.setDoOutput(true);

            // Commit body to request
            OutputStream os = ret.getOutputStream();

            os.write(bodyString.getBytes("UTF-8"));

            os.close();
        }

        return ret;
    }


    private static String urlEncode(Map<String, String> args)
    {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : args.entrySet())
        {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append("&");
        }

        // Remove trailing "&"
        sb.setLength(sb.length()-1);

        return sb.toString();
    }


}
