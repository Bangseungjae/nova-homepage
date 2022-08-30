package nova.novahomepage.service;

import lombok.RequiredArgsConstructor;
import nova.novahomepage.domain.entity.BusinessCard;
import nova.novahomepage.domain.entity.Skill;
import nova.novahomepage.domain.entity.Users;
import nova.novahomepage.repository.BusinessCardRepository;
import nova.novahomepage.repository.SkillRepository;
import nova.novahomepage.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BusinessCardService {

    private final BusinessCardRepository businessCardRepository;
    private final SkillRepository skillRepository;
    private final UsersRepository usersRepository;

    public void updateBusinessCard(String studentNumber, List<String> skills, String email, String gitLink) {
        Users users = usersRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다."));
        BusinessCard businessCard = users.getBusinessCard();
        extracted(businessCard);
        List<Skill> skillList = new ArrayList<>();
        if (!skills.isEmpty()) {
            for (String skill : skills) {
                Skill skill1 = Skill.builder().skillName(skill).build();
                skillRepository.save(skill1);
                skillList.add(skill1);
            }
            businessCard.setSkills(skillList);
        }
        if (!email.isEmpty()) {
            businessCard.setEmail(email);
        }
        if (!gitLink.isEmpty()) {
            businessCard.setGitLink(gitLink);
        }
    }

    private void extracted(BusinessCard businessCard) {
        List<Skill> skills1 = businessCard.getSkills();
        for (Skill skill : skills1) {
            skillRepository.delete(skill);
        }
    }

    public BusinessCard getBusinessCard(String studentNumber) {
        Users users = usersRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다."));
        return users.getBusinessCard();
    }

    public List<BusinessCard> getAllBusinessCard() {
        List<BusinessCard> all = businessCardRepository.findAll();
        return all;
    }
}
