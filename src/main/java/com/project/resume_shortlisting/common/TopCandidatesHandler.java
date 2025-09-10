package com.project.resume_shortlisting.common;

import com.project.resume_shortlisting.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component

public class TopCandidatesHandler implements BotResponseHandler {

    @Autowired
    ResumeRepository resumeRepo;

    @Override
    public boolean canHandle(BotQuestionType type) {
        return type == BotQuestionType.TOP_CANDIDATES;
    }

    @Override
    public String handle(String userInput) {
        return resumeRepo.findTop3ByStatusOrderByScoreDesc("Selected").stream()
                .map(r -> r.getCandidateName() + " (Score: " + r.getScore() + ")")
                .collect(Collectors.joining(", ",
                        "Top 3 selected candidates are: ", ""));
    }
}
