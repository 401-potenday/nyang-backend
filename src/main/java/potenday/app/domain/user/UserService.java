package potenday.app.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.domain.auth.AppUser;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

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

  @Transactional
  public String registerNickname(AppUser appUser, String nickname) {
    User user = findAppUser(appUser);
    if (!checkAvailableNickname(nickname)) {
      throw new PotendayException(ErrorCode.U001);
    }
    user.updateNickname(nickname);
    return user.getNickname();
  }

  private User findAppUser(AppUser appUser) {
    return userRepository.findById(appUser.id())
        .orElseThrow(() -> new PotendayException(ErrorCode.U006));
  }
}
