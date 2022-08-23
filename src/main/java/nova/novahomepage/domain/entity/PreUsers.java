package nova.novahomepage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "pre_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PreUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pre_user_id")
    private Long id;

    private String name;
    @Column(name = "student_number")
    private String studentNumber;

    private String password;
    private Integer ssn;
}
