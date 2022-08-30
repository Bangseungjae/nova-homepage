package nova.novahomepage.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nova.novahomepage.domain.entity.Skill;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessCardDto {

    private List<String> skills = new ArrayList<>();
    private String name;
    private String gitLink;
    private String email;
}
