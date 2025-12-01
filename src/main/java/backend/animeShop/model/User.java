package backend.animeShop.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@NotNull(message = "El nombre de usuario es obligatorio")
@Size(min = 1, message = "El nombre de usuario es obligatorio")
    @Column(unique = true, nullable = false)
    private String fullUsername;

@NotNull(message = "El email es obligatorio")
@Size(min = 1, message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Column(unique = true, nullable = false)
    private String email;

@NotNull(message = "La contraseña es obligatoria")
@Size(min = 1, message = "La contraseña es obligatoria")
    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String role = "USER"; // "USER" o "ADMIN"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructores
    public User() {
    }

    public User(String username, String email, String password, String firstName, String lastName) {
        this.fullUsername = username;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullUsername() {
        return fullUsername;
    }

    public void setFullUsername(String fullUsername) {
        this.fullUsername = fullUsername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    
}