package worthen.projects.diningreviewapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import worthen.projects.diningreviewapi.model.DiningReview;
import worthen.projects.diningreviewapi.model.ReviewStatus;

@Repository
public interface DiningReviewRepository extends JpaRepository<DiningReview, Long> {

    List<DiningReview> findReviewsByStatus(ReviewStatus reviewStatus);

}

