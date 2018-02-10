package rahul.api.userdetails.dba.repository.impl;

import org.springframework.stereotype.Repository;
import rahul.api.userdetails.dba.entity.Role;
import rahul.api.userdetails.dba.repository.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository("roleRepository")
@Transactional
public class RoleRepositoryImpl implements RoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role findByRole(String role) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :role", Role.class)
                .setParameter("role", role)
                .getSingleResult();
    }
}
