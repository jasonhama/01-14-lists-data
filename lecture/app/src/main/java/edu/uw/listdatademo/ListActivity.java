package edu.uw.listdatademo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.*;
import java.io.*;
import java.util.*;
import android.util.Log;


public class ListActivity extends AppCompatActivity {

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        String[] data = new String[100];
        for (int i = 99; i > 0; i--) {
            data[99-i] = i + " bottles of beer on the wall";
        }

        //String[] data = downloadMovieData("Die Hard");
        MovieDownloadTask task = new MovieDownloadTask();
        task.execute("Die Hard");


        //controller
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.list_item, R.id.txtItem, data);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public class MovieDownloadTask extends AsyncTask<String, Void, String[]>{
        protected String[] doInBackground(String... params){

            //construct the url for the omdbapi API
            String urlString = "";//"http://www.omdbapi.com/?s=" + movie + "&type=movie";

            //set up connection variables
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            //set up return variable
            String movies[] = null;

            try {

                //set a URL for later
                URL url = new URL(urlString);

                //create a connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //reads the input from the urlConnection in bits (0's and 1's)
                InputStream inputStream = urlConnection.getInputStream();

                //creates a new StringBufer object
                StringBuffer buffer = new StringBuffer();

                //if no data return null
                if (inputStream == null) {
                    return null;
                }

                //puts data into chunks (characters, arrays, and lines)
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                //while a next line is available, assign line to the reader's line
                while ((line = reader.readLine()) != null) {

                    //adds the buffer of a new line
                    buffer.append(line + "\n");
                }

                //case where buffer contains nothing
                if (buffer.length() == 0) {
                    return null;
                }

                //set results
                String results = buffer.toString();


                //this gets rid of the JSON fluff
                results = results.replace("{\"Search\":[", "");
                results = results.replace("]}","");

                Log.v("ListActivity", results);

                //seperate each result
                results = results.replace("},", "},\n");

                movies = results.split("\n");
            }
            catch (IOException e) {
                return null;
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    }
                    catch (final IOException e) {
                    }
                }
            }

            return movies;
        }

        protected void onPostExecute(String[] movies){
            adapter.clear();
            adapter.addAll(Arrays.toList(movies));
        }
    }
}

