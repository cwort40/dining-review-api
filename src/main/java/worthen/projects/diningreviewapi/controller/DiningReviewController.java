package worthen.projects.diningreviewapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worthen.projects.diningreviewapi.model.DiningReview;
import worthen.projects.diningreviewapi.model.ReviewStatus;
import worthen.projects.diningreviewapi.repository.DiningReviewRepository;

@RestController
@RequestMapping("/reviews")
public class DiningReviewController {

    @Autowired
    private DiningReviewRepository diningReviewRepository;

    @GetMapping("/{id}")
    public ResponseEntity<DiningReview> getDiningReviewById(@PathVariable Long id) {
        DiningReview diningReview = diningReviewRepository.findById(id).orElse(null);
        if (diningReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(diningReview, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DiningReview> createDiningReview(@RequestBody DiningReview diningReview) {
        ResponseEntity<DiningReview> validationResponse = validateDiningReview(diningReview);
        if (validationResponse != null) {
            return validationResponse;
        }
        DiningReview newDiningReview = diningReviewRepository.save(diningReview);
        return new ResponseEntity<>(newDiningReview, HttpStatus.CREATED);
    }

    @PutMapping("/admin/reviews/{id}/approve")
    public ResponseEntity<DiningReview> approveDiningReview(@PathVariable Long id) {
        DiningReview diningReview = diningReviewRepository.findById(id).orElse(null);
        if (diningReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        diningReview.setReviewStatus(ReviewStatus.ACCEPTED);
        DiningReview updatedDiningReview = diningReviewRepository.save(diningReview);
        return new ResponseEntity<>(updatedDiningReview, HttpStatus.OK);
    }

    @DeleteMapping("/admin/reviews/{id}")
    public ResponseEntity<DiningReview> deleteDiningReview(@PathVariable Long id) {
        DiningReview deletedDiningReview = diningReviewRepository.findById(id).orElse(null);
        if (deletedDiningReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        diningReviewRepository.delete(deletedDiningReview);
        return new ResponseEntity<>(deletedDiningReview, HttpStatus.OK);
    }

    private ResponseEntity<DiningReview> validateDiningReview(DiningReview diningReview) {
        if (diningReview.getSubmitterDisplayName() == null ||
                diningReview.getSubmitterDisplayName().isEmpty() ||
                diningReview.getRestaurantId() == null ||
                diningReview.getPeanutScore() == null ||
                diningReview.getPeanutScore() < 0 ||
                diningReview.getPeanutScore() > 5 ||
                diningReview.getEggScore() == null ||
                diningReview.getEggScore() < 0 ||
                diningReview.getEggScore() > 5 ||
                diningReview.getDairyScore() == null ||
                diningReview.getDairyScore() < 0 ||
                diningReview.getDairyScore() > 5 ||
                diningReview.getCommentary() == null ||
                diningReview.getCommentary().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (diningReview.getReviewStatus() == null) {
            diningReview.setReviewStatus(ReviewStatus.PENDING);
        }

        // Check that restaurant exists
        // (Assuming the restaurant ID is valid and corresponds to an existing restaurant in the database)
        // This can be done using a separate method in the RestaurantRepository, e.g. findById(Long id)

        return null;
    }
}
