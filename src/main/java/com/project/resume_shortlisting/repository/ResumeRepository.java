package com.project.resume_shortlisting.repository;

import com.project.resume_shortlisting.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findByCandidateNameIgnoreCase(String name);
    List<Resume> findTop3ByStatusOrderByScoreDesc(String name);

    List<Resume> findByStatus(String status);
}
