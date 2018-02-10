package rahul.api.userdetails.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rahul.api.userdetails.controllers.dto.UserDTO;
import rahul.api.userdetails.dba.entity.User;
import rahul.api.userdetails.services.UserService;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/loadUserByUsername/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User loadUserByUsername(@PathVariable(value = "userName") String userName) {
        User user = userService.findUserByLoginId(userName);
        return user;
    }

    @GetMapping(value = "/loadUserByLoginEmail/{loginId}/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findUserByLoginEmail(@PathVariable(value = "loginId") String loginId, @PathVariable(value = "email") String email) {
        User user = userService.findUserByLoginEmail(loginId, email);
        return user;
    }

    @GetMapping(value = "/findMatchingUsers/{term}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getMatchingUsers(String term) {
        return userService.getMatchingUsers(term);
    }

    @GetMapping(value = "/checkRoles/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    boolean hasAnyRole(String login, @RequestParam("roles") String... roles) {
        User user = userService.findUserByLoginId(login);
        return userService.hasAnyRole(user, roles);
    }

    @PostMapping(value = "/changeUserPassword/{login}/{newPassword}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean changeUserPassword(String login, String newPassword) {
        User user = userService.findUserByLoginId(login);
        userService.changeUserPassword(user, newPassword);
        return true;
    }

    @PostMapping(value = "/saveUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@RequestBody UserDTO userDTO) {

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmailId());
        user.setActive(true);
        user.setPassword(userDTO.getPassword());
        user.setLoginId(userDTO.getLoginId());
        userService.saveUser(user);
        return user;
    }

}
