package potenday.app.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "select u from User u where u.oAuthUid = :oauthUid and u.isWithDraw = false ")
  Optional<User> findOauthUser(String oauthUid);

  boolean existsByNickname(String nickname);

  Optional<User> findById(long id);

  @Query(value = "select u from User u where u.id = :userId and u.isWithDraw = false ")
  Optional<User> findAppUser(long userId);

  @Query(value = "select u.nickname from User u where u.id = :id")
  String findNicknameById(long id);
}
