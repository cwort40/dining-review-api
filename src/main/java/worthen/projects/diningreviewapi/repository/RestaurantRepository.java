package worthen.projects.diningreviewapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import worthen.projects.diningreviewapi.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByNameAndZipCode(String name, String zipCode);

    List<Restaurant> findByZipCodeOrderByOverallScoreDesc(String zipCode);

}
