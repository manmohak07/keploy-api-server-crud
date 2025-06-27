package com.api.job_api.controller;

import com.api.job_api.model.Job;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JobControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddJobAndGetAllJobs() throws Exception {
        Job job = new Job(null, "Software Engineer", null, "India", 60000.0, 100000.0);
        String jobJson = objectMapper.writeValueAsString(job);

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobRole", is("Software Engineer")));

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    public void testUpdateJob() throws Exception {
        Job job = new Job(null, "Developer", "USA", null, 50000.0, 80000.0);
        String jobJson = objectMapper.writeValueAsString(job);

        String content = mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobJson))
                .andReturn().getResponse().getContentAsString();

        Job createdJob = objectMapper.readValue(content, Job.class);
        createdJob.setRole("Senior Developer");

        mockMvc.perform(put("/jobs/" + createdJob.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdJob)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobRole", is("Senior Developer")));
    }

    @Test
    public void testDeleteJob() throws Exception {
        Job job = new Job(null, "Tester", "Canada", null, 40000.0, 70000.0);
        String jobJson = objectMapper.writeValueAsString(job);

        String content = mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobJson))
                .andReturn().getResponse().getContentAsString();

        Job createdJob = objectMapper.readValue(content, Job.class);

        mockMvc.perform(delete("/jobs/" + createdJob.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/jobs/" + createdJob.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddJob_InvalidRequest() throws Exception {
        String invalidJob = "{}"; 

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJob))
                .andExpect(status().isBadRequest());
    }
}
