package rahul.api.userdetails.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rahul.api.userdetails.dba.entity.Role;
import rahul.api.userdetails.dba.entity.User;
import rahul.api.userdetails.dba.repository.UserRepository;
import rahul.api.userdetails.services.UserService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByLoginEmail(String loginId, String email) {
        return userRepository.findByLoginEmail(loginId, email);
    }

    @Override
    public User findUserByLoginId(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void saveUser(User user) {
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public List<User> getMatchingUsers(String term) {
        return userRepository.findLikeLogin(term);
    }

    @Override
    public boolean hasAnyRole(User loggedInUser, String... roles) {
        Set<Role> roleList = loggedInUser.getRoles();
        Set<String> roleStrings = roleList.stream().map(Role::getRole).collect(Collectors.toSet());
        return Arrays.stream(roles).anyMatch(roleStrings::contains);
    }


    @Override
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.merge(user);

    }


}
