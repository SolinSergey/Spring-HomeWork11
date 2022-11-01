package ru.gb.homework11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.homework11.entities.CheckBoxGroup;
import ru.gb.homework11.entities.Role;
import ru.gb.homework11.entities.User;
import ru.gb.homework11.entities.UserInfo;
import ru.gb.homework11.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public List<User> findAllUsersList (){
        Iterable<User> userIterable = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for(User u:userIterable){
            users.add(u);
        }
        return users;
    }


    public Iterable<User> findAllUsersIterable (){
        Iterable<User> userIterable = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for(User u:userIterable){
            users.add(u);
        }
        return userIterable;
    }

    public void addUser(UserInfo userInfo, CheckBoxGroup checkBoxGroup){
        User user=new User();
        user.setUsername(userInfo.getName());
        if (userInfo.getId()==0){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        }else {
            user.setId(userInfo.getId());
            user.setPassword(userRepository.findById(userInfo.getId()).get().getPassword());
        }
        user.setEmail(userInfo.getEmail());
        Collection<Role> roles=new ArrayList<>();
        if (checkBoxGroup.isUserRole()) roles.add(new Role(1L,"ROLE_USER"));
        if (checkBoxGroup.isManagerRole()) roles.add((new Role(3L,"ROLE_MANAGER")));
        if (checkBoxGroup.isAdminRole()) roles.add((new Role(2L,"ROLE_ADMIN")));
        if (checkBoxGroup.isRootRole()) roles.add((new Role(4L,"ROLE_ROOT")));
        user.setRoles(roles);
        userRepository.save(user);
    }

}