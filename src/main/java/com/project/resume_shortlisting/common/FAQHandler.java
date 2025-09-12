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
        String input = userInput.trim().toLowerCase();

        // 1. Try exact match with full input
        var faq = faqRepo.findByKeywordIgnoreCase(input);
        if (faq.isPresent()) return faq.get().getAnswer();

        // 2. Fallback: try last word
        String[] words = input.split("\\s+");
        if (words.length > 0) {
            faq = faqRepo.findByKeywordIgnoreCase(words[words.length - 1]);
            if (faq.isPresent()) return faq.get().getAnswer();
        }

        return "I’m not sure about that 🤔. Please check with an admin.";
    }


}