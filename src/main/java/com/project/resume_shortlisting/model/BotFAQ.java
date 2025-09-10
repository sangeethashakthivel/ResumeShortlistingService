package com.project.resume_shortlisting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LK_BotFAQ", schema = "dbo")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotFAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Keyword")
    private String keyword;

    @Column(name = "Answer")
    private String answer;

}
