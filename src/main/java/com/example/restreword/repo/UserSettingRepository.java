package com.example.restreword.repo;

import com.example.restreword.entity.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingRepository extends JpaRepository<UserSetting,Integer> {
}
