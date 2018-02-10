package rahul.api.userdetails.dba.repository;

import rahul.api.userdetails.dba.entity.Role;

public interface RoleRepository {
    Role findByRole(String role);
}
