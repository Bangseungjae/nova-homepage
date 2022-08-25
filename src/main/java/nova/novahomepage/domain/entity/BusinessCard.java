package nova.novahomepage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "business_card")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BusinessCard implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "business_card_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessCard")
    private List<Skill> skills;
    private String name;
    private String gitLink;

    @OneToOne(mappedBy = "businessCard")
    private Users users;
}
