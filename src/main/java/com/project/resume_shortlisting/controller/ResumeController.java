package com.project.resume_shortlisting.controller;

import com.project.resume_shortlisting.model.Resume;
import com.project.resume_shortlisting.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<Resume> uploadResume(@RequestParam("file") MultipartFile file,
                                               @RequestParam("jobDescription") String jobDescription,
                                               @RequestParam(value = "candidateName",required = true) String candidateName,
                                               @RequestParam("jobTitle") String jobTitle) {
        try {
            Resume savedResume = resumeService.uploadResume(file, jobDescription, jobTitle,candidateName);
            return ResponseEntity.ok(savedResume);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/score/{candidateName}")
    public ResponseEntity<String> getScore(@PathVariable String candidateName) {
        return ResponseEntity.ok(resumeService.calculateScore(candidateName));
    }
}


