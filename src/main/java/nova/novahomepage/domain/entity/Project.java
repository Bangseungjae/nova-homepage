package nova.novahomepage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "project_name")
    private String projectName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Skill> skills = new ArrayList<>();
    private String describe;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private String leader;

}