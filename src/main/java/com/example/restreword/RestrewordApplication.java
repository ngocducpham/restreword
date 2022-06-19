package com.example.restreword;


import com.example.restreword.entity.Setting;
import com.example.restreword.repo.SettingRepository;

import com.example.restreword.entity.User;
import com.example.restreword.repo.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;


@SpringBootApplication
@RequiredArgsConstructor
public class RestrewordApplication implements CommandLineRunner {
    private final SettingRepository settingRepository;
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestrewordApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Setting setting = Setting.builder().key("theme").value("dark").build();
        User user = User.builder().name("Duc").birthDate(new Date()).build();

        setting.setUsers(Arrays.asList(user));
        settingRepository.saveAndFlush(setting);

        //user.setSettings(Arrays.asList(setting));
        //userRepository.saveAndFlush(user);
    }
}
