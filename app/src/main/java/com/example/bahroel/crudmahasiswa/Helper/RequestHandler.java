package com.example.bahroel.crudmahasiswa.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestHandler {
    public String sendPostRequest(String requestURL, HashMap<String,String> postDataParams){
        URL url;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            url = new URL(requestURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(getPostDataString(postDataParams));

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int responCode = httpURLConnection.getResponseCode();
            if(responCode == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                stringBuilder = new StringBuilder();
                String response;
                while((response = bufferedReader.readLine()) != null){
                    stringBuilder.append(response);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public String sendGetRequest(String requestURL){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String s;
            while((s = bufferedReader.readLine()) != null){
                stringBuilder.append(s + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();

    }

    private String getPostDataString(HashMap<String,String> postDataParams) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = false;
        for(Map.Entry<String,String> entry : postDataParams.entrySet()){
            if(first){
                first = false;
            }else{
                result.append("&");
                result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            }
        }
        return result.toString();
    }

}
