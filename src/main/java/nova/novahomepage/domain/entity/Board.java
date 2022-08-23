package nova.novahomepage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_name")
    private String typeName;
    private String title;
    private String content;
    private Integer good;
    @Column(name = "number_of_view")
    private Integer numberOfView;

    @OneToMany(mappedBy = "board")
    private List<Chatting> chattings;
}
