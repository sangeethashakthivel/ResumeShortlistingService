package com.project.resume_shortlisting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "SkillMaster", schema = "dbo")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SkillName")
    private String skillName;

    @Column(name = "Weightage")
    private int weightage;

}
