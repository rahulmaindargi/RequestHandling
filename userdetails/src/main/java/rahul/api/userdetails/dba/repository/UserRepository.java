package rahul.api.userdetails.dba.repository;

import rahul.api.userdetails.dba.entity.User;

import java.util.List;

public interface UserRepository {
    /*User findByEmail(String email);*/

    User findByLoginEmail(String loginId, String email);

    void save(User user);

    User findByLogin(String login);

    List<User> findLikeLogin(String term);

    void merge(User user);
}
