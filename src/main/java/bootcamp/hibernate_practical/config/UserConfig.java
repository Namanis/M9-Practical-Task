package bootcamp.hibernate_practical.config;

import bootcamp.hibernate_practical.entity.AppUser;
import bootcamp.hibernate_practical.enums.UserRole;
import bootcamp.hibernate_practical.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String @NonNull ... args) {
        createUserIfMissing("user", "user", UserRole.USER);
        createUserIfMissing("admin", "admin", UserRole.ADMIN);
    }

    private void createUserIfMissing(String username, String rawPassword, UserRole role) {
        if (userRepository.findByUsername(username).isEmpty()) {
            userRepository.save(new AppUser(username, passwordEncoder.encode(rawPassword), role, true));
        }
    }
}
