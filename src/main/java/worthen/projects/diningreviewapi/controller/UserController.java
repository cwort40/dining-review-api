package worthen.projects.diningreviewapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worthen.projects.diningreviewapi.model.User;
import worthen.projects.diningreviewapi.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{displayName}")
    public ResponseEntity<User> getUserByDisplayName(@PathVariable String displayName) {
        User user = userRepository.findByDisplayName(displayName);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        ResponseEntity<User> validationResponse = validateUser(user);
        if (validationResponse != null) {
            return validationResponse;
        }
        User newUser = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{displayName}")
    public ResponseEntity<User> updateUser(@PathVariable String displayName, @RequestBody User user) {
        User existingUser = userRepository.findByDisplayName(displayName);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingUser.setCity(user.getCity());
        existingUser.setState(user.getState());
        existingUser.setZipCode(user.getZipCode());
        existingUser.setHasDairyAllergy(user.isHasDairyAllergy());
        existingUser.setHasPeanutAllergy(user.isHasPeanutAllergy());
        existingUser.setHasEggAllergy(user.isHasEggAllergy());
        User updatedUser = userRepository.save(existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{displayName}")
    public ResponseEntity<User> deleteUser(@PathVariable String displayName) {
        User deletedUser = userRepository.findByDisplayName(displayName);
        if (deletedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userRepository.delete(deletedUser);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }

    private ResponseEntity<User> validateUser(User user) {
        if (user.getDisplayName() == null ||
                user.getDisplayName().isEmpty() ||
                user.getCity() == null ||
                user.getCity().isEmpty() ||
                user.getState() == null ||
                user.getState().isEmpty() ||
                user.getZipCode() == null ||
                user.getZipCode() < 0 ||
                user.getZipCode() > 99999) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}


