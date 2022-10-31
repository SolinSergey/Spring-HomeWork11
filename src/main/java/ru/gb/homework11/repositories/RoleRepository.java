package ru.gb.homework11.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.homework11.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
