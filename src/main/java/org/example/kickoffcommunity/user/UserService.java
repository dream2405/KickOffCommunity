package org.example.kickoffcommunity.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SiteUser create(String username, String studentnum, String name, String password) {
        // Check for null or empty values
        if (username == null || username.isEmpty() ||
            studentnum == null || studentnum.isEmpty() ||
            name == null || name.isEmpty() ||
            password == null || password.isEmpty()) {
            throw new IllegalArgumentException("필수 필드가 비어 있습니다.");
        }

        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setStudentnum(studentnum);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("이미 등록된 사용자명 또는 학생 번호입니다.");
        }

        return user;

    }
    @Transactional(readOnly = true)
    public SiteUser getUser(String username) {
        return userRepository.findByusername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }

    public Optional<SiteUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByusername(username);
    }

    public SiteUser updateUserTeam(String username, String teamName) {
        return userRepository.findByusername(username).map(user -> {
            user.setTeam(teamName);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}