package edu.lewisu.cs.zakaryakrumlinde.fantasyfootballprojections;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = "x6ckfw96injq";
    private Spinner positionSpinner;
    private Spinner weekSpinner;
    private ProgressBar progressBar;
    private ProjectionAdapter projectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        positionSpinner = findViewById(R.id.position_spinner);
        weekSpinner = findViewById(R.id.week_spinner);
        progressBar = findViewById(R.id.progressBar);
        projectionAdapter = new ProjectionAdapter();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(projectionAdapter);
    }

    public void getProjectionsClick(View v) {
        String position = positionSpinner.getSelectedItem().toString();
        String week = weekSpinner.getSelectedItem().toString();
        getProjections(position, week);
    }

    private void getProjections(String position, String week) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("fantasyfootballnerd.com")
                .appendPath("service")
                .appendPath("weekly-rankings")
                .appendPath("json")
                .appendPath(API_KEY)
                .appendPath(position)
                .appendPath(week);

        Uri projectionUri = builder.build();
        Log.d("uri", projectionUri.toString());

        DownloadProjections downloadProjections = new DownloadProjections(this);
        downloadProjections.execute(projectionUri.toString());
    }

    private static class DownloadProjections extends
            AsyncTask<String, Void, ArrayList<Projection>> {

        private WeakReference<MainActivity> activityReference;

        DownloadProjections(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = activityReference.get();
            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Projection> doInBackground(String... strings) {
            String jsonData = "";
            ArrayList<Projection> projectionArrayList = new ArrayList<>();

            String name;
            String standard;
            String ppr;
            Projection projection;
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    jsonData = scanner.next();
                }

                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray projections = jsonObject.getJSONArray("Rankings");

                for (int i = 0; i < projections.length(); i++) {
                    JSONObject playerObject = projections.getJSONObject(i);
                    name = playerObject.getString("name");
                    standard = playerObject.getString("standard");
                    ppr = playerObject.getString("ppr");

                    projection = new Projection(name, standard, ppr);
                    projectionArrayList.add(projection);
                }

                return projectionArrayList;
            } catch (Exception ex) {
                Log.d("error", ex.toString());
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Projection> projections) {
            MainActivity activity = activityReference.get();
            if(activity == null || activity.isFinishing())
                return;
            activity.progressBar.setVisibility(View.INVISIBLE);
            activity.projectionAdapter.setProjectionData(projections);


        }
    }
}
