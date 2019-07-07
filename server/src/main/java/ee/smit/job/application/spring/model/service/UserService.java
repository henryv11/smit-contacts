package ee.smit.job.application.spring.model.service;

import ee.smit.job.application.spring.model.entity.Role;
import ee.smit.job.application.spring.model.entity.User;
import ee.smit.job.application.spring.model.enums.RoleName;
import ee.smit.job.application.spring.model.repository.RoleRepository;
import ee.smit.job.application.spring.model.repository.UserRepository;
import ee.smit.job.application.spring.security.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String username, String password) {
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        return userRepository.save(
                new User(username, passwordEncoder.encode(password), Collections.singleton(userRole))
        );
    }
}
