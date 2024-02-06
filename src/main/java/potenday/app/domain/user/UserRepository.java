package potenday.app.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "select u from User u where u.oAuthUid = :oauthUid")
  Optional<User> findOauthUser(String oauthUid);

  boolean existsByNickname(String nickname);

  Optional<User> findById(long id);
}
