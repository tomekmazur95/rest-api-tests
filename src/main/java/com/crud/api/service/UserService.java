package com.crud.api.service;

import com.crud.api.dto.CreateUser;
import com.crud.api.dto.UpdateUser;
import com.crud.api.dto.ViewUser;
import com.crud.api.entity.User;
import com.crud.api.error.UserNotFoundException;
import com.crud.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ViewUser create(CreateUser createUser) {

        if (createUser == null || createUser.name() == null || createUser.surname() == null) {
            throw new IllegalArgumentException("User fields cannot be null");
        }

        User user = User.mapper(createUser);
        return ViewUser.mapper(userRepository.save(user));
    }

    public Optional<ViewUser> findById(long id) {
        return userRepository.findById(id)
                .map(ViewUser::mapper);
    }

    public List<ViewUser> findAll() {
        return userRepository.findAll().stream()
                .map(ViewUser::mapper)
                .toList();
    }

    public void deleteById(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    public Optional<ViewUser> updateUser(long id, UpdateUser updateUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updateUser.name());
                    user.setSurname(updateUser.surname());
                    return ViewUser.mapper(userRepository.save(user));
                });
    }

}
