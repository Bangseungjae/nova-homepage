package nova.novahomepage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_name")
    private String typeName;
    private String title;
    private String content;
    private Integer good;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Chatting> chatting = new ArrayList<>();

    @ManyToOne
    private Users users;

    public void changeBoard(String title, String content, String typeName) {
        this.title = title;
        this.content = content;
        this.typeName = typeName;
    }
}
