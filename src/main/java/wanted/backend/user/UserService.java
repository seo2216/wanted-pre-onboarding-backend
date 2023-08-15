package wanted.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
