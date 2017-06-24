package com.yuankunluo.bonmovie.services.jobs;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by yuank on 2017-06-24.
 */

public class FetchDataFromApiJobService extends JobService {


    public FetchDataFromApiJobService()


    @Override
    public boolean onStartJob(JobParameters job) {
        return false;
    }


    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
