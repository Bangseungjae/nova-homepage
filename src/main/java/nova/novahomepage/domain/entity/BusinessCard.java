package nova.novahomepage.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "business_card")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BusinessCard implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "business_card_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Skill> skills = new ArrayList<>();
    private String name;
    private String gitLink;
    private String email;

    @OneToOne(mappedBy = "businessCard")
    private Users users;
}
