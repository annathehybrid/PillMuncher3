package com.anna.pillaroo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public ArrayAdapter<String> PillAdapter;

    public MainActivityFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            MyDownloadTask downloadTask = new MyDownloadTask();
            downloadTask.execute("texas", "houston", "wheee", "crestor");
            return true;
        }

        if (id == R.id.action_search) {
            MyDownloadTask downloadTask = new MyDownloadTask();
            downloadTask.execute("texas", "houston", "wheee", "humira");
            return true;
        }

        if (id == R.id.action_settings) {

            return true;
        }

        if (id == R.id.action_another) {
            MyDownloadTask downloadTask = new MyDownloadTask();
            downloadTask.execute("texas", "houston", "wheee", "crest");
            return true;
        }



        return super.onOptionsItemSelected(item);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);


        //new MyDownloadTask().execute("input_here");
       //MyDownloadTask downloadTask = new MyDownloadTask();
        //downloadTask.execute("input_here");

        // Create some dummy data for the ListView.  Here's a sample pillbox
        String[] pill_data = new String[]{
                "Red Pill",
                "Orange Pill",
                "Yellow Pill"
        };

        List<String> weekPills = new ArrayList<String>(Arrays.asList(pill_data));


        PillAdapter = new ArrayAdapter<String>(
                getActivity(), //global information about the app enviroment, resources
                R.layout.list_item_pills, //layout for each element
                R.id.list_item_pills_textview, //id of the textview
                weekPills);


        ListView listView = (ListView) view.findViewById(R.id.listview_pills);

        PillAdapter.add("hi hi");
        listView.setAdapter(PillAdapter);


        return view;
        //return inflater.inflate(R.layout.fragment_main, container, false);
    }






    public class MyDownloadTask extends AsyncTask<String, Void, String> {

        public String send_to_the_adapter;

        //String parameter = "rxcui.json";
        String parameter = "spellingsuggestions";
        String json_paramter = "json";

        String units2 = "11523-7020-1";


        @Override
        public String doInBackground(String... args) {

            //URL url;
            //see_if_this_works = "hello I'm in the Async task";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;

            String args_print0 = args[0];
            String args_print1 = args[1];
            String args_print2 = args[2];
            String args_print3 = args[3];

            //String units = "humira";

            Log.e("This is the input: ", args_print0);
            Log.e("This is the input: ", args_print1);
            Log.e("This is the input: ", args_print2);
            Log.e("This is the input: ", args_print3);


            try {
                // Construct the URL
                final String RXNORM_BASE_URL = "http://rxnav.nlm.nih.gov/REST/";
                //URL url = new URL("http://rxnav.nlm.nih.gov/REST/rxcui.json?idtype=NDC&id=11523-7020-1");
                //https://rxnav.nlm.nih.gov/REST/drugs?name=cymbalta

                //final String QUERY_PARAM = "mouse";
                //final String UNITS_PARAM = "idtype";
                final String UNITS_PARAM = "name";
                final String UNITS_PARAM2 = "id";

                Uri builtUri = Uri.parse(RXNORM_BASE_URL).buildUpon()
                        .appendPath(parameter)
                        .appendPath(json_paramter)
                        .appendQueryParameter(UNITS_PARAM, args_print3) //adds a ?PARAM=units and fragment adds a #
                        //.appendQueryParameter(UNITS_PARAM2, units2)

                        .build();

                URL url = new URL(builtUri.toString());

                String log_this = url.toString();
                Log.e("log this: ", log_this);


                //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }


                forecastJsonStr = buffer.toString();
                send_to_the_adapter = forecastJsonStr;

                //String returnthis = url.toString();
                Log.e("background: ", send_to_the_adapter);

                urlConnection.disconnect();


            } catch (MalformedURLException ex) {
                Log.e("httptest", Log.getStackTraceString(ex));
            } catch (IOException ex2) {
                Log.e("httptest", "Error ", ex2);
            }

            //put the Json text into a return string that was initialized earlier
            send_to_the_adapter = forecastJsonStr;


            return forecastJsonStr;
        }


        public void onPostExecute(String result) {
            //result = returnstring;

            if (result != null) {
                Log.e("this is the result", send_to_the_adapter);









                PillAdapter.add(send_to_the_adapter);
            }

            //send_to_the_adapter = result;

            //Log.e("from post execute", send_to_the_adapter);

        }



    }



}
