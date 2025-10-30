package com.project.resume_shortlisting.common;

import com.project.resume_shortlisting.model.JobRole;
import com.project.resume_shortlisting.repository.JobRoleRepository;
import com.project.resume_shortlisting.utility.FuzzyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobThresholdHandler implements BotResponseHandler {

    @Autowired
    private JobRoleRepository jobRoleRepository;

    @Override
    public boolean canHandle(BotQuestionType type) {
        return type == BotQuestionType.JOB_THRESHOLD;
    }

    @Override
    public String handle(String userInput) {
        // Remove common words to extract job title
        String cleanedInput = userInput.replaceAll("(?i)threshold|passing|minimum|score needed|for", "").trim();

        List<JobRole> allJobs = jobRoleRepository.findAll();

        // Exact match first
        for (JobRole job : allJobs) {
            if (job.getTitle().equalsIgnoreCase(cleanedInput)) {
                return "The minimum passing score for \"" + job.getTitle() + "\" is: " + job.getThresholdScore();
            }
        }

        // Fuzzy match fallback
        for (JobRole job : allJobs) {
            if (FuzzyMatcher.isSimilar(cleanedInput, job.getTitle(), 2)) {
                return "The minimum passing score for \"" + job.getTitle() + "\" is: " + job.getThresholdScore();
            }
        }

        return "Sorry, I could not find the threshold for job role: " + cleanedInput;
    }
}