package nova.novahomepage.controller.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestSignUp {
    @NotNull
    @Size(min = 10, max = 10)
    private String studentNumber;

    @NotNull
    @Size(min = 6, max = 16)
    private String password;

    @NotNull
    String name;

    @Min(1000)
    @Max(999999)
    @ApiModelProperty(value = "주민번호", example = "주민번호입니다.",required = true)
    @NotNull
    private Integer ssn; // 주민번호
}
