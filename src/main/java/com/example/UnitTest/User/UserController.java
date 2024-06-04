package com.example.UnitTest.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping
    public User createUser (@RequestBody User user){
        return service.create(user);
    }

    @GetMapping
    public List<User> showUsers (){
        return service.searchAll();
    }

    @GetMapping("/{id}")
    public User showASingleUser (@PathVariable Long id){
        return service.searchOne(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(service.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
