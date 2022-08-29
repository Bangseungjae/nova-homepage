package nova.novahomepage.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @Size(min = 10, max = 10)
    private String studentNumber;

    String name;

    @Min(1000)
    @Max(999999)
    @ApiModelProperty(value = "주민번호", example = "주민번호")
    private Integer ssn; // 주민번호

    @ApiModelProperty(value = "결과", example = "결과")
    private Boolean res;
}
