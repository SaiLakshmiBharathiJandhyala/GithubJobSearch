package com.example.bharathi.jandhyala.githubjobsearch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> {

    private JobSearchActivity jobSearchActivity;
    private ArrayList<JobModel> jobModels;

    public JobAdapter(JobSearchActivity jobSearchActivity, ArrayList<JobModel> jobModels) {
        this.jobSearchActivity = jobSearchActivity;
        this.jobModels = jobModels;
    }

    @NonNull
    @Override
    public JobHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new JobHolder( LayoutInflater.from( jobSearchActivity).inflate( R.layout.job_design, viewGroup,false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull JobHolder jobHolder, int i) {
        jobHolder.jobTitlleView.setText( jobModels.get( i ).getJob() );
        jobHolder.locationView.setText( jobModels.get( i ).getLoc() );
        jobHolder.howToApplyView.setText( jobModels.get( i ).getApply() );

    }

    @Override
    public int getItemCount() {
        return jobModels.size()>0?jobModels.size():0;
    }

    public class JobHolder extends RecyclerView.ViewHolder {
        TextView jobTitlleView,locationView,howToApplyView;
        public JobHolder(@NonNull View itemView) {
            super( itemView );
            jobTitlleView=itemView.findViewById( R.id.jobTitle );
            locationView=itemView.findViewById( R.id.location);
            howToApplyView=itemView.findViewById( R.id.howToApply ) ;
        }
    }
}
