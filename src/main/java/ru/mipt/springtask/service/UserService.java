package ru.mipt.springtask.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mipt.springtask.DTO.AccountDTO;
import ru.mipt.springtask.entity.AccountEntity;
import ru.mipt.springtask.entity.Role;
import ru.mipt.springtask.entity.UserPrincipal;
import ru.mipt.springtask.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    public UserPrincipal addUser(Set<Role> roles) {
        UserPrincipal user = UserPrincipal.builder().roles(roles).build();
        return userRepository.save(user);
    }

    public Optional<UserPrincipal> getUserPrincipal(Long id) {
        return userRepository.findById(id);
    }
}
