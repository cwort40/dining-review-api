package worthen.projects.diningreviewapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class DiningReview {

    @Id
    @GeneratedValue
    private Long id;

    private String submitterDisplayName;
    private Long restaurantId;
    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;
    private String commentary;

}
