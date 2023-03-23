package worthen.projects.diningreviewapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String displayName;
    private String city;
    private String state;
    private Integer zipCode;
    private boolean hasDairyAllergy;
    private boolean hasPeanutAllergy;
    private boolean hasEggAllergy;

}
