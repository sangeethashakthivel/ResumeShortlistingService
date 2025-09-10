package com.project.resume_shortlisting.repository;

import com.project.resume_shortlisting.model.SkillMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillMasterRepository extends JpaRepository<SkillMaster, Long> {
    Optional<SkillMaster> findBySkillNameIgnoreCase(String skillName);
}
