package rahul.api.userdetails.dba.repository.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import rahul.api.userdetails.dba.entity.User;
import rahul.api.userdetails.dba.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository("userRepository")
@Transactional
@Log4j2
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByLoginEmail(String loginId, String email) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email and u.loginId= :loginId", User.class)
                    .setParameter("email", email)
                    .setParameter("loginId", loginId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            log.info("User not found for loginId: {}, email {}", loginId, email);
            return null;
        }
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findByLogin(String login) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.loginId = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("User not found for loginId: {}", login);
            return null;
        }
    }

    @Override
    public List<User> findLikeLogin(String term) {
        try {
            /*return entityManager.createQuery("SELECT u FROM User u WHERE u.loginId like %:login%", User.class)
                    .setParameter("login", term)
                    .getResultList();*/
            return entityManager.createQuery("SELECT u FROM User u WHERE  0<LOCATE(:login, u.loginId)", User.class)
                    .setParameter("login", term)
                    .getResultList();
        } catch (Exception e) {
            log.error("Exception while searching for term : "+ term, e);
            throw e;
        }
    }

    @Override
    public void merge(User user) {
        entityManager.merge(user);
    }
}
