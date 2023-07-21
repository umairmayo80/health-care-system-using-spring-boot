package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.domain.User;
import server.dto.UserDTO;
import server.mapper.UserMapper;
import server.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<User> users = userService.getUsers();
        List<UserDTO> userDTOList = users.stream()
                .map(userMapper::toDTO)
                .toList();
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username){
        User user = userService.getByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO userDTO = userMapper.toDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/patients")
    public ResponseEntity<List<UserDTO>> getPatients(){
        List<User> patients = userService.getPatients();
        if (patients == null) {
            return ResponseEntity.notFound().build();
        }

        List<UserDTO> patientsDTOList = patients.stream()
                .map(userMapper::toDTO)
                .toList();
        return ResponseEntity.ok(patientsDTOList);
    }
    @GetMapping("/doctors")
    public ResponseEntity<List<UserDTO>> getDoctors(){
        List<User> doctors = userService.getDoctors();
        if (doctors == null) {
            return ResponseEntity.notFound().build();
        }
        List<UserDTO> doctorsDTOList = doctors.stream()
                .map(userMapper::toDTO)
                .toList();
        return ResponseEntity.ok(doctorsDTOList);
    }

    @GetMapping("/roles/{role}")
    public ResponseEntity<List<UserDTO>> getUserByRole(@PathVariable String role){
        List<User> users = userService.getByRole(role);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        List<UserDTO> userDTOList = users.stream()
                .map(userMapper::toDTO)
                .toList();
        return ResponseEntity.ok(userDTOList);
    }

    @DeleteMapping(path = "/{username}")
    public void deleteUserByUsername(@PathVariable String username){
        userService.deleteUser(username);
    }

    @PutMapping(path = "set_account_status/{username}")
    public void setUserAccountStatus(@PathVariable String username,
                                     @RequestParam(required = true) boolean status){
        userService.setUserAccountStatus(username,status);
        // alternative solution is to get the username, set the username and make the method transaction
    }

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody User newUser){
        userService.addUserEntry(newUser);
        return ResponseEntity.ok("User created Successfully");
    }



}
