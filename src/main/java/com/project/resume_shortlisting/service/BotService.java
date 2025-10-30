package com.project.resume_shortlisting.service;

import com.project.resume_shortlisting.common.BotQuestionType;
import com.project.resume_shortlisting.common.BotResponseHandler;
import com.project.resume_shortlisting.utility.FuzzyMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BotService {

    private final List<BotResponseHandler> handlers;

    // Keywords per BotQuestionType
    private static final Map<BotQuestionType, List<String[]>> KEYWORDS = new LinkedHashMap<>();

    static {
        // GREETING
        List<String[]> greetingList = new LinkedList<>();
        greetingList.add(new String[]{"hi", "hello", "hey", "yo", "whats up", "good morning", "morning", "good night"});
        KEYWORDS.put(BotQuestionType.GREETING, greetingList);

        // SKILL_WEIGHTAGE
        List<String[]> skillList = new LinkedList<>();
        skillList.add(new String[]{"weightage", "score for", "importance", "how important", "tell me about"});
        KEYWORDS.put(BotQuestionType.SKILL_WEIGHTAGE, skillList);

        // TOP_CANDIDATES
        List<String[]> topCandidatesList = new LinkedList<>();
        topCandidatesList.add(new String[]{"top", "best", "show me", "list"});
        topCandidatesList.add(new String[]{"candidate", "people", "guys"});
        KEYWORDS.put(BotQuestionType.TOP_CANDIDATES, topCandidatesList);

        // JOB_THRESHOLD
        List<String[]> jobThresholdList = new LinkedList<>();
        jobThresholdList.add(new String[]{"threshold", "passing", "minimum", "minimum score", "score needed"});
        KEYWORDS.put(BotQuestionType.JOB_THRESHOLD, jobThresholdList);

        // CANDIDATE_STATUS
        List<String[]> candidateStatusList = new LinkedList<>();
        candidateStatusList.add(new String[]{"is", "did"});
        candidateStatusList.add(new String[]{"selected", "rejected", "clear"});
        KEYWORDS.put(BotQuestionType.CANDIDATE_STATUS, candidateStatusList);
    }

    /**
     * Get bot response based on user input
     */
    public String getResponse(String userInput) {
        BotQuestionType type = classify(userInput);

        return handlers.stream()
                .filter(h -> h.canHandle(type))
                .findFirst()
                .map(h -> h.handle(userInput))
                .orElse("Sorry, I didn’t understand that. 🤔 Can you rephrase?");
    }

    /**
     * Classify user input into BotQuestionType
     */
    private BotQuestionType classify(String input) {
        String lower = input.toLowerCase().replace("?", "").trim();

        for (Map.Entry<BotQuestionType, List<String[]>> entry : KEYWORDS.entrySet()) {
            List<String[]> groups = entry.getValue();

            boolean matched;

            if (entry.getKey() == BotQuestionType.CANDIDATE_STATUS) {
                // For candidate status, just check if any status keyword is present
                matched = matches(lower, groups.get(1)); // second group: selected/rejected/clear
            } else if (groups.size() == 1) {
                matched = matches(lower, groups.get(0));
            } else {
                // multiple groups, all groups must match at least one keyword
                matched = true;
                for (String[] group : groups) {
                    if (!matches(lower, group)) {
                        matched = false;
                        break;
                    }
                }
            }

            if (matched) return entry.getKey();
        }

        return BotQuestionType.UNKNOWN;
    }

    /**
     * Check if input contains any of the keywords (with fuzzy match)
     */
    private boolean matches(String input, String[] keywords) {
        for (String keyword : keywords) {
            if (input.contains(keyword)) return true;
            if (FuzzyMatcher.isSimilar(input, keyword, 2)) return true;
        }
        return false;
    }
}