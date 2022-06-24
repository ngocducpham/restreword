package com.example.restreword.service;

import com.example.restreword.common.ResponseTemplate;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
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
        Role role = Role.builder().name("USER").users(Arrays.asList(user)).build();
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Collection<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).toList();

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }
}
