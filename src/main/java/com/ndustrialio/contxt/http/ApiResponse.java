package com.ndustrialio.contxt.http;

/**
 * Created by jmhunt on 5/18/16.
 */
public class ApiResponse
{
    public int StatusCode;
    public String Data, StatusMessage;

    public ApiResponse(int statusCode, String statusMessage, String data)
    {
        StatusCode = statusCode;
        StatusMessage = statusMessage;
        Data = data;
    }
}
