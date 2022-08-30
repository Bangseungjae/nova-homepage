package nova.novahomepage.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardPageDto {


    private Long id;
    @NotNull
    private String typeName;
    @NotNull
    private String title;
    @NotNull
    private Integer good;

    private String writer;
    private String studentNumber;
}
