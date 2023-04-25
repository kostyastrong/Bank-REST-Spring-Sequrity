package ru.mipt.springtask.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mipt.springtask.entity.UserPrincipal;
import ru.mipt.springtask.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private UserRepository userRepository;

    public UserPrincipal addUser(UserPrincipal user) {
        return userRepository.save(user);
    }

    public Optional<UserPrincipal> getUserPrincipal(Long id) {
        return userRepository.findById(id);
    }
}
