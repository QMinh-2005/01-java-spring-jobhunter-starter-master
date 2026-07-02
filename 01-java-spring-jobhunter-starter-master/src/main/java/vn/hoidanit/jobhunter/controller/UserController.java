package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/user")
    public ResponseEntity<User> CreateUser(@RequestBody User user) {
        String hashedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
       User newUser =this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        //return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> DeleteUserById(@PathVariable("userId") long userId) throws IdInvalidException {
        if(userId >1500)
        {
            throw new IdInvalidException("Id không hợp lệ");
        }
        this.userService.delete(userId);
        return ResponseEntity.ok("Xóa thành công");
        //return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công");   
    }
    @GetMapping("user/{userId}")
    public ResponseEntity<User> GetUserInfo(@PathVariable("userId") long userId) {
        User user =this.userService.getInfo(userId);
        return ResponseEntity.ok(user);
        //return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @GetMapping("user/getAllUser")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = this.userService.getAllUser();
        return ResponseEntity.ok(users);
        //return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    @PutMapping("user/{id}")
    public ResponseEntity<User> UpdateUser(@PathVariable("id") long id, @RequestBody User updateUser) 
    {
        updateUser.setId(id);
        User res = this.userService.updateUser(updateUser);
        if(res == null)
        {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }
        //return ResponseEntity.status(HttpStatus.OK).body(res);
        return ResponseEntity.ok(res);
    }
}
