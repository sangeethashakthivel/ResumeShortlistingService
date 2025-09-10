package com.project.resume_shortlisting.common;

import org.springframework.stereotype.Component;

@Component
public class GreetingHandler implements BotResponseHandler {

    @Override
    public boolean canHandle(BotQuestionType type) {
        return type == BotQuestionType.GREETING;
    }

    @Override
    public String handle(String userInput) {
        return "Hi! 👋 How can I assist you today?";
    }
}
