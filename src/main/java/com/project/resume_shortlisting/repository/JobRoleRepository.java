package com.project.resume_shortlisting.repository;

import com.project.resume_shortlisting.model.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRoleRepository extends JpaRepository<JobRole, Long> {


    JobRole findByTitleIgnoreCase(String title);
}
