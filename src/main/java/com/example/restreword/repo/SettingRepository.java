package com.example.restreword.repo;

import com.example.restreword.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SettingRepository extends JpaRepository<Setting, Integer> {
    @Modifying
    @Query(value = "update 'setting' set 'value'='?1' where 'id'=?2",nativeQuery = true)
    void updateSetting(String value, String id);
}
