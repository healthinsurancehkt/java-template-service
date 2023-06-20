package ca.levio.hackathon.delegate;

import ca.levio.hackathon.api.UserApiDelegate;
import ca.levio.hackathon.dto.UserDTO;
import ca.levio.hackathon.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDelegateImpl implements UserApiDelegate {

    private final UserService userService;

    public UserDelegateImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDTO> saveUser(UserDTO userDTO) {
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}
