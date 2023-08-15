package wanted.backend.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String token;
    private Integer id;

     @Email(message = "이메일 형식에 맞지 않습니다.")
     private String userEmail;
     @Pattern(regexp = ".{8}", message = "비밀번호는 8자 이상 입력하세요.")
     private String password;
}
