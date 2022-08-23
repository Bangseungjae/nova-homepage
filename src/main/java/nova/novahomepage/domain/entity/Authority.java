package nova.novahomepage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "authority_id")
    Long id;

    @Column(name = "authority_name")
    private String name;

//    @ManyToOne()
//    Users users;

    public Authority(String name) {
        this.name = name;
    }
}
