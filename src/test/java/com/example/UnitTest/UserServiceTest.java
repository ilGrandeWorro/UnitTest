package com.example.UnitTest;

import com.example.UnitTest.User.User;
import com.example.UnitTest.User.UserRepository;
import com.example.UnitTest.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles(value = "test")
public class UserServiceTest {


    @Autowired
    private UserRepository repo;
    @Autowired
    private UserService service;


    User userTest = new User(1L, "Alessio", "Delle Donne");
    User userTest2 = new User(2L, "Sio", "Worro");

    @Test
    public void testCreateUser() throws Exception {
       service.create(userTest);
       assertTrue(repo.existsById(userTest.getId()));
    }

    @Test
    public void testSearchAllUsers() {


        repo.save(userTest);
        repo.save(userTest2);
        List<User> result = service.searchAll();
        assertNotNull(result);
    }

    @Test
    public void testSearchSingleUser() {
        repo.save(userTest);
        User result = repo.findById(userTest.getId()).get();
        assertEquals(result.getId(), userTest.getId());
    }

    @Test
    public void testUpdateUser() {
        repo.save(userTest);
        User result = service.update(userTest.getId(), userTest2);
        assertEquals(result.getId(), userTest.getId());
        assertEquals(result.getName(), userTest2.getName());
    }

    @Test
    public void testDeleteUser(){
        repo.save(userTest);
        service.delete(userTest.getId());
        assertFalse(repo.existsById(userTest.getId()));
    }
}