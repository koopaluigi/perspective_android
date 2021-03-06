package com.example.cooper.perspective_android;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Cooper on 10/20/2017.
 */

public class Request
{

    private static HttpURLConnection httpConn;

    //Sends a post request
    public static String post(String urlString, Map<String, String> properties, String postData) throws IOException
    {
        URL url = new URL(urlString);
        httpConn = (HttpURLConnection) url.openConnection();

        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        for (Map.Entry<String, String> property : properties.entrySet())
        {
            httpConn.setRequestProperty(property.getKey(), property.getValue());
        }
        sendData(postData);

        return response();
    }

    //Sends a get request
    public static String get(String urlString, Map<String, String> properties) throws IOException
    {
        URL url = new URL(urlString);
        httpConn = (HttpURLConnection) url.openConnection();

        httpConn.setRequestMethod("GET");
        for (Map.Entry<String, String> property : properties.entrySet())
        {
            httpConn.setRequestProperty(property.getKey(), property.getValue());
        }

        return response();
    }

    //Prepares data to be sent through an http connection
    private static void sendData(String postData) throws IOException {
        OutputStream connOutput = httpConn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connOutput, "UTF-8"));

        writer.write(postData);
        writer.flush();
        writer.close();

        connOutput.close();
    }

    //Returns response of an http connection
    private static String response() throws IOException {
        InputStreamReader inputReader = new InputStreamReader(httpConn.getInputStream());
        BufferedReader reader = new BufferedReader(inputReader);
        StringBuilder responseString = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            responseString.append(line);
        }
        reader.close();
        httpConn.disconnect();
        return responseString.toString();
    }

}
