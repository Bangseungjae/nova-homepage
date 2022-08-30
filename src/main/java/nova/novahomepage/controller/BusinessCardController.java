package nova.novahomepage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.controller.dto.BusinessCardDto;
import nova.novahomepage.domain.entity.BusinessCard;
import nova.novahomepage.domain.entity.Skill;
import nova.novahomepage.service.BusinessCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "명함 기능 API를 제공하는 Controller")
@Slf4j
public class BusinessCardController {
    private final BusinessCardService businessCardService;

    @ApiOperation(value = "명함을 수정한다")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping("/businessCard")
    public ResponseEntity<BusinessCardDto> updateBusinessCard(@RequestBody BusinessCardDto businessCardDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentNumber = authentication.getName();
        businessCardService.updateBusinessCard(
                studentNumber, businessCardDto.getSkills(), businessCardDto.getEmail(), businessCardDto.getGitLink());
        return ResponseEntity.ok().body(businessCardDto);
    }

    @ApiOperation(value = "명함 정보를 본다")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/businessCard/{studentNumber}")
    public ResponseEntity<BusinessCardDto> getBusinessCard(@PathVariable(name = "studentNumber") String studentNumber) {
        BusinessCard businessCard = businessCardService.getBusinessCard(studentNumber);
        List<Skill> skills = businessCard.getSkills();
        List<String> skillList = new ArrayList<>();
        for (Skill skill : skills) {
            skillList.add(skill.getSkillName());
        }
        BusinessCardDto businessCardDto =
                new BusinessCardDto(skillList, businessCard.getName(), businessCard.getGitLink(), businessCard.getEmail());
        return ResponseEntity.ok().body(businessCardDto);
    }

    @ApiOperation(value = "모든 명함을 본다")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/businessCard")
    public ResponseEntity<List<BusinessCardDto>> getAllBusinessCard() {
        List<BusinessCard> allBusinessCard = businessCardService.getAllBusinessCard();
        List<BusinessCardDto> businessCardDtoList= new ArrayList<>();
        for (BusinessCard businessCard : allBusinessCard) {
            List<Skill> skills = businessCard.getSkills();
            List<String> skillList = new ArrayList<>();
            for (Skill skill : skills) {
                skillList.add(skill.getSkillName());
            }
            BusinessCardDto businessCardDto =
                    new BusinessCardDto(skillList, businessCard.getName(), businessCard.getGitLink(), businessCard.getEmail());
            businessCardDtoList.add(businessCardDto);
        }
        return ResponseEntity.ok().body(businessCardDtoList);
    }
}
