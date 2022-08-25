package nova.novahomepage.controller.dto;


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
public class RequestSignUp {
    @Size(min = 10, max = 10)
    private String studentNumber;

    @Size(min = 6, max = 16)
    private String password;

    String name;

    @Min(1000)
    @Max(999999)
    @Size(min = 6, max = 6)
    private Integer ssn; // 주민번호
}
