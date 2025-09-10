package com.project.resume_shortlisting.common;

public interface BotResponseHandler {
    boolean canHandle(BotQuestionType type);
    String handle(String userInput);
}
