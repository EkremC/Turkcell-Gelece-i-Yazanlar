package com.example.ekremc.gelecegiyazanlar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class DownloadTask extends AsyncTask<String, Void, String> {

    public ArrayList<String> id = new ArrayList<String>();
    public ArrayList<String> title = new ArrayList<String>();
    public ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    public ArrayList<String> url = new ArrayList<>();
    public AsyncResponse delegate = null;
    String result;
    Bitmap image = null;

    @Override
    public String doInBackground(String... urls) {
        result = "";
        URL url;
        HttpURLConnection httpURLConnection = null;

        try {
            url = new URL(urls[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = httpURLConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            String s = "";
            BufferedReader br = new BufferedReader(reader);
            while ((s = br.readLine()) != null) {
                result += s;
            }


            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);

        ImageDownload imageDownload = new ImageDownload();

        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonPart = jsonArray.getJSONObject(i);
                String tempId = "";
                String tempTitle = "";
                String tempImage = "";

                tempId = jsonPart.getString("id");
                tempTitle = jsonPart.getString("title");
                tempImage = jsonPart.getString("thumbnail");
                id.add(tempId);
                title.add(tempTitle);
                url.add(tempImage);
                image = new ImageDownload().execute(tempImage).get();
                images.add(image);
            }
            delegate.processFinish();
        } catch (JSONException e) {
            Log.e("hata yakalandı", "hataaaa");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public interface AsyncResponse {
        void processFinish();
    }

    private class ImageDownload extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

