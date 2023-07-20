package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<UserDTO> getUserById(@PathVariable String username){
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




}
