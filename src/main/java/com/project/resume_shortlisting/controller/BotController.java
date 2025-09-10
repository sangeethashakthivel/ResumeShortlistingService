package com.project.resume_shortlisting.controller;

import com.project.resume_shortlisting.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bot")

public class BotController {

    @Autowired
    BotService botService;

    @PostMapping("/chat")
    public String chatWithBot(@RequestParam String message) {
        return botService.getResponse(message);
    }
}
