package backend.animeShop.repository;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.animeShop.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
    Category findByName(String name);

    // List<Category> findByFeaturedTrue();

    // @Query("SELECT c FROM Category c WHERE" +
    //         "LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
    //         "LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    // List<Category> searchCategories(@Param("query") String query);

    // List<Category> findCategories(String name);
}
