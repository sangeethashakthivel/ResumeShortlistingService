package com.project.resume_shortlisting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Resumes", schema = "dbo")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CandidateName")
    private String candidateName;

    @Column(name = "Content")
    private String content;

    @Column(name = "Score")
    private double score;

    @Column(name = "AtsScore")
    private double atsScore;

    @Column(name = "ExperienceScore")
    private double experienceScore;

    @Column(name = "Status")
    private String status;

    @Column(name = "JobTitle")
    private String jobTitle;

    @Column(name = "UploadedAt")
    private LocalDateTime uploadedAt;
}
