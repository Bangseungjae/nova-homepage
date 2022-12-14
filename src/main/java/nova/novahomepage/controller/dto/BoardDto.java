package nova.novahomepage.controller.dto;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class BoardDto {

    private Long id;
    @NotNull
    private String typeName;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private Integer good;

    private List<String> chatting;
    private String writer;
    private String studentNumber;

}

