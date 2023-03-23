package worthen.projects.diningreviewapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worthen.projects.diningreviewapi.model.Restaurant;
import worthen.projects.diningreviewapi.repository.RestaurantRepository;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/{name}/{zipCode}")
    public ResponseEntity<Restaurant> getRestaurantByNameAndZipCode(@PathVariable String name,
                                                                    @PathVariable String zipCode) {
        Restaurant restaurant = restaurantRepository.findByNameAndZipCode(name, zipCode);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        ResponseEntity<Restaurant> validationResponse = validateRestaurant(restaurant);
        if (validationResponse != null) {
            return validationResponse;
        }
        Restaurant newRestaurant = restaurantRepository.save(restaurant);
        return new ResponseEntity<>(newRestaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{name}/{zipCode}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String name, @PathVariable String zipCode,
                                                       @RequestBody Restaurant restaurant) {
        Restaurant existingRestaurant = restaurantRepository.findByNameAndZipCode(name, zipCode);
        if (existingRestaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingRestaurant.setAddress(restaurant.getAddress());
        existingRestaurant.setCity(restaurant.getCity());
        existingRestaurant.setState(restaurant.getState());
        existingRestaurant.setZipCode(restaurant.getZipCode());
        existingRestaurant.setPhoneNumber(restaurant.getPhoneNumber());
        existingRestaurant.setEmailAddress(restaurant.getEmailAddress());
        existingRestaurant.setWebsite(restaurant.getWebsite());
        existingRestaurant.setOverallScore(restaurant.getOverallScore());
        existingRestaurant.setPeanutScore(restaurant.getPeanutScore());
        existingRestaurant.setDairyScore(restaurant.getDairyScore());
        existingRestaurant.setEggScore(restaurant.getEggScore());
        Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{name}/{zipCode}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable String name, @PathVariable String zipCode) {
        Restaurant deletedRestaurant = restaurantRepository.findByNameAndZipCode(name, zipCode);
        if (deletedRestaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        restaurantRepository.delete(deletedRestaurant);
        return new ResponseEntity<>(deletedRestaurant, HttpStatus.OK);
    }

    private ResponseEntity<Restaurant> validateRestaurant(Restaurant restaurant) {
        if (restaurant.getName() == null ||
                restaurant.getName().isEmpty() ||
                restaurant.getAddress() == null ||
                restaurant.getAddress().isEmpty() ||
                restaurant.getCity() == null ||
                restaurant.getCity().isEmpty() ||
                restaurant.getState() == null ||
                restaurant.getState().isEmpty() ||
                restaurant.getZipCode() == null ||
                restaurant.getZipCode().isEmpty() ||
                restaurant.getPhoneNumber() == null ||
                restaurant.getPhoneNumber().isEmpty() ||
                restaurant.getEmailAddress() == null ||
                restaurant.getEmailAddress().isEmpty() ||
                restaurant.getOverallScore() == null ||
                restaurant.getOverallScore().isEmpty() ||
                restaurant.getPeanutScore() == null ||
                restaurant.getPeanutScore().isEmpty() ||
                restaurant.getDairyScore() == null ||
                restaurant.getDairyScore().isEmpty() ||
                restaurant.getEggScore() == null ||
                restaurant.getEggScore().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}


