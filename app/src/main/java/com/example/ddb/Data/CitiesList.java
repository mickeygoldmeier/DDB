package com.example.ddb.Data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class CitiesList {
    private static LinkedList<String> CitiesList;

    public static void UpdateCitiesList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                InitUpdateCitiesList();
            }
        });
        thread.start();
    }

    private static void InitUpdateCitiesList() {
        try {
            // set up the URL and the Streams
            URL url = new URL("https://data.gov.il/dataset/3fc54b81-25b3-4ac7-87db-248c3e1602de/resource/d4901968-dad3-4845-a9b0-a57d027f11ab/download/yeshuvim_20190401.txt");
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream input = new BufferedInputStream(httpURLConnection.getInputStream());

            // read the data from online
            BufferedReader br = new BufferedReader(new InputStreamReader(input, "WINDOWS-1255"));

            // add the data into the list
            String line = "";
            int i = 0;
            CitiesList = new LinkedList<>();
            while ((line = br.readLine()) != null) {
                if (i > 1) {
                    String[] city = line.split(",");
                    CitiesList.add(city[2].split("\\)")[0]);
                }
                i++;
            }

            // close the URL connection
            httpURLConnection.disconnect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String[] getCitiesArray(){
        String[] arr = new String[CitiesList.size()];
        for (int i = 0; i < CitiesList.size(); i++)
            arr[i] = CitiesList.get(i);
        return arr;
    }
}