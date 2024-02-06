package potenday.app.domain.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {


  @Query("select t from Token t where t.socialUid = :oauthId")
  Optional<Token> findToken(String oauthId);
}
