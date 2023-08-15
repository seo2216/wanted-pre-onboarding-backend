package wanted.backend.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wanted.backend.security.TokenProvider;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/api/auth/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDto, BindingResult bindingResult) {
        // 유효성 검사
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError error : fieldErrors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        try {
            if (userDto != null && userDto.getPassword() != null) {
                UserEntity user = UserEntity.builder().userEmail(userDto.getUserEmail()).password(this.passwordEncoder.encode(userDto.getPassword())).build();
                UserEntity registeredUser = this.userService.create(user);
                UserDTO responseUserDto = UserDTO.builder().id(registeredUser.getId()).userEmail(registeredUser.getUserEmail()).build();
                return ResponseEntity.ok().body(responseUserDto);
            } else {
                throw new RuntimeException("Invalid Password Value.");
            }
        } catch (Exception var5) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(var5.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody UserDTO userDto, BindingResult bindingResult) {
        // 유효성 검사
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError error : fieldErrors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        UserEntity user = userService.getByCheckedUser(userDto.getUserEmail(), userDto.getPassword()
                , passwordEncoder);

        if (user != null) {
            String token = tokenProvider.createToken(user);

            UserDTO response = UserDTO.builder()
                    .id(user.getId())
                    .userEmail(user.getUserEmail())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(response);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder().error("Login Failed").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}

