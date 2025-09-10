package com.project.resume_shortlisting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LK_JobRole", schema = "dbo")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "ThresholdScore")
    private double thresholdScore;

}
