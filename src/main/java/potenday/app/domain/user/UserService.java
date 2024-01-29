package potenday.app.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public boolean checkAvailableNickname(String nickname) {
    return !userRepository.existsByNickname(nickname);
  }
}
