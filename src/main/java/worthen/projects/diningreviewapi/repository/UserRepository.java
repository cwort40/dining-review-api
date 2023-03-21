package worthen.projects.diningreviewapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import worthen.projects.diningreviewapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByDisplayName(String displayName);

}

