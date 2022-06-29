package com.example.restreword.service;

import com.example.restreword.dto.UserInput;
import com.example.restreword.dto.UserOutput;
import com.example.restreword.dto.mapper.UserMapper;
import com.example.restreword.entity.Role;
import com.example.restreword.entity.Setting;
import com.example.restreword.entity.User;
import com.example.restreword.exception.UserNotFoundException;
import com.example.restreword.repo.RoleRepository;
import com.example.restreword.repo.SettingRepository;
import com.example.restreword.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final SettingRepository settingRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public List<UserOutput> findAll() {
        return mapper.toOutputDtos(userRepository.findAll());
    }

    public UserOutput findOne(Integer id) {
       return mapper.toOutputDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: id-" + id)));

    }

    public Integer create(UserInput userInput) {
        User user = mapper.inputToEntity(userInput);
        user.setPassword(passwordEncoder.encode(userInput.getPassword()));
        Role role = Role.builder().name("ROLE_USER").users(Arrays.asList(user)).build();
        roleRepository.saveAndFlush(role);
        return userRepository.saveAndFlush(user).getId();
    }

    public void update(Integer id, UserInput userInput) {
        List<Setting> settings = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"))
                .getSettings();

        User user = mapper.inputToEntity(userInput);
        user.setId(id);
        user.setSettings(settings);
        userRepository.saveAndFlush(user);
    }

    public void delete(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: id-" + id));
        userRepository.delete(user);
    }
}
