package com.example.UnitTest;

import com.example.UnitTest.User.User;
import com.example.UnitTest.User.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService service;
    @Autowired
    private ObjectMapper mapper;

    User userTest = new User(1L, "Alessio", "Delle Donne");
    User userTest2 = new User(2L, "Sio", "Worro");

    @Test
    public void testCreateUser() throws Exception {
        String json = mapper.writeValueAsString(userTest);
        MvcResult result = this.mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User response = mapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertEquals(userTest.getId(), response.getId());
    }

    @Test
    public void testShowAllUsers() throws Exception {
        service.create(userTest);
        service.create(userTest2);

        MvcResult result = this.mvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<User> response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<User>>() {
        });
        assertFalse(response.isEmpty());
    }

    @Test
    public void testShowASingelUser() throws Exception {
        service.create(userTest);

        MvcResult result = this.mvc.perform(get("/users/{id}", userTest.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alessio"))
                .andExpect(jsonPath("$.surname").value("Delle Donne"))
                .andReturn();

        User response = mapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertEquals(userTest.getId(), response.getId());
    }

    @Test
    public void testUpdateUser() throws Exception {
        service.create(userTest);
        String json = mapper.writeValueAsString(userTest2);
        MvcResult result = this.mvc.perform(put("/users/{id}", userTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sio"))
                .andExpect(jsonPath("$.surname").value("Worro"))
                .andReturn();

        User response = mapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertEquals(response.getId(), userTest.getId());
    }

    @Test
    public void testDeleteUser() throws Exception {
        service.create(userTest);

        MvcResult result = this.mvc.perform(delete("/users/{id}", userTest.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()).andReturn();
        List<User>response = service.searchAll();
        assertFalse(response.contains(userTest));
    }
}
