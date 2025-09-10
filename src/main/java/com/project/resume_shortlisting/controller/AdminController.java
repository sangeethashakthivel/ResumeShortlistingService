package com.project.resume_shortlisting.controller;

import com.project.resume_shortlisting.model.BotFAQ;
import com.project.resume_shortlisting.model.JobRole;
import com.project.resume_shortlisting.model.SkillMaster;
import com.project.resume_shortlisting.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")

public class AdminController {

    @Autowired
    AdminService adminService;

    // -------- BOT FAQ --------
    @GetMapping("/botfaq")
    public List<BotFAQ> getAllBotFAQs() {
        return adminService.getAllBotFAQs();
    }

    @PostMapping("/botfaq")
    public BotFAQ createBotFAQ(@RequestBody BotFAQ botFAQ) {
        return adminService.saveBotFAQ(botFAQ);
    }

    @PutMapping("/botfaq/{id}")
    public ResponseEntity<BotFAQ> updateBotFAQ(@PathVariable Long id, @RequestBody BotFAQ botFAQ) {
        return adminService.updateBotFAQ(id, botFAQ)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/botfaq/{id}")
    public ResponseEntity<Void> deleteBotFAQ(@PathVariable Long id) {
        adminService.deleteBotFAQ(id);
        return ResponseEntity.noContent().build();
    }

    // -------- JOB ROLES --------
    @GetMapping("/jobroles")
    public List<JobRole> getAllJobRoles() {
        return adminService.getAllJobRoles();
    }

    @PostMapping("/jobroles")
    public JobRole createJobRole(@RequestBody JobRole jobRole) {
        return adminService.saveJobRole(jobRole);
    }

    @PutMapping("/jobroles/{id}")
    public ResponseEntity<JobRole> updateJobRole(@PathVariable Long id, @RequestBody JobRole jobRole) {
        return adminService.updateJobRole(id, jobRole)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/jobroles/{id}")
    public ResponseEntity<Void> deleteJobRole(@PathVariable Long id) {
        adminService.deleteJobRole(id);
        return ResponseEntity.noContent().build();
    }

    // -------- SKILLS --------
    @GetMapping("/skills")
    public List<SkillMaster> getAllSkills() {
        return adminService.getAllSkills();
    }

    @PostMapping("/skills")
    public SkillMaster createSkill(@RequestBody SkillMaster skill) {
        return adminService.saveSkill(skill);
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<SkillMaster> updateSkill(@PathVariable Long id, @RequestBody SkillMaster skill) {
        return adminService.updateSkill(id, skill)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        adminService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
