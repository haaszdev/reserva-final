package com.reservemanager.user.interfaces.rest;
import com.reservemanager.user.application.service.UserApplicationService;
import com.reservemanager.user.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userApplicationService.listUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userApplicationService.findUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userApplicationService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userApplicationService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userApplicationService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> listActiveUsers() {
        return ResponseEntity.ok(userApplicationService.listActiveUsers());
    }

    @GetMapping("/registration/{registration}")
    public ResponseEntity<User> getUserByRegistration(@PathVariable String registration) {
        return userApplicationService.findUserByRegistration(registration)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
