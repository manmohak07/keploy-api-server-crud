package com.api.job_api.impl;

import com.api.job_api.model.Job;
import com.api.job_api.repo.JobRepository;
import com.api.job_api.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job addJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Job updateJob(Long id, Job job) {
        Optional<Job> existing = jobRepository.findById(id);
        if (existing.isPresent()) {
            Job j = existing.get();
            j.setRole(job.getRole());
            j.setCountry(job.getCountry());
            j.setDescription(job.getDescription());
            j.setMinSalary(job.getMinSalary());
            j.setMaxSalary(job.getMaxSalary());
            return jobRepository.save(j);
        }
        return null;
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
