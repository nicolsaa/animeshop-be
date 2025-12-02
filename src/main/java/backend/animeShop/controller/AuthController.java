package backend.animeShop.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.animeShop.dto.AuthRequest;
import backend.animeShop.dto.AuthResponse;
import backend.animeShop.model.User;
import backend.animeShop.service.AuthService;
import backend.animeShop.dto.RegisterRequest;
//import backend.animeShop.service.UserService;

import java.util.Optional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    // @Autowired
    // private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        Optional<User> userOptional = authService.authenticate(authRequest);
        
        if (userOptional.isPresent()) {
            AuthResponse authResponse = authService.generateAuthResponse(userOptional.get());
            return ResponseEntity.ok(authResponse);
        } else {
            return ResponseEntity.badRequest()
                    .body("Credenciales inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword(), null, null);
            User newUser = authService.register(user);
            AuthResponse authResponse = authService.generateAuthResponse(newUser);
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
