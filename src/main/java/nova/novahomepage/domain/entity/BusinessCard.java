package nova.novahomepage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "business_card")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BusinessCard {

    @Id
    @GeneratedValue
    @Column(name = "business_card_id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "businessCard")
    private List<Skill> skills;

    @OneToOne(mappedBy = "businessCard")
    private Users users;
}
