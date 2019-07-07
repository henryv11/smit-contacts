package ee.smit.job.application.spring.controller;

import ee.smit.job.application.spring.model.payload.UserIdentityAvailability;
import ee.smit.job.application.spring.model.payload.UserInfo;
import ee.smit.job.application.spring.model.repository.UserRepository;
import ee.smit.job.application.spring.security.annotation.CurrentUser;
import ee.smit.job.application.spring.security.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserInfo getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserInfo userSummary = new UserInfo(currentUser.getId(), currentUser.getUsername());
        return userSummary;
    }

    @GetMapping("/checkUsernameAvailability/{username}")
    public UserIdentityAvailability checkUsernameAvailability(@PathVariable(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }
}
