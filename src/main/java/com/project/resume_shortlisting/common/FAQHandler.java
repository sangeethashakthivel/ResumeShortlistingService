package com.project.resume_shortlisting.common;

import com.project.resume_shortlisting.model.BotFAQ;
import com.project.resume_shortlisting.repository.BotFAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FAQHandler implements BotResponseHandler {

    @Autowired
    BotFAQRepository faqRepo;

    @Override
    public boolean canHandle(BotQuestionType type) {
        // fallback for anything "common" if nothing else matched
        return type == BotQuestionType.UNKNOWN;
    }

    @Override
    public String handle(String userInput) {
        // Extract possible keyword (last word in sentence, for simplicity)
        String[] words = userInput.trim().split("\\s+");
        String keyword = words[words.length - 1];

        return faqRepo.findByKeywordIgnoreCase(keyword)
                .map(botFAQ -> botFAQ.getAnswer())
                .orElse("I’m not sure about that 🤔. Please check with an admin.");
    }
}