package nova.novahomepage.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    @Column(name = "student_number")
    private String studentNumber;

    private String password;
    private Integer ssn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_card_id")
    BusinessCard businessCard;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_id",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_id")})
    private List<Authority> authority = new ArrayList<>();


    public void setEncodedPassword(String password) {
        this.password = password;
    }
}
