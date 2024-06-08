package org.example.kickoffcommunity.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

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
}