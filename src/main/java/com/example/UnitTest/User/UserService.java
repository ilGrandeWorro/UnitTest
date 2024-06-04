package com.example.UnitTest.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public User create (User user){
        return repo.save((user));
    }

    public List<User> searchAll (){
        return repo.findAll();
    }

    public User searchOne(long id){
        return repo.findById(id).get();
    }

    public User update(long id, User user) {
        User result = repo.findById(id).get();
        result.setName(user.getName());
        result.setSurname(user.getSurname());
        return result;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
