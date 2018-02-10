package rahul.api.userdetails.services;

import rahul.api.userdetails.dba.entity.User;

import java.util.List;

public interface UserService {
    /* public User findUserByEmail(String email);*/

    User findUserByLoginEmail(String loginId, String email);

    User findUserByLoginId(String login);

    public void saveUser(User user);

    List<User> getMatchingUsers(String term);

    boolean hasAnyRole(User loggedInUser, String... roles);

    void changeUserPassword(User user, String newPassword);
}
