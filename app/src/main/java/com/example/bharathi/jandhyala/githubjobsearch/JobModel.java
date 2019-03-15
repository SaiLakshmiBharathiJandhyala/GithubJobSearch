package com.example.bharathi.jandhyala.githubjobsearch;

class JobModel {
    private String job;
    private String loc;
    private String apply;

    public JobModel(String titleOfJob, String locationOfJob, String howToApply) {
        this.job=titleOfJob;
        this.loc=locationOfJob;
        this.apply=howToApply;
    }


    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }
}
