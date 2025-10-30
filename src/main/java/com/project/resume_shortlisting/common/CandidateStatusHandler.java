package com.project.resume_shortlisting.common;

import com.project.resume_shortlisting.model.JobRole;
import com.project.resume_shortlisting.model.Resume;
import com.project.resume_shortlisting.repository.JobRoleRepository;
import com.project.resume_shortlisting.repository.ResumeRepository;
import com.project.resume_shortlisting.utility.FuzzyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CandidateStatusHandler implements BotResponseHandler {

    @Autowired
    private ResumeRepository resumeRepository;
    @Override
    public boolean canHandle(BotQuestionType type) {
        return type == BotQuestionType.CANDIDATE_STATUS;
    }

    @Override
    public String handle(String userInput) {
        String lower = userInput.toLowerCase().replace("?", "").trim();

        // Remove status keywords
        for (String keyword : new String[]{"is", "did", "selected", "rejected", "clear"}) {
            lower = lower.replace(keyword, "").trim();
        }

        if (lower.isEmpty()) return "Please specify the candidate name.";

        // Fuzzy match candidate name
        String candidateName = lower; // effectively final
        Optional<Resume> candidate = resumeRepository.findAll().stream()
                .filter(r -> FuzzyMatcher.isSimilar(r.getCandidateName().toLowerCase(), candidateName, 2))
                .findFirst();

        return candidate.map(r -> "Candidate " + r.getCandidateName() + " is " + r.getStatus())
                .orElse("No resume found for candidate: " + lower);
    }
}
