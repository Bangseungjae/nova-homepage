package nova.novahomepage.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Users implements Serializable {

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
    @JoinTable(name = "user_authority",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authority_id")})
    private Set<Authority> authority = new HashSet<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Board> board = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    public void setEncodedPassword(String password) {
        this.password = password;
    }
}
