package com.project.resume_shortlisting.service;

import com.project.resume_shortlisting.model.BotFAQ;
import com.project.resume_shortlisting.model.JobRole;
import com.project.resume_shortlisting.model.SkillMaster;
import com.project.resume_shortlisting.repository.BotFAQRepository;
import com.project.resume_shortlisting.repository.JobRoleRepository;
import com.project.resume_shortlisting.repository.SkillMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    BotFAQRepository botFAQRepository;
    @Autowired
    JobRoleRepository jobRoleRepository;
    @Autowired
    SkillMasterRepository skillMasterRepository;

    // ---------------- BOT FAQ ----------------
    public List<BotFAQ> getAllBotFAQs() {
        return botFAQRepository.findAll();
    }

    public BotFAQ saveBotFAQ(BotFAQ botFAQ) {
        return botFAQRepository.save(botFAQ);
    }

    public void deleteBotFAQ(Long id) {
        botFAQRepository.deleteById(id);
    }

    public Optional<BotFAQ> updateBotFAQ(Long id, BotFAQ updatedFAQ) {
        return botFAQRepository.findById(id).map(existing -> {
            existing.setKeyword(updatedFAQ.getKeyword());
            existing.setAnswer(updatedFAQ.getAnswer());
            return botFAQRepository.save(existing);
        });
    }

    // ---------------- JOB ROLES ----------------
    public List<JobRole> getAllJobRoles() {
        return jobRoleRepository.findAll();
    }

    public JobRole saveJobRole(JobRole jobRole) {
        return jobRoleRepository.save(jobRole);
    }

    public void deleteJobRole(Long id) {
        jobRoleRepository.deleteById(id);
    }

    public Optional<JobRole> updateJobRole(Long id, JobRole updatedRole) {
        return jobRoleRepository.findById(id).map(existing -> {
            existing.setTitle(updatedRole.getTitle());
            existing.setThresholdScore(updatedRole.getThresholdScore());
            return jobRoleRepository.save(existing);
        });
    }

    // ---------------- SKILLS ----------------
    public List<SkillMaster> getAllSkills() {
        return skillMasterRepository.findAll();
    }

    public SkillMaster saveSkill(SkillMaster skill) {
        return skillMasterRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillMasterRepository.deleteById(id);
    }

    public Optional<SkillMaster> updateSkill(Long id, SkillMaster updatedSkill) {
        return skillMasterRepository.findById(id).map(existing -> {
            existing.setSkillName(updatedSkill.getSkillName());
            existing.setWeightage(updatedSkill.getWeightage());
            return skillMasterRepository.save(existing);
        });
    }
}
