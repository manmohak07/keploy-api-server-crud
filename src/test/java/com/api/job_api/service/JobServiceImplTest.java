package com.api.job_api.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.api.job_api.impl.JobServiceImpl;
import com.api.job_api.model.Job;
import com.api.job_api.repo.JobRepository;

import java.util.Optional;


public class JobServiceImplTest {

    private final JobRepository jobRepository = mock(JobRepository.class);
    private final JobServiceImpl jobService = new JobServiceImpl(jobRepository);

    @Test
    void testAddJob() {
        Job job = new Job(null, "Developer", "India", "Spring Boot dev", 50000.0, 100000.0);
        when(jobRepository.save(any(Job.class))).thenReturn(job);

        Job result = jobService.addJob(job);
        assertEquals("Developer", result.getRole());
        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void testUpdateJobWhenExists() {
        Job job = new Job(1L, "Updated", "India", "Updated desc", 60000.0, 120000.0);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any(Job.class))).thenReturn(job);

        Job result = jobService.updateJob(1L, job);
        assertEquals("Updated", result.getRole());
    }

    @Test
    void testUpdateJobWhenNotExists() {
        Job job = new Job(2L, "Ghost", "India", "Should not update", 1000.0, 2000.0);
        when(jobRepository.findById(2L)).thenReturn(Optional.empty());

        Job result = jobService.updateJob(2L, job);
        assertNull(result);
    }
}
