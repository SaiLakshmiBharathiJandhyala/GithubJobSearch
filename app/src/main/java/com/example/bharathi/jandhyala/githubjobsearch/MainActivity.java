package com.example.bharathi.jandhyala.githubjobsearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText technologyET;
    private Button jobSearchButton;
    private String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        technologyET=findViewById( R.id.edit_job_search );
        jobSearchButton=findViewById( R.id.job_search_button );

    }

    public void jobSearch(View view) {
        value=technologyET.getText().toString();
        if (value.equalsIgnoreCase( "" )) {
            AlertDialog.Builder dialog=new AlertDialog.Builder( this );
            dialog.setTitle( "Error" );
            dialog.setMessage( getString( R.string.can_t_go_to_next_activity_without_entering_technology ));
            dialog.setCancelable( false );
            dialog.setPositiveButton( getString( R.string.ok ), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            } );
            dialog.show();
        }
        else
        {
            Intent i=new Intent(this,JobSearchActivity.class  );
            i.putExtra( getString( R.string.search),value.trim() );
            startActivity(i);
        }
    }
}
