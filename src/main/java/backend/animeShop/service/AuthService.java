package backend.animeShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.animeShop.dto.AuthRequest;
import backend.animeShop.dto.AuthResponse;
import backend.animeShop.model.User;
import backend.animeShop.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

    public Optional<User> authenticate(AuthRequest authRequest) {
        Optional<User> userOptional = userRepository.findByEmail(authRequest.getEmail());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // En una aplicación real, aquí verificarías la contraseña hasheada
            if (user.getPassword().equals(authRequest.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public User register(User user) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        if (userRepository.existsByFullUsername(user.getFullUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        
        return userRepository.save(user);
    }

    public AuthResponse generateAuthResponse(User user) {
        // En una aplicación real, aquí generarías un JWT token
        String mockToken = "mock-jwt-token-" + user.getId();
        
        return new AuthResponse(
            mockToken,
            user.getId(),
            user.getFullUsername(),
            user.getEmail(),
            user.getRole()
        );
    }
}