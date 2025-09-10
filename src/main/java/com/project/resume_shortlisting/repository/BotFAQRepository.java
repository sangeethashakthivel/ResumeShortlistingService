package com.project.resume_shortlisting.repository;

import com.project.resume_shortlisting.model.BotFAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotFAQRepository extends JpaRepository<BotFAQ, Long> {
    Optional<BotFAQ> findByKeywordIgnoreCase(String keyword);

}
