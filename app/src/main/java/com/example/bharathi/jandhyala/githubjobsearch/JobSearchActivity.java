package com.example.bharathi.jandhyala.githubjobsearch;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class JobSearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private static final int LOADER_ID=3054;
    private String jobsearchurl;
    private ArrayList<JobModel> jobModels;
    private ConnectivityManager connectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_job_search );
        progressBar=findViewById( R.id.progress_circular );
        recyclerView=findViewById( R.id.recycler );
        jobModels=new ArrayList<>();
        String query=getIntent().getStringExtra(getString(  R.string.search) );
        jobsearchurl="https://jobs.github.com/positions.json?description="+query+"&location=new+york";
        connectivityManager= (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );

        if(connectionCheck())
            getLoaderManager().initLoader(LOADER_ID ,null, JobSearchActivity.this );
        else
        {
            isOnline();
        }
    }

    private boolean connectionCheck() {
        return connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_MOBILE ).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_WIFI ).getState() == NetworkInfo.State.CONNECTED;
    }
    private void isOnline()
    {
        AlertDialog.Builder dialog=new AlertDialog.Builder( this );
        dialog.setTitle( "Connectivty" );
        dialog.setMessage( getString( R.string.network) );
        dialog.setCancelable( false );

        dialog.setPositiveButton( getString( R.string.ok ), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        } );
        dialog.show();

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            public String loadInBackground() {
                try {
                    URL url=new URL(jobsearchurl );
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream stream=httpURLConnection.getInputStream();
                    Scanner sn=new Scanner( stream );
                    sn.useDelimiter( "\\A" );
                    if(sn.hasNext()){
                        return  sn.next();
                    }
                    else
                        return null;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                jobModels.clear();
                if (connectionCheck()) {
                    progressBar.setVisibility( VISIBLE );
                    forceLoad();
                } else {
                    isOnline();
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data != null) {
            try {
                JSONArray array = new JSONArray( data );
              if(array.length()!=0) {
                  for (int i = 0; i < array.length(); i++) {
                      JSONObject object = array.getJSONObject( i );
                      String titleOfJob = object.getString( "title" );
                      String locationOfJob = object.getString( "location" );
                      String howToApply = object.getString( "how_to_apply" );
                      jobModels.add( new JobModel( titleOfJob, locationOfJob, howToApply ) );
                  }
                  progressBar.setVisibility( GONE );
                  recyclerView.setAdapter( new JobAdapter( JobSearchActivity.this, jobModels ) );
                  recyclerView.setLayoutManager( new LinearLayoutManager( JobSearchActivity.this ) );
              }
              else
              {
                  dataNotFound();
              }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
           dataNotFound();

                    }

    }

    private void dataNotFound()
    {
        progressBar.setVisibility( GONE );
        AlertDialog.Builder dialog=new AlertDialog.Builder( this );
        dialog.setTitle( getString( R.string.u) );
        dialog.setMessage( getString( R.string.c));
        dialog.setCancelable( false );
        dialog.setPositiveButton( getString( R.string.ok ), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        } );
        dialog.show();
    }


    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
