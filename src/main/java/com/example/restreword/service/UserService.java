package com.example.restreword.service;

import com.example.restreword.dto.UserInput;
import com.example.restreword.dto.UserOutput;
import com.example.restreword.dto.mapper.UserMapper;
import com.example.restreword.entity.Setting;
import com.example.restreword.entity.User;
import com.example.restreword.exception.UserNotFoundException;
import com.example.restreword.repo.SettingRepository;
import com.example.restreword.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final SettingRepository settingRepository;
    private final UserMapper mapper;

    public List<UserOutput> findAll() {
        return mapper.toOutputDtos(userRepository.findAll());
    }

    public UserOutput findOne(Integer id) {
        return mapper.toOutputDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: id-" + id)));
    }

    public Integer create(UserInput userInput) {
        User user = mapper.inputToEntity(userInput);
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
