package worthen.projects.diningreviewapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worthen.projects.diningreviewapi.model.AdminReviewAction;
import worthen.projects.diningreviewapi.model.DiningReview;
import worthen.projects.diningreviewapi.model.Restaurant;
import worthen.projects.diningreviewapi.model.ReviewStatus;
import worthen.projects.diningreviewapi.repository.DiningReviewRepository;
import worthen.projects.diningreviewapi.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DiningReviewRepository diningReviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping("/reviews/{id}/approve")
    public ResponseEntity<Object> approveDiningReview(@PathVariable Long id,
                                                      @RequestBody AdminReviewAction adminReviewAction) {
        DiningReview diningReview = diningReviewRepository.findById(id).orElse(null);
        if (diningReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Check if dining review has already been approved
        if (diningReview.getReviewStatus() == ReviewStatus.ACCEPTED) {
            return new ResponseEntity<>("Dining review has already been approved", HttpStatus.CONFLICT);
        }

        // Check if admin review action is valid
        if (adminReviewAction.isAccepted()) {
            // Approve dining review
            diningReview.setReviewStatus(ReviewStatus.ACCEPTED);

            // Recalculate restaurant scores
            Long restaurantId = diningReview.getRestaurantId();
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
            if (optionalRestaurant.isPresent()) {
                Restaurant restaurant = optionalRestaurant.get();
                List<DiningReview> approvedReviews = diningReviewRepository
                        .findByRestaurantIdAndReviewStatus(restaurantId, ReviewStatus.ACCEPTED);
                int numReviews = approvedReviews.size();
                if (numReviews > 0) {
                    double peanutScore = approvedReviews.stream().mapToInt(DiningReview::getPeanutScore)
                            .average().orElse(0);
                    double dairyScore = approvedReviews.stream().mapToInt(DiningReview::getDairyScore)
                            .average().orElse(0);
                    double eggScore = approvedReviews.stream().mapToInt(DiningReview::getEggScore)
                            .average().orElse(0);
                    restaurant.setPeanutScore(formatScore(peanutScore));
                    restaurant.setDairyScore(formatScore(dairyScore));
                    restaurant.setEggScore(formatScore(eggScore));
                    restaurant.setOverallScore(formatScore((peanutScore + dairyScore + eggScore) / 3));
                    restaurantRepository.save(restaurant);
                }
            }
        } else {
            // Deny dining review
            diningReview.setReviewStatus(ReviewStatus.REJECTED);
        }

        DiningReview updatedDiningReview = diningReviewRepository.save(diningReview);
        return new ResponseEntity<>(updatedDiningReview, HttpStatus.OK);
    }

    private String formatScore(double score) {
        return String.format("%.2f", score);
    }

}

