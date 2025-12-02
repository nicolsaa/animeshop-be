package backend.animeShop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para registrar un nuevo usuario.
 * Separado de la entidad User para evitar exponer internamente estructuras de persistencia.
 */
public class RegisterRequest {
    @NotNull(message = "El nombre de usuario es obligatorio")
    @Size(min = 1, message = "El nombre de usuario es obligatorio")
    private String username;
    @NotNull(message = "El email es obligatorio")
    @Size(min = 1, message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;
    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 1, message = "La contraseña es obligatoria")
    private String password;

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
