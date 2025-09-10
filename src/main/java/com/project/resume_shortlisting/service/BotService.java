package com.project.resume_shortlisting.service;

import com.project.resume_shortlisting.common.BotQuestionType;
import com.project.resume_shortlisting.common.BotResponseHandler;
import com.project.resume_shortlisting.utility.FuzzyMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotService {

    private final List<BotResponseHandler> handlers;

    public String getResponse(String userInput) {
        BotQuestionType type = classify(userInput);

        return handlers.stream()
                .filter(h -> h.canHandle(type))
                .findFirst()
                .map(h -> h.handle(userInput))
                .orElse("Sorry, I didn’t understand that. 🤔 Can you rephrase?");
    }

    // --- Natural language + fuzzy matching ---
    private BotQuestionType classify(String input) {
        String lower = input.toLowerCase();

        if (matches(lower, new String[]{"hi", "hello", "hey", "yo", "whats up"}))
            return BotQuestionType.GREETING;

        if (matches(lower, new String[]{"weightage", "importance", "how important", "tell me about"}))
            return BotQuestionType.SKILL_WEIGHTAGE;

        if (matches(lower, new String[]{"top", "best", "show me", "list"})
                && matches(lower, new String[]{"candidate", "people", "guys"}))
            return BotQuestionType.TOP_CANDIDATES;

        if (matches(lower, new String[]{"threshold", "passing", "minimum", "score needed"}))
            return BotQuestionType.JOB_THRESHOLD;

        if (matches(lower, new String[]{"is", "did"})
                && matches(lower, new String[]{"selected", "rejected", "clear"}))
            return BotQuestionType.CANDIDATE_STATUS;

        return BotQuestionType.UNKNOWN;

    }

    private boolean matches(String input, String[] keywords) {
        for (String keyword : keywords) {
            if (input.contains(keyword)) return true;
            if (FuzzyMatcher.isSimilar(input, keyword, 2)) return true; // allow small typos
        }
        return false;
    }
}
