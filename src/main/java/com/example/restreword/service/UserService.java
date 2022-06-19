package com.example.restreword.service;

import com.example.restreword.dto.SettingDTO;
import com.example.restreword.dto.UserDTO;
import com.example.restreword.entity.User;
import com.example.restreword.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> findAll(){
        return userRepository.findAll()
                .stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    private UserDTO mapToDTO(User user,UserDTO userDTO) {


        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setBirthDay(user.getBirthDate());
        userDTO.setSettings(user.getSettings().stream()
                .map(setting ->  new SettingDTO(setting.getId(),setting.getKey(),setting.getValue()))
                .collect(Collectors.toList()));
        return userDTO;
    }

    private User mapToEntity(UserDTO userDTO, User user){
        user.setName(userDTO.getName());
        user.setBirthDate(userDTO.getBirthDay());
        return user;
    }
}
