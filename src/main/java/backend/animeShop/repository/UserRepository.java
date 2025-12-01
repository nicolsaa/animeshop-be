package backend.animeShop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import backend.animeShop.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFullUsername(String fullUsername);
    Boolean existsByEmail(String email);
    Boolean existsByFullUsername(String fullUsername);
}