package com.project.resume_shortlisting.common;

import com.project.resume_shortlisting.repository.SkillMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class SkillWeightageHandler implements BotResponseHandler {

    @Autowired
    SkillMasterRepository skillRepo;

    @Override
    public boolean canHandle(BotQuestionType type) {
        return type == BotQuestionType.SKILL_WEIGHTAGE;
    }

    @Override
    public String handle(String userInput) {
        String skill = userInput.replaceAll("(?i).*\\b(of|about)\\s+", "").trim();
        return skillRepo.findBySkillNameIgnoreCase(skill)
                .map(s -> "The weightage (importance) of " + skill + " is " + s.getWeightage())
                .orElse("Sorry, I don’t have data for skill: " + skill);
    }
}
