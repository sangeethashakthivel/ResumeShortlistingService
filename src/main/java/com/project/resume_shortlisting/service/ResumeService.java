package com.project.resume_shortlisting.service;

import com.project.resume_shortlisting.model.JobRole;
import com.project.resume_shortlisting.model.Resume;
import com.project.resume_shortlisting.model.SkillMaster;
import com.project.resume_shortlisting.repository.JobRoleRepository;
import com.project.resume_shortlisting.repository.ResumeRepository;
import com.project.resume_shortlisting.repository.SkillMasterRepository;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeService {

    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    SkillMasterRepository skillWeightRepository;
    @Autowired
    JobRoleRepository jobRoleRepository;
    @Autowired
    Tika tika;

    /**
     * Upload a resume file, parse candidate name and calculate ATS score
     */
    public Resume uploadResume(MultipartFile file, String jobDescription, String jobTitle, String candidateName) throws IOException, TikaException {
        String content = tika.parseToString(file.getInputStream());


        double score = calculateATSScore(content, jobDescription);

        JobRole jobRole = jobRoleRepository.findByTitleIgnoreCase(jobTitle);
        double threshold = (jobRole != null) ? jobRole.getThresholdScore() : 50; // fallback

        Resume resume = Resume.builder()
                .candidateName(candidateName)
                .content(content)
                .score(score)
                .jobTitle(jobTitle)
                .status(score >= threshold ? "Selected" : "Rejected")
                .uploadedAt(LocalDateTime.now())
                .build();

        return resumeRepository.save(resume);
    }

    /**
     * Extract candidate name from resume content
     */
    private String extractCandidateName(String content, String fileName) {
        // Try "Name: John Doe"
        Pattern pattern = Pattern.compile("(?i)name[:\\s]+([A-Za-z ]+)");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        // Try first line
        String firstLine = content.split("\\R", 2)[0];
        if (firstLine.split(" ").length <= 5) { // usually short like "John Doe"
            return firstLine.trim();
        }

        // Fallback to filename (without extension)
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf('.'));
        }
        return fileName != null ? fileName : "Unknown Candidate";
    }

    /**
     * Get candidate score
     */
    public String calculateScore(String candidateName) {
        Optional<Resume> resume = resumeRepository.findByCandidateNameIgnoreCase(candidateName);
        return resume.map(value -> String.format("✅ Candidate: %s%nATS Score: %.2f%nStatus: %s",
                value.getCandidateName(), value.getScore(), value.getStatus())).orElseGet(() -> "❌ No resume found for candidate: " + candidateName);
    }

    /**
     * ATS Scoring Logic
     */
    private double calculateATSScore(String resumeContent, String jobDescription) {
        double score = 0;

        // Keywords
        String[] jdWords = jobDescription.toLowerCase().split("\\W+");
        for (String word : jdWords) {
            if (resumeContent.toLowerCase().contains(word)) {
                score += 1;
            }
        }

        // Skills with weights
        List<SkillMaster> skillWeights = skillWeightRepository.findAll();
        for (SkillMaster skillWeight : skillWeights) {
            String skill = skillWeight.getSkillName().toLowerCase();
            if (resumeContent.toLowerCase().contains(skill) &&
                    jobDescription.toLowerCase().contains(skill)) {
                score += skillWeight.getWeightage();
            }
        }

        return score;
    }
}
