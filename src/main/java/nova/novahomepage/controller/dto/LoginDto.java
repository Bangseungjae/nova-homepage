package nova.novahomepage.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginDto {

    @Size(min = 10, max = 10)
    String studentNumber;
    @Size(min = 6)
    String password;
}
