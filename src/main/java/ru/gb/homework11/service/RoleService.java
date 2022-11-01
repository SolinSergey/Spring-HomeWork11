package ru.gb.homework11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.homework11.entities.Role;
import ru.gb.homework11.repositories.RoleRepository;
import ru.gb.homework11.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> getRolesById(Long id){
        return roleRepository.findById(id);
    }
}
