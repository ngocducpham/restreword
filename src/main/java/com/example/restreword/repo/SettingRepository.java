package com.example.restreword.repo;

import com.example.restreword.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Integer> {
    @Modifying
    @Query(value = "update 'setting' set 'value'='?1' where 'id'=?2", nativeQuery = true)
    void updateSetting(String value, String id);

    @Query(value = """
            select "id","key","value","create_time","update_time"
            from "setting" where "key"=?1 and "value"=?2
            """, nativeQuery = true)
    Setting findByKeyAndValue(String key, String value);

    @Query(value = "select \"setting\".\"id\" from \"setting_user\" join \"setting\" " +
            "on \"setting_user\".\"setting_id\" = \"setting\".\"id\" " +
            "where \"setting_user\".\"user_id\" = ?1 and \"setting\".\"key\" =?2", nativeQuery = true)
    Integer findByUserIdAndKey(Integer userId, String settingKey);

    @Modifying
    @Query(value = "delete from \"setting_user\" " +
            "where \"setting_user\".\"setting_id\" = ?1 and \"setting_user\".\"user_id\" = ?2", nativeQuery = true)
    void deleteBySettingIdAndUserId(Integer settingId, Integer user_id);
}
