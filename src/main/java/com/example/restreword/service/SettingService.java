package com.example.restreword.service;

import com.example.restreword.dto.SettingInput;
import com.example.restreword.dto.SettingOutput;
import com.example.restreword.dto.mapper.SettingMapper;
import com.example.restreword.entity.Setting;
import com.example.restreword.entity.User;
import com.example.restreword.exception.SettingNotFoundException;
import com.example.restreword.exception.UserNotFoundException;
import com.example.restreword.repo.SettingRepository;
import com.example.restreword.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository settingRepository;
    private final UserRepository userRepository;
    private final SettingMapper mapper;

    public List<SettingOutput> findAll(Integer userId) {
        return mapper.toOutputDtos(getUser(userId).getSettings());
    }

    public HttpStatus create(Integer userId, SettingInput settingInput) {
        boolean isConflictSetting = settingRepository.findByUserIdAndKey(userId, settingInput.getKey()) != null;
        if (isConflictSetting)
            return HttpStatus.CONFLICT;

        Setting setting = settingRepository.findByKeyAndValue(settingInput.getKey(), settingInput.getValue());
        Setting mappedSetting;
        if (setting == null) {
            mappedSetting = mapper.inputToEntity(settingInput);
            mappedSetting.setUsers(Arrays.asList(getUser(userId)));
        } else {
            mappedSetting = setting;
            mappedSetting.getUsers().add(getUser(userId));
        }


        setting = settingRepository.saveAndFlush(mappedSetting);
        return HttpStatus.OK;
    }

    public void update(Integer userId, SettingInput settingInput) {
        Integer settingId = settingRepository.findByUserIdAndKey(userId, settingInput.getKey());
        if(settingId == null){
            create(userId,settingInput);
            return;
        }

        Setting setting = settingRepository.findById(settingId).get();
        setting.setValue(settingInput.getValue());
        settingRepository.saveAndFlush(setting);


    }

    public void delete(Integer userId, String key) {
        User user = getUser(userId);
        Integer settingId = settingRepository.findByUserIdAndKey(userId, key);
        if(settingId != null) {
            settingRepository.deleteBySettingIdAndUserId(settingId, userId);
        }
        else throw new SettingNotFoundException("Seting not found");
    }

    private User getUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: id-" + userId));
    }
}
