package dev.codeio.HelloWorld.controller;

import dev.codeio.HelloWorld.models.User;
import dev.codeio.HelloWorld.repository.UserRepository;
import dev.codeio.HelloWorld.service.UserService;
import dev.codeio.HelloWorld.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        if (userRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>(
                    "Email already exists",
                    HttpStatus.CONFLICT
            );
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        userService.createUser(user);

        return new ResponseEntity<>(
                "Successfully Registered",
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(
            @RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        var userOptional =
                userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(
                    HttpStatus.NOT_FOUND
            ).build();
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(
                password,
                user.getPassword())) {

            return ResponseEntity.status(
                    HttpStatus.UNAUTHORIZED
            ).build();
        }

        String token =
                jwtUtil.generateToken(email);

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }
}