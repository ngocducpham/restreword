package com.example.restreword;


import com.example.restreword.common.ResponseTemplate;
import com.example.restreword.entity.Post;
import com.example.restreword.entity.Role;
import com.example.restreword.entity.Setting;
import com.example.restreword.repo.PostRepository;
import com.example.restreword.repo.RoleRepository;
import com.example.restreword.repo.SettingRepository;

import com.example.restreword.entity.User;
import com.example.restreword.repo.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;


@SpringBootApplication
@RequiredArgsConstructor
public class RestrewordApplication implements CommandLineRunner {
    private final SettingRepository settingRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(RestrewordApplication.class, args);
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Setting setting = Setting.builder().key("theme").value("dark").build();
        String password = passwordEncoder.encode("123");
        User user = User.builder().name("editor").password(password).birthDate(LocalDate.of(2001,10,1)).build();
        Post post = Post.builder().id(1).description("Hello").user(user).build();
        Post post2 = Post.builder().id(2).description("World").user(user).build();
        Role role = Role.builder().name("ROLE_EDITOR").users(Arrays.asList(user)).build();

        User user1 = User.builder().name("admin").password(password).birthDate(LocalDate.of(2001,10,1)).build();
        Role role1 = Role.builder().name("ROLE_ADMIN").users(Arrays.asList(user1)).build();

        User user2 = User.builder().name("hr").password(password).birthDate(LocalDate.of(2001,10,1)).build();

        setting.setUsers(Arrays.asList(user));
        settingRepository.saveAndFlush(setting);
        roleRepository.saveAndFlush(role);
        roleRepository.save(role1);
        postRepository.saveAndFlush(post);
        postRepository.saveAndFlush(post2);
        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);


        //user.setSettings(Arrays.asList(setting));
        //userRepository.saveAndFlush(user);
    }
}
