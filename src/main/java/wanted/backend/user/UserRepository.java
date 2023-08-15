package wanted.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findById(Integer id);

    UserEntity findByUserEmail(String userEmail);

    UserEntity findByUserEmailAndPassword(String UserEmail, String password);
}
