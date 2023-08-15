package wanted.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(UserEntity userEntity) {
        if (userEntity != null && userEntity.getUserEmail() != null && userEntity.getPassword() != null) {
            String userEmail = userEntity.getUserEmail();
            return (UserEntity)this.userRepository.save(userEntity);
        } else {
            throw new RuntimeException("Invalid arguments");
        }
    }

    public Optional<UserEntity> getUserById(Integer id){
        Optional<UserEntity> user = userRepository.findById(id);
        return  user;
    }

    public UserEntity getByCheckedUser(String userEmail, String password, PasswordEncoder encoder){
        UserEntity userEntity = userRepository.findByUserEmail(userEmail);
        if(userEntity != null && encoder.matches(password, userEntity.getPassword())){
            return userEntity;
        }
        return  null;
    }

}
