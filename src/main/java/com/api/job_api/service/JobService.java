package com.api.job_api.service;

import com.api.job_api.model.Job;
import java.util.List;

public interface JobService {
    Job addJob(Job job);
    Job updateJob(Long id, Job job);
    void deleteJob(Long id);
    List<Job> getAllJobs();
}
